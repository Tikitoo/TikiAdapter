package cat.tiki.saas.goods.add

import android.view.View
import cat.tiki.saas.utils.dip2px
import cat.tiki.saas.utils.load
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_goods_img.view.*

class GoodsImgVH : TikiBaseVHImpl<GoodsImg>() {
    override fun bindData(data: GoodsImg, view: View) {
        val goodsImg = view?.item_goods_img_goods_img
        val dp60 = dip2px(60f)
        goodsImg?.load(data.md5, dp60, dp60)
    }

}
