package cat.tiki.tikiadapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by Yifa Liang on 2019-09-06.
 */
class TikiBaseItemDecoration<T : TikiBaseModel>(val context: Context, val isWaterflow: Boolean, val rvAdapter: TikiRvAdapter<T>): RecyclerView.ItemDecoration() {

    var flagIndex: Int = 0
    private var mDivider: Drawable? = null
    var drawPaint = Paint()

    init {
        drawPaint.setColor(context.getResources().getColor(R.color.tiki_adapter_rv_draw_color))
        drawPaint.setAntiAlias(true)

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight


        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val childPosition = parent.getChildAdapterPosition(child)
            val wrapper: T? = getItemByPosition(childPosition)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val topStart: Int = child.top + params.topMargin - wrapper?.drawRect?.top!!
            val topEnd: Int = child.top + params.topMargin

            val bottomStart = child.bottom + params.bottomMargin
            val bottomEnd = child.bottom + params.bottomMargin + wrapper?.drawRect?.bottom!!
            if (wrapper != null) {

                // draw top
                if (topStart != topEnd) {
                    c.drawRect(left.toFloat(), topStart.toFloat(), right.toFloat(), topEnd.toFloat(), drawPaint)
                }

                // draw bottom
                if (bottomStart != bottomEnd) {
                    c.drawRect(left.toFloat(), bottomStart.toFloat(), right.toFloat(), bottomEnd.toFloat(), drawPaint)
                }
            }
        }
    }

    private fun getItemByPosition(childPosition: Int): T? {
        return rvAdapter?.getItem(childPosition)
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        val item = rvAdapter?.getItem(position)
        val column = item?.column


        /** 瀑布流 */
        if (isWaterflow) {
            setWaterFall(item, outRect, view, parent, position)
            return
        }

        var previousItem:T? = null
        if (position > 1) {
            previousItem = rvAdapter?.getItem(position - 1)
        }
        val previousColumn = previousItem?.column

        if (column != previousColumn) {
            flagIndex = position
        }
        item?.rect?.apply {
            var tempLeft: Int = side
            var tempRight: Int = side

            if (column == 1) {
                tempLeft = side
                tempRight = side
            } else if (column == 2) {
                val isLeft: Boolean = (position - flagIndex) % 2 == 0
                tempLeft = if (isLeft) side else center
                tempRight = if (isLeft) center else side
            } else if (column == 3) {
                val surplusIndex = (position - flagIndex) % column
                if (surplusIndex == 0) {
                    tempLeft = side
                    tempRight = center
                } else if (surplusIndex == 1) {
                    tempLeft = center
                    tempRight = center
                } else if (surplusIndex == 2) {
                    tempLeft = center
                    tempRight = side
                }
            }

            // TODO: 2019-09-10 如果一行多列，怎么书写

            outRect.left = tempLeft
            outRect.right = tempRight
            outRect.top = top
            outRect.bottom = bottom
        }
    }


    val spanCount: Int = 2
    private fun setWaterFall(item: T, outRect: Rect, view: View, parent: RecyclerView, itemPosition: Int) {

        val tempLayoutParams = view?.layoutParams
        if (tempLayoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val spanIndex = tempLayoutParams.spanIndex
            val layoutPosition = tempLayoutParams.viewLayoutPosition
            val itemCount = parent.adapter!!.itemCount


            val left:Int
            val right: Int
            val isLeft = spanIndex % spanCount == 0
            if (isLeft) {
                left = item?.rect?.side
                right = item?.rect?.center
            } else{
                right = item?.rect?.side
                left = item?.rect?.center
            }

            val topEdge = spanIndex < spanCount
            val bottomEdge = layoutPosition >= itemCount - spanCount

            // TODO: 2019-10-17  
            Log.d("decoration", "left: " + left + "; right: " + right
                    + "; top: " + topEdge + "; bottom: " + bottomEdge)

            val top = item?.rect?.top
            val bottom = item?.rect?.bottom


            Log.w("decoration", "top: " + top + "; bottom: " + bottom + "; left: " + left + "; right: " + right)

            outRect.left = left
            outRect.top = if (topEdge) top else top
            outRect.right = right
            outRect.bottom = if (bottomEdge) bottom else bottom

        }
    }
}