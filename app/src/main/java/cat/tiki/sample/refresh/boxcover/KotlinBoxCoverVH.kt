package cat.tiki.sample.refresh.boxcover

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cat.tiki.sample.R
import cat.tiki.sample.extendsion.load
import cat.tiki.tikiadapter.TikiBaseVHImpl

/**
 * Created by Yifa Liang on 2019-08-20.
 */
class KotlinBoxCoverVH: TikiBaseVHImpl<KotlinBoxCover>() {

    override fun bindData(data: KotlinBoxCover, view: View) {
        val titleTv = view?.findViewById<TextView>(R.id.biz_show_item_boxcover_title_tv)
        val sloganTv = view?.findViewById<TextView>(R.id.biz_show_item_boxcover_slogan_tv)
        val topImg = view?.findViewById<ImageView>(R.id.biz_show_item_boxcover_top_img)

        data?.apply {
            titleTv?.text = name
            sloganTv?.text = slogan
            topImg?.load(headImg, imgWidth, imgHeight)
        }
    }
}