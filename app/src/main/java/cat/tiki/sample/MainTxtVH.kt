package cat.tiki.sample

import android.view.View
import android.widget.TextView
import cat.tiki.tikiadapter.TikiBaseVHImpl
import kotlinx.android.synthetic.main.item_main_txt.view.*

class MainTxtVH : TikiBaseVHImpl<MainModel>() {

    override fun bindData(mainModel: MainModel, view: View) {
        val mainTxtTv = view.main_txt_tv
        mainTxtTv.text = mainModel.txt

    }


}
