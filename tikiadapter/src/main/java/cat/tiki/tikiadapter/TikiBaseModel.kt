package cat.tiki.tikiadapter

import android.content.Context
import android.os.Parcelable
import androidx.annotation.Keep
import cat.tiki.tikiadapter.extendsion.getAppWidth
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
open class TikiBaseModel(open var column: Int = 1, open var layoutId: Int = 0) : Parcelable {
    open var margin: Int = 0
    open var imgWidth: Int = 0
    open var imgHeight: Int = 0

    open var rect: Rect = Rect(0, 0, 0, 0)

    open var isUseDrawRect = false
    open var drawRect: Rect = Rect(0, 0, 0, 0)

    open var width: Float= 0F
    open var height: Float= 0F
    open var md5: String= ""

    open fun setImgParams(context: Context) {
        var appWidth = getAppWidth(context)
        imgWidth = (appWidth - rect?.side * column - rect?.center * column) / column
        imgHeight = (imgWidth / (width / height)).toInt()
    }

    open fun setBaseImg(md5: String, width: Float, height: Float) {
        this.md5 = md5
        this.width = width
        this.height = height
    }

    open fun setBaseImg(baseImg: TikiBaseImg) {
        this.md5 = baseImg?.md5
        this.width = baseImg?.width
        this.height = baseImg?.height
    }

    open fun top(top: Int) {
        rect?.top = top
    }

}

@Parcelize
@Keep
class Rect(
    var center: Int,
    var side: Int,
    var top: Int,
    var bottom: Int
) : Parcelable {

}

@Parcelize
@Keep
open class TikiBaseImg(
    override var width: Float,
    override var height: Float,
    override var md5: String) : TikiBaseModel(), Parcelable {

    fun getScale(): Float {
        if (width != 0f  && height != 0f) {
            return width / height
        } else {
            return 1f
        }
    }
}

