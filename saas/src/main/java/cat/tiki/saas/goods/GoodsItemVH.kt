package cat.tiki.saas.goods

import android.view.View
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_goods_detail.view.*

class GoodsItemVH : TikiBaseVHImpl<GoodsDetail>() {

    override fun bindData(data: GoodsDetail, view: View) {
        val nameTv = view?.item_goods_detail_name_tv
        val priceTv = view?.item_goods_detail_price_tv
        val descTv = view?.item_goods_detail_desc_tv
        val modifyTv = view?.item_goods_detail_modify_tv

        data?.apply {
            nameTv.text = name
            priceTv.text = price?.toString()
            descTv.text = desc
        }
    }

}
