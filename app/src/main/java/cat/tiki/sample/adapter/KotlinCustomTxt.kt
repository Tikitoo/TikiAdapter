package cat.tiki.sample.adapter

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import cat.tiki.tikiadapter.KotlinBaseModel

/**
 * Created by Yifa Liang on 2019-09-16.
 */
@Parcelize
@Keep
class KotlinCustomTxt(
        override var layoutId: Int = 0,
        override var column: Int = 1,
        var title: String
) : KotlinBaseModel(column, layoutId), Parcelable {
}


