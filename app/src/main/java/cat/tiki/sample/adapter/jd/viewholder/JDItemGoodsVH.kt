package cat.tiki.sample.adapter.jd.viewholder

import android.view.View
import cat.tiki.sample.TikiContext
import cat.tiki.sample.adapter.jd.JDItemGoods
import cat.tiki.sample.extendsion.load
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_jd_item_goods.view.*

/**
 * Created by Yifa Liang on 2019-10-24.
 */
class JDItemGoodsVH: TikiBaseVHImpl<JDItemGoods>() {
    override fun bindData(data: JDItemGoods, view: View) {
        var goodsImg = view.item_jd_goods_img
        data?.img.apply {
            setImgParams(TikiContext.getContext())
            goodsImg.load(md5, imgWidth, imgHeight)

        }
    }
}