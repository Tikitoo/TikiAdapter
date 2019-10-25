package cat.tiki.sample.adapter.jd

import android.os.Parcelable
import androidx.annotation.Keep
import cat.tiki.tikiadapter.TikiBaseImg
import kotlinx.android.parcel.Parcelize
import cat.tiki.tikiadapter.KotlinBaseModel

/**
 * Created by Yifa Liang on 2019-09-16.
 */
@Parcelize
@Keep
data class JDDetailModel(
override var layoutId: Int = 0,
override var column: Int = 1,
var title: String
) : KotlinBaseModel(column, layoutId), Parcelable {
}

// 顶部图片轮滑介绍
data class JDBanner(
    override var layoutId: Int = 0,
    override var column: Int = 1,
    val imgList: MutableList<TikiBaseImg>
    ): KotlinBaseModel(column, layoutId), Parcelable  {
        lateinit var title: String
        var content: String = ""
        var price: Float = 0.0f


}


// Sku 选择
data class JDSku(
    override var layoutId: Int = 0,
    override var column: Int = 1
    ): KotlinBaseModel(column, layoutId), Parcelable  {

    }

// 评论
class JDComment(
    override var layoutId: Int = 0,
    override var column: Int = 1
    ): KotlinBaseModel(column, layoutId), Parcelable  {

    }

// 介绍的标题
class JDTitle(
    override var layoutId: Int = 0,
    override var column: Int = 1
    ): KotlinBaseModel(column, layoutId), Parcelable  {

    }

// 推荐的商品
class JDItemGoods(
    override var layoutId: Int = 0,
    override var column: Int = 1
    ): KotlinBaseModel(column, layoutId), Parcelable  {

    }







