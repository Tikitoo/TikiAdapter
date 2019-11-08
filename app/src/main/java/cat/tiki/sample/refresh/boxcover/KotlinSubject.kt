package cat.tiki.sample.refresh.boxcover

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import cat.tiki.tikiadapter.TikiBaseModel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Yifa Liang on 2019-08-20.
 */
@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class KotlinSubject(
        val data: KotlinSubjectData,
        val success: Boolean)
    : Parcelable

@Parcelize
@Keep
data class KotlinSubjectData(
    val boxCovers: MutableList<KotlinBoxCover> = mutableListOf(),
    val isShow: Boolean)
    : Parcelable

@Parcelize
@Keep
data class KotlinBoxCover (
        override var layoutId: Int = 0,
        override var column: Int = 1,
        override var width: Float,
        override var height: Float,
        val name: String,
        val slogan: String,
        val headImg: String,
        val goTo: GoTo)
    : TikiBaseModel(column, layoutId), Parcelable {

}

@Parcelize
@Keep
open class GoTo: Parcelable {

}

@Parcelize
@Keep
data class KotlinBoxHeader(
        override var layoutId: Int = 0,
        override var column: Int = 1,
        val title:String): TikiBaseModel(column, layoutId), Parcelable {

}


