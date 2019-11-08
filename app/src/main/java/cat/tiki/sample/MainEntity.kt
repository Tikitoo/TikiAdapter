package cat.tiki.sample

import cat.tiki.tikiadapter.TikiBaseModel

/**
 * Created by Yifa Liang on 2019-10-23.
 */
data class MainEntity(
    override var layoutId: Int = 0,
    override var column: Int = 1,
    var txt: String,
    var intentType: Int
    ): TikiBaseModel() {

}