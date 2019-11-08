package cat.tiki.sample

import android.view.View
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_main_txt.view.*

class MainTxtVH : TikiBaseVHImpl<MainEntity>() {

    override fun bindData(mainModel: MainEntity, view: View) {
        val mainTxtTv = view.main_txt_tv
        mainTxtTv.text = mainModel.txt

    }


}
