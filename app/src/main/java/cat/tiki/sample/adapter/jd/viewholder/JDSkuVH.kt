package cat.tiki.sample.adapter.jd.viewholder

import android.view.View
import cat.tiki.sample.adapter.jd.JDSku
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_jd_item_sku_custom.view.*

/**
 * Created by Yifa Liang on 2019-10-24.
 */
class JDSkuVH: TikiBaseVHImpl<JDSku>() {
    override fun bindData(data: JDSku, view: View) {
        var typeTv = view.item_jd_custom_sku_item_layout_type_tv
        var titleTv = view.item_jd_custom_sku_item_layout_title_tv
        var tagLayout = view.item_jd_custom_sku_item_layout_tag_layout
        var tagTv = view.item_jd_custom_sku_item_layout_tag_tv
        var tagImg = view.item_jd_custom_sku_item_layout_tag_img
        var moreTv = view.item_jd_custom_sku_item_layout_right_click_tv


//        view.setOnClickListener()

        data?.apply {
            typeTv.text = typeStr
            titleTv.text = content

            if (tagImgResId == 0 && tag?.isEmpty()) {
                tagLayout.visibility = View.GONE
            } else{
                tagLayout.visibility = View.VISIBLE
                tagTv.text = tag
                tagImg.setImageResource(tagImgResId)
            }

            moreTv.visibility = if (isShowMore) View.VISIBLE  else View.GONE


        }


    }
}