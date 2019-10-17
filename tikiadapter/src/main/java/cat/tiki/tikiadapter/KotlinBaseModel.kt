package cat.tiki.tikiadapter

import android.content.Context
import cat.tiki.tikiadapter.extendsion.getAppWidth


open class KotlinBaseModel {

    open var column: Int = 1
    open var layoutId: Int = 0

    open var margin: Int = 0
    open var imgWidth: Int = 0
    open var imgHeight: Int = 0

    open var rect: Rect = Rect(0, 0, 0, 0)


    open var width: Float=0F
    open var height: Float=0F

    constructor(column: Int = 1, layoutId: Int = 0) {
        this.column = column
        this.layoutId = layoutId
    }

    open fun setImgParams(context: Context) {
        var appWidth = getAppWidth(context)
        imgWidth = (appWidth - rect?.side * column - rect?.center * column) / column
        imgHeight = (imgWidth / (width / height)).toInt()
    }

    open fun top(top: Int) {
        rect?.top = top
    }

}

class Rect(
        var center: Int,
        var side: Int,
        var top: Int,
        var bottom: Int
)


open interface KotlinBaseModel2 {
    fun getLayoutId2(): Int

}