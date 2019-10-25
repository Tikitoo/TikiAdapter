package cat.tiki.sample.adapter.waterflow

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
class TikiWaterflowTxt(
    override var layoutId: Int = 0,
    override var column: Int = 1,
    var title: String
) : KotlinBaseModel(column, layoutId), Parcelable {



//    https://i.loli.net/2019/10/22/Uyjg8DEABRkmW6f.jpg 522 348
//    https://i.loli.net/2019/10/22/xNWzOsH9f2k1Yti.jpg 464 348
//    https://i.loli.net/2019/10/22/KhSm4Edi7cfWLvY.jpg 519 348
//    https://i.loli.net/2019/10/22/MHsLz3GeROhPCwu.jpg 902 348

    companion object {
        private var waterflowList = mutableListOf<TikiWaterflowTxt>()
        private var imgList = mutableListOf<TikiBaseImg>()

        fun getData(): MutableList<TikiWaterflowTxt> {
            imgList.add(TikiBaseImg(522f, 348f, createImgUrl("Uyjg8DEABRkmW6f")))
            imgList.add(TikiBaseImg(464f, 348f, createImgUrl("xNWzOsH9f2k1Yti")))
            imgList.add(TikiBaseImg(519f, 348f, createImgUrl("KhSm4Edi7cfWLvY")))
            imgList.add(TikiBaseImg(902f, 348f, createImgUrl("MHsLz3GeROhPCwu")))

            for (i in 1..20) {
                val tikiWaterflowTxt = TikiWaterflowTxt(TikiWaterflowActivity.CUSTOM_TXT,2, "hello" + i)
                val tempBaseImg = imgList.get((0..3).random())
                tikiWaterflowTxt.setBaseImg(tempBaseImg)
                waterflowList.add(tikiWaterflowTxt)
            }
            return waterflowList
        }
        private fun createImgUrl(md5: String):String {
            return "https://i.loli.net/2019/10/22/" + md5 + ".jpg"
        }
    }





}


