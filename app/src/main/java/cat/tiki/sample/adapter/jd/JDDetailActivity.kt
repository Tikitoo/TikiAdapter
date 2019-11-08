package cat.tiki.sample.adapter.jd

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.tiki.sample.R
import cat.tiki.sample.adapter.jd.viewholder.*
import cat.tiki.tikiadapter.TikiBaseModel
import cat.tiki.tikiadapter.TikiBaseImg
import cat.tiki.tikiadapter.TikiRvAdapter
import kotlinx.android.synthetic.main.activity_tiki_recycler_view.*
import cat.tiki.sample.extendsion.dip2px

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
    var dataList: MutableList<TikiBaseModel> = arrayListOf()
    lateinit var context: Context
    lateinit var rvAdapter: TikiRvAdapter<TikiBaseModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiki_recycler_view)
        context = applicationContext


        addModels()

        for (kotlinBaseModel in dataList) {
            kotlinBaseModel?.apply {
                rect.bottom = dip2px(15F)
//                rect.center = dip2px(5F)
//                rect.side = dip2px(10F)
                if (Jd_ITEM_GOODS_RECOMMEND == layoutId) {
                    rect.center = dip2px(7f)
                    rect.side = dip2px(15f)
                } else {
                    rect.center = 0
                    rect.side = 0
                }
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
            registerItem(Jd_ITEM_GOODS, JDItemGoodsVH())
            registerItem(Jd_ITEM_GOODS_RECOMMEND, JDItemGoodsRecommendVH())
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
        var Jd_SKU = R.layout.item_jd_item_sku_custom
        var Jd_COMMENT = R.layout.item_jd_comment
        var Jd_ITEM_TITLE = R.layout.item_jd_item_title
        var Jd_ITEM_GOODS = R.layout.item_jd_item_goods
        var Jd_ITEM_GOODS_RECOMMEND = R.layout.item_jd_goods_recommend
    }

    object JDData {
        open fun parseData(): MutableList<TikiBaseModel> {
            val modelList = mutableListOf<TikiBaseModel>()
            // banner
            val bannerImgList = mutableListOf<TikiBaseImg>()
            bannerImgList.add(TikiBaseImg(500f, 500f, bannerMd51))
            bannerImgList.add(TikiBaseImg(500f, 500f, bannerMd52))
            val jdBanner = JDBanner(JD_BANNER, 1, bannerImgList)
            jdBanner.title = "【超级爆款】华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机8GB+128GB天空之境全网通双4G手机"
            jdBanner.price = 3988.00f
            jdBanner.content = "【价保到11月1日，好货不用等；1号优惠200元！到手价3？？8！】以旧换新至高补贴1000元！P20优惠600，到手价2499起》"
            modelList.add(jdBanner)

            // sku select

            val jdSku1 = JDSku(Jd_SKU, 1, "优惠", "满199减99")
            modelList.add(jdSku1)

            val jdSku2 = JDSku(Jd_SKU, 1, "活动", "手机专场，仅此一天。。。")
            modelList.add(jdSku2)


            // 评论区
            val commentTitle = JDTitle(Jd_ITEM_TITLE, 1, "评论")
            commentTitle.content = "6.3万+"
            commentTitle.rightStr = "好评率96%"
            modelList.add(commentTitle)

            var commentImgList = mutableListOf<TikiBaseImg>()
            var imgListTemp = arrayOf(img1, img2, img3, img4, img5)
            for (imgModel in imgListTemp) {
            val tikiImg = TikiBaseImg(500f,    500f, imgModel)
                commentImgList.add(tikiImg)
            }
            val commentModel1 = JDComment(Jd_COMMENT, 1, comment_avator1, "有范儿", 3, commentContent1, commentImgList, "紫蓝色")
            modelList.add(commentModel1)


            val commentModel2 = JDComment(Jd_COMMENT, 1, comment_avator1, "王柳", 3, commentContent1, commentImgList, "紫红色")
            modelList.add(commentModel2)

            /** 详细介绍 */
            val detailTitle = JDTitle(Jd_ITEM_TITLE, 1, "详细介绍")
//            commentTitle.content = "6.3万+"
//            commentTitle.rightStr = "好评率96%"
            modelList.add(detailTitle)

            var goodsImg1 = TikiBaseImg(386f, 702f, goodsImg1)
            var goodsItem1 = JDItemGoods(Jd_ITEM_GOODS, 1, goodsImg1)
            modelList.add(goodsItem1)

            var goodsImg2 = TikiBaseImg(386f, 373f, goodsImg2)
            var goodsItem2 = JDItemGoods(Jd_ITEM_GOODS, 1, goodsImg2)
            modelList.add(goodsItem2)

            var goodsImg3 = TikiBaseImg(750f, 16600f, goodsImg3)
            var goodsItem3 = JDItemGoods(Jd_ITEM_GOODS, 1, goodsImg3)
            modelList.add(goodsItem3)


            // 推荐
            val recommentTitle = JDTitle(Jd_ITEM_TITLE, 1, "推荐")
            modelList.add(recommentTitle)
            var goodsReommImgList = arrayOf(goodsRecomImg1, goodsRecomImg2, goodsRecomImg3)
            var priceList = arrayOf<Float>(3200.2f, 4900f, 5600f)
            var recommTitleList = arrayOf(goodsRecommTitle1, goodsRecommTitle2, goodsRecommTitle3)
            var recommGoodsItemList = mutableListOf<JDItemGoods>()
            for (pos in 0..10) {
                val randomImg = goodsReommImgList.get((0..2).random())
                val price = priceList.get((0..2).random())
                val title = recommTitleList.get((0..2).random())
                var goodsImg = TikiBaseImg(500f, 500f, randomImg)
                goodsImg.column = 2
                var itemGoods = JDItemGoods(Jd_ITEM_GOODS_RECOMMEND, 2, goodsImg)
                itemGoods.price = price
                itemGoods.content = title
                recommGoodsItemList.add(itemGoods)
            }
            modelList.addAll(recommGoodsItemList)

            return modelList
        }

        val commentContent1 = "手机质量非常好，外观非常漂亮，尺寸大小适合女性，男性建议mate20，拍照清晰，逼格高，自带算法，画面增强，随手拍，竟显大片本色，颜值高，自拍，旅游神器，屏幕清晰，触感好，电池待机时间长，充电速度很快"
        val comment_avator1 = "https://pic2.zhimg.com/v2-92e173f8baef800840a165d7e7e2a1f5_xl.jpg"
        val bannerMd51 = "https://tva1.sinaimg.cn/large/006y8mN6ly1g89d4owsehj30ku0kudgj.jpg"
        val bannerMd52 = "https://tva1.sinaimg.cn/large/006y8mN6gy1g89d9jptiwj30b40b4t97.jpg"


        val img1 = "https://img30.360buyimg.com/shaidan/s128x96_jfs/t1/54205/10/6753/79734/5d46dc61Eca73a35d/78d9b4e23d5a1895.jpg"
        val img2 = "https://img30.360buyimg.com/shaidan/s128x96_jfs/t1/82255/1/6282/97683/5d46dc61Efd96d466/53a2d610ab0ad56d.jpg"
        val img3 = "https://img30.360buyimg.com/shaidan/s128x96_jfs/t1/60889/32/6389/71828/5d46dc62E28bfee8e/099c2f202e9e51f8.jpg"
        val img4 = "https://img30.360buyimg.com/shaidan/jfs/t1/44857/25/6917/75917/5d46dc63E2a2a8b84/94bd704a08d747de.jpg"
        val img5 = "https://img30.360buyimg.com/shaidan/s128x96_jfs/t1/52332/40/5508/179604/5d33bc92Ecd662325/9f572baff4c64e8c.jpg"


        val goodsImg1 = "https://img20.360buyimg.com/vc/jfs/t1/97212/18/563/528238/5dafc681E143ef804/fc81ae477c41ce7f.jpg"
        val goodsImg2 = "https://img20.360buyimg.com/vc/jfs/t1/104023/37/535/200137/5dafce70E50ef99ed/bd33aa924d4eacc7.jpg"
        val goodsImg3 = "https://img20.360buyimg.com/vc/jfs/t1/68032/10/13451/4867733/5dafc686Ed4348dd4/dfcc9e0ffe2a3c38.jpg"


        val goodsRecommTitle1 = "【送原装无线充等400元壕礼|华为直供】华为mate30 pro 全网通手机 翡冷翠 8G+256G"
        val goodsRecommTitle2 = "华为 HUAWEI Mate 30 5G 麒麟990 4000万超感光徕卡影像双超级快充8GB+256GB亮黑色5G全网通版"
        val goodsRecommTitle3 = "华为 HUAWEI Mate 30 5G 麒麟990 4000万超感光徕卡影像双超级快充8GB+256GB亮黑色5G全网通版"

        val goodsRecomImg1 = "https://img14.360buyimg.com/mobilecms/s270x270_jfs/t1/40607/35/15448/1002934/5d834bc9Ea5a264dd/9c204f803b5459ec.png"
        val goodsRecomImg2 = "https://img14.360buyimg.com/mobilecms/s270x270_jfs/t1/61692/22/14579/168609/5dbe8723Eb2587932/8c464c4b72238166.jpg"
        val goodsRecomImg3 = "https://img14.360buyimg.com/mobilecms/s270x270_jfs/t1/63127/18/14014/52549/5db45649E4e2cb146/ef4dd7ae22d1ad80.jpg"
    }
}

