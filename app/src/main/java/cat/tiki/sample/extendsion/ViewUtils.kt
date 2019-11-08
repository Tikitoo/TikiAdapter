package cat.tiki.sample.extendsion

import android.util.TypedValue
import cat.tiki.sample.TikiContext

/**
 * Created by Yifa Liang on 2019-08-29.
 */
inline fun dip2px(dpValue: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpValue,
        TikiContext?.getContext()?.getResources()?.getDisplayMetrics()
    ).toInt()

}


