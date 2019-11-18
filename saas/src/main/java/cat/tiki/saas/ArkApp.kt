package cat.tiki.saas

import android.app.Application
import android.content.Context
import cat.tiki.saas.utils.LeanInit
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by Yifa Liang on 2019-11-08.
 */
class ArkApp: Application() {
    override fun onCreate() {
        super.onCreate()
        LeanInit.init()
        ArkApp._context = this

        RxJavaPlugins.setErrorHandler {
            //异常处理
            println("it: " + it.message)
        }
    }

    companion object {
        var  _context:Application? = null
        fun getContext(): Context {
            return _context!!
        }

    }
}