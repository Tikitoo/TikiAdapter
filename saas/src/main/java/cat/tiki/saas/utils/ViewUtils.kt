package cat.tiki.saas.utils

import android.util.TypedValue
import cat.tiki.saas.ArkApp

/**
 * Created by Yifa Liang on 2019-08-29.
 */
inline fun dip2px(dpValue: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpValue,
        ArkApp?.getContext()?.getResources()?.getDisplayMetrics()
    ).toInt()

}


