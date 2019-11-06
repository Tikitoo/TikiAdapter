package cat.tiki.sample.adapter.jd.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cat.tiki.sample.R
import cat.tiki.sample.TikiContext
import cat.tiki.sample.adapter.jd.JDComment
import cat.tiki.sample.extendsion.load
import cat.tiki.tikiadapter.TikiBaseImg
import cat.tiki.tikiadapter.TikiBaseVHImpl
import cat.tiki.tikiadapter.TikiRvAdapter
import cat.tiki.tikiadapter.extendsion.dip2px
import kotlinx.android.synthetic.main.item_jd_comment.view.*

/**
 * Created by Yifa Liang on 2019-10-24.
 */
class JDCommentVH: TikiBaseVHImpl<JDComment>() {
    override fun bindData(data: JDComment, view: View) {
        var avatarImg = view.item_jd_comment_avatar_img
        var nameTv = view.item_jd_comment_name_tv
        var ratingBar = view.item_jd_comment_ratingbar
        var contentTv = view.item_jd_comment_content_tv
        var imglistRcv = view.item_jd_comment_imglist_rcv
        var modelNameTv = view.item_jd_comment_model_name_tv

        data?.apply {
            var dp50 = dip2px(TikiContext.getContext(), 50f)
            avatarImg.load(avatar, dp50, dp50)
            nameTv.text = name
            ratingBar.rating = ratingNum?.toFloat()
            contentTv.text = content
            modelNameTv.text = modelName
            setAdapter(imgList, imglistRcv)
        }
    }

    private fun setAdapter(
        dataList: MutableList<TikiBaseImg>,
        imglistRcv: RecyclerView) {

        for (tikiBaseImg in dataList) {
            tikiBaseImg?.apply {
                layoutId = JD_COMMENT_IMG
                column = 1
                rect.center = dip2px(TikiContext.getContext(), 5f)

            }
        }

        var imgAdapter = TikiRvAdapter(TikiContext.getContext(), dataList)
        imgAdapter?.apply {
            imglistRcv.adapter = this
            setOrientation(RecyclerView.HORIZONTAL)
            setRvConfig(false, imglistRcv)
            registerItem(JD_COMMENT_IMG, JDCommentImgVH())
            notifyDataSetChanged()
        }
    }

    companion object ItemType {
        var JD_COMMENT_IMG = R.layout.item_jd_comment_img
    }
}