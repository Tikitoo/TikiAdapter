package cat.tiki.tikiadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Yifa Liang on 2019-08-20.
 */
class TikiBaseVH<T: KotlinBaseModel>(var view: View, val vhImpl: TikiBaseVHImpl<out T>?): RecyclerView.ViewHolder(view) {

    open fun bindData(data: T) {
        vhImpl?.bindData(data, view)
    }
}