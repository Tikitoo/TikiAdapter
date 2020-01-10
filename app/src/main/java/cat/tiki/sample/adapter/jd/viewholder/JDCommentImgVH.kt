package cat.tiki.sample.adapter.jd.viewholder

import android.view.View
import cat.tiki.sample.TikiContext
import cat.tiki.sample.extendsion.load
import cat.tiki.tikiadapter.TikiBaseImg
import cat.tiki.tikiadapter.TikiBaseVHImpl
import cat.tiki.tikiadapter.extendsion.getAppWidth
import kotlinx.android.synthetic.main.item_jd_comment_img.view.*

class JDCommentImgVH : TikiBaseVHImpl<TikiBaseImg>() {

    override fun bindData(data: TikiBaseImg, view: View) {
        var commentImg = view.item_jd_item_comment_img
        data.setImgParams(TikiContext.getContext())
        var appWidth = getAppWidth(TikiContext.getContext())
        var imgWidth = appWidth / 3.5F
        commentImg.load(data.md5, imgWidth?.toInt(), imgWidth?.toInt())
    }

}
