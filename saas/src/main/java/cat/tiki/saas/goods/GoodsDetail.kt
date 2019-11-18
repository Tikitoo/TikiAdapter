package cat.tiki.saas.goods

import cat.tiki.tikiadapter.TikiBaseModel

/**
 * Created by Yifa Liang on 2019-11-09.
 */
data class GoodsDetail(
    override var layoutId: Int = 0,
    override var column: Int = 1,
    open val name: String,
    open val price: Int,
    open val desc: String): TikiBaseModel(layoutId, column) {
    open var objectId: String? = null
}

