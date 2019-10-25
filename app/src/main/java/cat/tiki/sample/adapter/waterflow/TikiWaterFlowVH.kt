package cat.tiki.sample.adapter.waterflow

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cat.tiki.sample.R
import cat.tiki.sample.TikiContext
import cat.tiki.sample.extendsion.load
import cat.tiki.tikiadapter.TikiBaseVHImpl


class TikiWaterFlowVH : TikiBaseVHImpl<TikiWaterflowTxt>() {

    override fun bindData(data: TikiWaterflowTxt, view: View) {
        val titleTv = view?.findViewById<TextView>(R.id.item_waterflow_title_tv)
        val descImg = view?.findViewById<ImageView>(R.id.item_waterflow_desc_img)

        data?.apply {
            titleTv.text = title

            setImgParams(TikiContext.getContext())
            descImg.load(md5, imgWidth, imgHeight)

            descImg.layoutParams.width = imgWidth
            descImg.layoutParams.height = imgHeight

        }
    }

}