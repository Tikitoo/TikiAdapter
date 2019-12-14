package cat.tiki.sample

import android.view.View
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_main_txt.view.*

class MainTxtVH : TikiBaseVHImpl<MainEntity>() {

    override fun bindData(mainModel: MainEntity, view: View) {
        val mainTxtTv = view.main_txt_tv
        val mainTxtTv2 = view.main_txt_tv2
        mainTxtTv.text = mainModel.txt

        addClickView(mainTxtTv)
        addClickView(mainTxtTv2)
    }




}
