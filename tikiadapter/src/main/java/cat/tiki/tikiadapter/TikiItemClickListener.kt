package cat.tiki.tikiadapter

import android.view.View

/**
 * Created by Yifa Liang on 2019-10-23.
 */
interface TikiItemClickListener {
    fun onItemClick(view: View, position: Int)
}