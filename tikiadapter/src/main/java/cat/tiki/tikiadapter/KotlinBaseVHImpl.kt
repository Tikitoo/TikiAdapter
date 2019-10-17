package cat.tiki.tikiadapter

import android.view.View

/**
 * Created by Yifa Liang on 2019-08-21.
 */
open abstract class KotlinBaseVHImpl<T> {

    open fun bindData(data: KotlinBaseModel, view: View) {
        val data = data as T
        bindData(data, view)
    }

    abstract fun bindData(t: T, view: View)
}