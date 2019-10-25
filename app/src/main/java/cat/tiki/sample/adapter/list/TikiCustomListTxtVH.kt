package cat.tiki.sample.adapter.list

import android.view.View
import android.widget.TextView
import cat.tiki.sample.R
import cat.tiki.tikiadapter.TikiBaseVHImpl


class TikiCustomListTxtVH : TikiBaseVHImpl<TikiCustomTxt>() {

    override fun bindData(data: TikiCustomTxt, view: View) {
        val titleTv = view?.findViewById<TextView>(R.id.item_customlist_txt_title_tv)
        titleTv.text = data.title
    }

}