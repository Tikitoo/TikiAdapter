package cat.tiki.sample.adapter

import android.view.View
import android.widget.TextView
import cat.tiki.sample.R
import cat.tiki.tikiadapter.KotlinBaseVHImpl


class KotlinCustomListTxtVH : KotlinBaseVHImpl<KotlinCustomTxt>() {

    override fun bindData(data: KotlinCustomTxt, view: View) {
        val titleTv = view?.findViewById<TextView>(R.id.biz_show_item_customlist_txt_title_tv)
        titleTv.text = data.title
    }

}