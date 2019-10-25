package cat.tiki.sample.adapter.jd.viewholder

import android.view.View
import cat.tiki.sample.adapter.jd.JDBanner
import cat.tiki.sample.adapter.jd.JDSku
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_jd_item_sku.view.*

/**
 * Created by Yifa Liang on 2019-10-24.
 */
class JDSkuVH: TikiBaseVHImpl<JDSku>() {
    override fun bindData(data: JDSku, view: View) {
        view.item_jd_item_sku_click_img

    }
}