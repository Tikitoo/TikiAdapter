package cat.tiki.tikiadapter

import android.view.View

/**
 * Created by Yifa Liang on 2019-08-21.
 */
open abstract class TikiBaseVHImpl<T> {
    private var clickViewList: MutableList<View>? = null

    open fun bindData(data: TikiBaseModel, view: View) {
        val data = data as T
        bindData(data, view)
        setListener()
    }

    abstract fun bindData(t: T, view: View)

    open fun setListener() {

    }

    open fun addClickView(clickView: View?) {
        clickView?.apply {
            clickViewList?.add(clickView)
        }
    }

    fun setOnClick(clickViewList: MutableList<View>) {
        this.clickViewList = clickViewList

    }


}