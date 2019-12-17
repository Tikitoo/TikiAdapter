package cat.tiki.tikiadapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Yifa Liang on 2019-08-20.
 */
class TikiBaseVH<T: TikiBaseModel>(
    var view: View,
    val vhImpl: TikiBaseVHImpl<out T>?,
    var itemClickListener: TikiItemClickListener?
): RecyclerView.ViewHolder(view), View.OnClickListener {

    internal val clickViewList: MutableList<View> = mutableListOf()

    override fun onClick(v: View) {
        val position = v?.getTag(R.id.tiki_rv_item_id)
        Log.d("TikiBaseVH: ", "position: " + position)
        itemClickListener?.onItemClick(v, position as Int)
    }

    open fun bindData(data: T, position: Int) {
        vhImpl?.setOnClick(clickViewList)
        vhImpl?.bindData(data, view)

        clickViewList?.map {
            it?.setOnClickListener(this@TikiBaseVH)
            it?.setTag(R.id.tiki_rv_item_id, position)
            Log.d("TikiBaseVH: ", "position: " + position)

        }



    }
}