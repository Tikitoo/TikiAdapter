package cat.tiki.sample.adapter.jd.viewholder

import android.view.View
import cat.tiki.sample.TikiContext
import cat.tiki.sample.adapter.jd.JDItemGoods
import cat.tiki.sample.extendsion.load
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_jd_goods_recommend.view.*

/**
 * Created by Yifa Liang on 2019-10-24.
 */
class JDItemGoodsRecommendVH: TikiBaseVHImpl<JDItemGoods>() {
    override fun bindData(data: JDItemGoods, view: View) {
        var headerImg = view.item_goods_recommend_header_img
        var contentTv = view.item_goods_recommend_content_tv
        var priceTv = view.item_goods_recommend_price_tv
        data?.apply {
            contentTv.text = content
            priceTv.text = price?.toString()

            img?.apply {
                setImgParams(TikiContext.getContext())
                headerImg.load(md5, imgWidth, imgHeight)

            }
        }
    }
}