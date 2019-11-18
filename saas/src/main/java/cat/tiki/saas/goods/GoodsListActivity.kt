package cat.tiki.saas.goods

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cat.tiki.saas.R
import cat.tiki.saas.goods.add.GoodsAddActivity
import cat.tiki.saas.utils.ModelParseUtils
import cat.tiki.saas.utils.dip2px
import cat.tiki.tikiadapter.TikiBaseModel
import cat.tiki.tikiadapter.TikiItemClickListener
import cat.tiki.tikiadapter.TikiRvAdapter
import cn.leancloud.AVObject
import cn.leancloud.AVQuery
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_goods_list.*


class GoodsListActivity : AppCompatActivity(), TikiItemClickListener {
    override fun onItemClick(view: View, position: Int) {
        print("pos:" + position)
        val goodsDetail = mainList.get(position) as GoodsDetail
        toGoodsModifyActivity(goodsDetail.objectId)
    }

    var mainList: MutableList<TikiBaseModel> = mutableListOf()
    var tikiRvAdapter: TikiRvAdapter<TikiBaseModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_list)
        var listRcv: RecyclerView = activity_goods_list_rcv

        tikiRvAdapter = TikiRvAdapter(applicationContext, mainList)
        listRcv.adapter = tikiRvAdapter
        tikiRvAdapter?.apply {
            setRvConfig(false, listRcv)
            registerItem(TYPE_GOODS, GoodsItemVH())
            setOnItemClick(this@GoodsListActivity)
            notifyDataSetChanged()
        }
    }


    override fun onResume() {
        super.onResume()
        getGoodsList()
    }

    private fun getImgList(objId: String): Observable<MutableList<AVObject>>? {
        val goods = AVObject.createWithoutData("Goods", objId)
        val imgQuery = AVQuery<AVObject>("ArkImg")
        imgQuery.whereEqualTo("parent", goods)
        return imgQuery.findInBackground()
    }

    private fun getGoodsList2(): Observable<MutableList<AVObject>> {
        val goodsListQuery = AVQuery<AVObject>("Goods")
        goodsListQuery.orderByDescending("updatedAt")
        return goodsListQuery.findInBackground()
    }

    private fun getGoodsList() {

        mainList?.clear()
        var goodsList: MutableList<TikiBaseModel> = arrayListOf()
        var imgObserList: MutableList<Observable<MutableList<AVObject>>>? = arrayListOf()
        getGoodsList2()
            .subscribeOn(Schedulers.io())
            .map{
                Observable.fromIterable(it).map { t: AVObject -> ModelParseUtils.getGoodsDetail(t?.serverData) }
            }.flatMap{
                it?.map {
                    it?.rect?.apply {
                        side = dip2px(15f)
                        center = dip2px(8f)
                        top = dip2px(8f)
                        bottom = dip2px(8f)
                    }
                    goodsList.add(it)
                    println("getGoodsList child get img" + it?.objectId)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                println("getGoodsList child get img" + it)
                mainList.addAll(goodsList)
                tikiRvAdapter?.notifyDataSetChanged()
            }, {
                println("getGoodsList child getGoodsList" + it?.message)
            }, {

            }, {

            })

    }


    companion object ITEM_TYPE {
        val TYPE_GOODS = R.layout.item_goods_detail

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_goods, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_add -> {
                toGoodsModifyActivity(null)
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun toGoodsModifyActivity(objId: String?) {
        val intent = Intent(this, GoodsAddActivity::class.java)
        if (objId != null) {
            intent.putExtra("objId", objId)
        }
        startActivity(intent)
    }

}
