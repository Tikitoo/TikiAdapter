package cat.tiki.tikiadapter

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Yifa Liang on 2019-12-11.
 */
class TikiItemClickListener2(
    recyclerView: RecyclerView,
    val listener: OnTikiItemClickListener
) : RecyclerView.OnItemTouchListener {
    private var gestureDetector: GestureDetectorCompat

    init {
        gestureDetector = GestureDetectorCompat(
            recyclerView?.context!!,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }
            })
        recyclerView.addOnItemTouchListener(this)
    }


    interface OnTikiItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && listener != null && gestureDetector?.onTouchEvent(e)) {
            listener.onItemClick(childView, view.getChildPosition(childView!!))
            return true
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }
}