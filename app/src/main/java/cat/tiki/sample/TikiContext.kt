package cat.tiki.sample

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * Created by Yifa Liang on 2019-10-23.
 */
class TikiContext: Application() {
    override fun onCreate() {
        super.onCreate()
        _context  = this
        MultiDex.install(this)
    }

    companion object {
        var  _context:Application? = null
        fun getContext(): Context {
            return _context!!
        }

    }

}