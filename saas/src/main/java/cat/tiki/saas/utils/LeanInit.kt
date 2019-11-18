package cat.tiki.saas.utils

import android.content.Context
import android.net.Uri
import cn.leancloud.AVLogger
import cn.leancloud.AVOSCloud
import java.lang.reflect.Modifier
import cn.leancloud.AVFile
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.HashMap
import android.provider.MediaStore
import cat.tiki.tikiadapter.TikiBaseImg
import cn.leancloud.AVObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Yifa Liang on 2019-11-08.
 */
class LeanInit {

    companion object Common {
        private val appId: String= "HTKwP2DFbFrCiForJrJlitt3-gzGzoHsz"
        private val appKey: String= "gFl790J3K6WLY9GovgjAx7Pz"

        open fun init() {
            // 在 AVOSCloud.initialize 之前调用
            AVOSCloud.setLogLevel(AVLogger.Level.DEBUG) // or AVOSCloud.setLogLevel(AVLogger.Level.VERBOSE);
            AVOSCloud.initialize(appId, appKey)
        }

        @Throws(Exception::class)
        open fun mapToObject(map: Map<String, Any>?, beanClass: Class<*>): Any? {
            if (map == null)
                return null

            val obj = beanClass.newInstance()

            val fields = obj.javaClass.declaredFields
            for (field in fields) {
                val mod = field.modifiers
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue
                }

                field.isAccessible = true
                field.set(obj, map[field.name])
            }

            return obj
        }

        @Throws(Exception::class)
        fun objectToMap(obj: Any?): Map<String, Any>? {
            if (obj == null) {
                return null
            }

            val map = HashMap<String, Any>()
            val declaredFields = obj.javaClass.declaredFields
            for (field in declaredFields) {
                field.isAccessible = true
                map[field.name] = field.get(obj)
            }
            return map
        }

        open fun uploadImg(name: String?, baseImg: TikiBaseImg, objId: String?) {
            val imgNameMd5 = UUID.randomUUID().toString().replace("-", "").toLowerCase()
            val file = AVFile.withAbsoluteLocalPath(imgNameMd5, baseImg.md5)
            file.saveInBackground()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<AVFile> {
                override fun onSubscribe(disposable: Disposable) {}
                override fun onNext(file: AVFile) {
                    println("文件保存完成。objectId：" + file.objectId)
                    print("lean img: " + file.url + file.name)

                    baseImg.md5 = file.url
                    createArkImg(baseImg, objId!!)

                }

                override fun onError(throwable: Throwable) {
                    // 保存失败，可能是文件无法被读取，或者上传过程中出现问题
                }

                override fun onComplete() {}
            })
        }

        private fun createArkImg(img: TikiBaseImg, goodsId: String) {
            val arkImg = AVObject("ArkImg")
            arkImg.put("width", img.width)
            arkImg.put("height", img.height)
            arkImg.put("url", img.md5)

            val goods = AVObject.createWithoutData("Goods", goodsId)
            arkImg.put("parent", goods)
            arkImg.save()


        }


        open fun getRealPathFromURI(context: Context, contentURI: Uri): String {
            val result: String
            val cursor = context.getContentResolver().query(contentURI, null, null, null, null)
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath()
            } else {
                cursor!!.moveToFirst()
                val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor!!.getString(idx)
                cursor!!.close()
            }
            return result
        }

        /** 图片
         * url base http://lc-htkwp2df.cn-n1.lcfile.com/
         */
    }



}