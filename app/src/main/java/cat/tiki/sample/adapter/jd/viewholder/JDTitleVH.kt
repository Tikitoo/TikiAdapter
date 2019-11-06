package cat.tiki.sample.adapter.jd.viewholder

import android.view.View
import cat.tiki.sample.adapter.jd.JDTitle
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_jd_item_title.view.*

/**
 * Created by Yifa Liang on 2019-10-24.
 */
class JDTitleVH: TikiBaseVHImpl<JDTitle>() {
    override fun bindData(data: JDTitle, view: View) {
        var typeTv = view.item_jd_title_type_tv
        var cotnentTv = view.item_jd_title_content_tv
        var rightTv = view.item_jd_title_right_tv

        data?.apply {
            typeTv.text = typeStr
            cotnentTv.text = content
            rightTv.text = rightStr
        }

    }
}