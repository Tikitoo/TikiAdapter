package cat.tiki.sample.adapter.jd

import android.view.View
import android.widget.TextView
import cat.tiki.sample.R
import cat.tiki.tikiadapter.TikiBaseVHImpl


class JDDetailVH : TikiBaseVHImpl<JDDetailModel>() {

    override fun bindData(data: JDDetailModel, view: View) {
        val titleTv = view?.findViewById<TextView>(R.id.item_customlist_txt_title_tv)
        titleTv.text = data.title
    }

}