package cat.tiki.saas.goods.add

import cat.tiki.tikiadapter.TikiBaseImg

/**
 * Created by Yifa Liang on 2019-11-10.
 */
class GoodsImg(
    override var layoutId: Int = 0,
    override var column: Int = 1,
    override var width: Float = 0f,
    override var height: Float = 0f,
    override var md5: String): TikiBaseImg(width, height, md5) {
    lateinit var objectId: String
}