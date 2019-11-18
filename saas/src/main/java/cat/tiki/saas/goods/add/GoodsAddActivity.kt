package cat.tiki.saas.goods.add

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cat.tiki.saas.R
import cat.tiki.saas.utils.LeanInit
import cat.tiki.saas.utils.ModelParseUtils
import cat.tiki.tikiadapter.extendsion.getAppWidth
import cn.leancloud.AVObject
import cn.leancloud.AVQuery
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_goods_add.*
import cn.leancloud.AVFile
import android.graphics.BitmapFactory
import androidx.arch.core.util.Function
import androidx.recyclerview.widget.RecyclerView
import cat.tiki.saas.goods.GoodsDetail
import cat.tiki.tikiadapter.TikiBaseImg
import cat.tiki.tikiadapter.TikiBaseModel
import cat.tiki.tikiadapter.TikiRvAdapter


/**
 * Created by Yifa Liang on 2019-11-09.
 */
class GoodsAddActivity: AppCompatActivity() {
    var nameEt: EditText? = null
    var priceEt: EditText? = null
    var descEt: EditText? = null
    var objId: String? = null
    var addImg: ImageView? = null
    var showImg: ImageView? = null

    var tikiRvAdapter: TikiRvAdapter<TikiBaseModel>? = null
    var mainList: MutableList<TikiBaseModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_goods_add
        )

        nameEt = activity_goods_add_name_et
        priceEt = activity_goods_add_price_et
        descEt = activity_goods_add_desc_et
        val addBtn = activity_goods_add_add_btn
        addBtn?.setOnClickListener {
            val nameStr = nameEt?.editableText.toString()
            val priceStr = priceEt?.editableText.toString()
            val descStr = descEt?.editableText.toString()
            if (objId != null) {
                onUpdateGoods(nameStr, priceStr, descStr)
            } else {
                onAddGoods(nameStr, priceStr, descStr)
            }
        }

        addImg  = activity_goods_add_add_img
        showImg = activity_goods_add_show_img

        addImg?.setOnClickListener {
            selectImg()
        }

        val imgRcv = activity_goods_add_show_img_rcv
        tikiRvAdapter = TikiRvAdapter(applicationContext, mainList)
        imgRcv.adapter = tikiRvAdapter
        tikiRvAdapter?.apply {
            setOrientation(RecyclerView.HORIZONTAL)
            setRvConfig(false, imgRcv)
            registerItem(TYPE_ITEM_IMG, GoodsImgVH())
            notifyDataSetChanged()
        }

        objId = intent?.getStringExtra("objId")
        if (objId != null) {
            getGoodsDetailById(objId)
            getGoodImgList()
        }
    }

    private fun onUpdateGoods(nameStr: String, priceStr: String, descStr: String) {
        val goodsObj = AVObject.createWithoutData("Goods", objId)
        goodsObj.put("name", nameStr)
        goodsObj.put("price", priceStr?.toInt())
        goodsObj.put("desc", descStr)
        goodsObj.save()
    }

    private fun onAddGoods(nameStr: String, priceStr: String, descStr: String) {
        val goodsObj = AVObject("Goods")
        goodsObj.put("name", nameStr)
        goodsObj.put("price", priceStr?.toInt())
        goodsObj.put("desc", descStr)

        // 将对象保存到云端
        goodsObj.saveInBackground()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<AVObject> {
            override fun onSubscribe(disposable: Disposable) {}
            override fun onNext(goods: AVObject) {
                // 成功保存之后，执行其他逻辑
                objId = goods.objectId
                println("产品保存成功。objectId：" + goods.objectId)
            }

            override fun onError(throwable: Throwable) {
                println(throwable?.message)
            }

            override fun onComplete() {}
        })
    }

    private fun getGoodsDetailById(objId: String?) {
        val query = AVQuery<AVObject>("Goods")
        query.getInBackground(objId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<AVObject> {
            override fun onSubscribe(disposable: Disposable) {}
            override fun onNext(goods: AVObject) {
                val goodsDetail = ModelParseUtils.getGoodsDetail(goods?.serverData)
                setEditData(goodsDetail)
            }

            override fun onError(throwable: Throwable) {}
            override fun onComplete() {}
        })
    }

    private fun setEditData(goodsDetail: GoodsDetail?) {
        goodsDetail?.apply {
            nameEt?.setText(name)
            priceEt?.setText(price?.toString())
            descEt?.setText(desc)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            val mSelectedUri: MutableList<Uri> = Matisse.obtainResult(data)
            Log.d("Matisse", "mSelected: " + mSelectedUri)
            for (uri in mSelectedUri) {
                showImg?.setImageURI(uri)
                val imgSize = getImgSize(uri)
                LeanInit.uploadImg(null, imgSize, objId)
            }
        }
    }



    private fun getImgSize(uri: Uri): TikiBaseImg {
        val fromURI = LeanInit.getRealPathFromURI(applicationContext, uri)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(fromURI, options)
        val imageWidth = options.outWidth
        val imageHeight = options.outHeight
        return TikiBaseImg(imageWidth?.toFloat(), imageHeight?.toFloat(), fromURI)

    }

    private fun selectImg() {
        val girdSize: Int = (getAppWidth(applicationContext) / 3f).toInt()
        Matisse.from(this)
            .choose(MimeType.ofAll())
            .countable(true)
            .maxSelectable(9)
//            .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K)) // gif
            .gridExpectedSize(girdSize)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .showPreview(false) // Default is `true`
            .forResult(REQUEST_CODE_CHOOSE)
    }

    private fun getGoodImgList() {
        var imgList: MutableList<GoodsImg> = mutableListOf()
        val goods = AVObject.createWithoutData("Goods", objId)
        val imgQuery = AVQuery<AVObject>("ArkImg")
        imgQuery.whereEqualTo("parent", goods)
        imgQuery.findInBackground()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<AVObject>> {
            override fun onSubscribe(disposable: Disposable) {}
            override fun onNext(fileList: List<AVObject>) {
                // comments 包含与 post 相关联的评论
                for (avFile in fileList) {
                    avFile?.serverData?.apply {
                        val goodsImg = ModelParseUtils.getGoodsImg(this)
                        goodsImg?.layoutId = TYPE_ITEM_IMG
                        goodsImg?.column = 1
                        imgList.add(goodsImg!!)
                    }
                }
                mainList.addAll(imgList)
                tikiRvAdapter?.notifyDataSetChanged()
                println("包含的图片" + imgList)

            }

            override fun onError(throwable: Throwable) {
                print(throwable?.message)
            }
            override fun onComplete() {}
        })
    }

    companion object Static {
        val REQUEST_CODE_CHOOSE: Int = 10001
        val TYPE_ITEM_IMG = R.layout.item_goods_img
    }

}