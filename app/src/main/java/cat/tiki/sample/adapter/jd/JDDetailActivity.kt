package cat.tiki.sample.adapter.jd

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.tiki.sample.R
import cat.tiki.sample.adapter.jd.viewholder.JDBannerVH
import cat.tiki.sample.adapter.jd.viewholder.JDCommentVH
import cat.tiki.sample.adapter.jd.viewholder.JDSkuVH
import cat.tiki.sample.adapter.jd.viewholder.JDTitleVH
import cat.tiki.tikiadapter.KotlinBaseModel
import cat.tiki.tikiadapter.TikiBaseImg
import cat.tiki.tikiadapter.TikiRvAdapter
import kotlinx.android.synthetic.main.activity_tiki_recycler_view.*
import cat.tiki.tikiadapter.extendsion.dip2px
import kotlinx.android.synthetic.main.activity_main.*

/**
 * banner 滚动
 * SKU 信息，优惠券，地址
 * 评论
 * 问答
 * 详解介绍（图片多）
 * 推荐
 * Created by Yifa Liang on 2019-09-16.
 */
class JDDetailActivity: AppCompatActivity() {
    var dataList: MutableList<KotlinBaseModel> = arrayListOf()
    lateinit var context: Context
    lateinit var rvAdapter: TikiRvAdapter<KotlinBaseModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiki_recycler_view)
        context = applicationContext


        addModels()

        for (kotlinBaseModel in dataList) {
            kotlinBaseModel?.apply {
                rect.bottom = dip2px(context, 15F)
//                rect.center = dip2px(context, 5F)
//                rect.side = dip2px(context, 10F)
            }
        }


        val recyclerView = activity_tiki_rcv
        rvAdapter = TikiRvAdapter(applicationContext, dataList)
        rvAdapter?.apply {
            recyclerView.adapter = this
            setRvConfig(false, recyclerView)
            registerItem(JD_BANNER, JDBannerVH())
            registerItem(Jd_SKU, JDSkuVH())
            registerItem(Jd_ITEM_TITLE, JDTitleVH())
            registerItem(Jd_COMMENT, JDCommentVH())
            registerItem(Jd_ITEM_GOODS, JDCommentVH())
            notifyDataSetChanged()
        }


    }

    private fun addModels() {
        val parseData = JDData.parseData()
        dataList = parseData
//        rvAdapter.notifyDataSetChanged()


    }



    companion object ItemType {
        var JD_BANNER = R.layout.item_jd_detail_banner
        var Jd_SKU = R.layout.item_jd_item_sku
        var Jd_COMMENT = R.layout.item_jd_comment
        var Jd_ITEM_TITLE = R.layout.item_jd_item_title
        var Jd_ITEM_GOODS = R.layout.item_jd_item_goods
    }

    object JDData {
        open fun parseData(): MutableList<KotlinBaseModel> {
            val modelList = mutableListOf<KotlinBaseModel>()
            // banner
            val imgList = mutableListOf<TikiBaseImg>()
            imgList.add(TikiBaseImg(500f, 500f, bannerMd51))
            imgList.add(TikiBaseImg(500f, 500f, bannerMd52))
            val jdBanner = JDBanner(JD_BANNER, 1, imgList)
            jdBanner.title = "【超级爆款】华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机8GB+128GB天空之境全网通双4G手机"
            jdBanner.price = 3988.00f
            jdBanner.content = "【价保到11月1日，好货不用等；1号优惠200元！到手价3？？8！】以旧换新至高补贴1000元！P20优惠600，到手价2499起》"
            modelList.add(jdBanner)
            return modelList
        }

        val bannerMd51 = "https://tva1.sinaimg.cn/large/006y8mN6ly1g89d4owsehj30ku0kudgj.jpg"
        val bannerMd52 = "https://tva1.sinaimg.cn/large/006y8mN6gy1g89d9jptiwj30b40b4t97.jpg"
    }
}

