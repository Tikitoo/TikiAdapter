package cat.tiki.tikiadapter.extendsion

import android.content.Context
import android.util.TypedValue

/**
 * Created by Yifa Liang on 2019-08-29.
 */
inline fun dip2px(context: Context, dpValue: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpValue,
        context.getResources().getDisplayMetrics()
    ).toInt()

}


