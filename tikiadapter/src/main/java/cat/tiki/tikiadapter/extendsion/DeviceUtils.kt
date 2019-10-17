package cat.tiki.tikiadapter.extendsion

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * Created by Yifa Liang on 2019-10-17.
 */


inline fun getAppWidth(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}