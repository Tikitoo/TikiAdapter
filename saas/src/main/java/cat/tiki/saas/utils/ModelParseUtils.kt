package cat.tiki.saas.utils

import cat.tiki.saas.goods.GoodsDetail
import cat.tiki.saas.goods.GoodsListActivity
import cat.tiki.saas.goods.add.GoodsAddActivity.Static.TYPE_ITEM_IMG
import cat.tiki.saas.goods.add.GoodsImg
import cat.tiki.tikiadapter.TikiBaseImg
import java.util.concurrent.ConcurrentMap

/**
 * Created by Yifa Liang on 2019-11-09.
 */
class ModelParseUtils {
    companion object Model {
        fun getGoodsDetail(serviceData: ConcurrentMap<String, Any?>): GoodsDetail? {
            serviceData?.apply {
                val name: String = get("name") as String
                val desc: String = get("desc") as String
                val price: Int = get("price") as Int
                val objectId: String = get("objectId") as String
                val goodsDetailChild = GoodsDetail(GoodsListActivity.TYPE_GOODS, 1, name, price, desc)
                goodsDetailChild.objectId = objectId
                return goodsDetailChild
            }
            return null
        }

        open fun getGoodsImg(serviceData: ConcurrentMap<String, Any?>): GoodsImg? {
            serviceData?.apply {
                val width = get("width").toString()?.toFloat()
                val height = get("height").toString()?.toFloat()
                val url: String = get("url") as String
                val objectId: String = get("objectId") as String
                val goodsDetail = GoodsImg(TYPE_ITEM_IMG, 1, width, height, url)
                goodsDetail.objectId = objectId
                return goodsDetail
            }
            return null
        }
    }
}