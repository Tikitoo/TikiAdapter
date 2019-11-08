package cat.tiki.tikiadapter

import android.view.View

/**
 * Created by Yifa Liang on 2019-08-21.
 */
open abstract class TikiBaseVHImpl<T> {

    open fun bindData(data: TikiBaseModel, view: View) {
        val data = data as T
        bindData(data, view)
    }

    abstract fun bindData(t: T, view: View)
}