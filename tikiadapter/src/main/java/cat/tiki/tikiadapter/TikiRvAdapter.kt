package cat.tiki.tikiadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Tiki 封装RecyclerView 通用Adapter
 * onBindViewHolder
 * onCreateViewHolder
 * getItemCount
 * Created by Yifa Liang on 2019-08-20.
 */
class TikiRvAdapter<T: TikiBaseModel> : RecyclerView.Adapter<TikiBaseVH<T>>,
    View.OnClickListener {

    override fun onClick(v: View?) {
        v?.let {
            itemClickListener?.onItemClick(it, v?.getTag() as Int)
        }
    }

    private var orientation: Int = RecyclerView.VERTICAL
    private var isWaterflow: Boolean = false
    var context: Context
    var dataList: MutableList<out T>
    var inflater: LayoutInflater
    // 布局文件layout 和Item 一一对应
    var viewHolderBinder: HashMap<Int, TikiBaseVHImpl<out T>> = HashMap()
    private var itemClickListener: TikiItemClickListener? = null

    constructor(context: Context, dataList: MutableList<out T>) : super() {
        this.context = context
        this.inflater = LayoutInflater.from(context)
        this.dataList = dataList
    }

    fun setOnItemClick(itemClickListener: TikiItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun registerItem(layoutItemType: Int, vhImpl: TikiBaseVHImpl<out T>) {
        viewHolderBinder?.put(layoutItemType, vhImpl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TikiBaseVH<T> {
        val rootView = inflater?.inflate(viewType, parent, false)
        val vhImpl = viewHolderBinder?.get(viewType)
//        rootView.setOnClickListener(this)

        return TikiBaseVH(rootView, vhImpl)
    }

    override fun onBindViewHolder(holder: TikiBaseVH<T>, position: Int) {
        holder?.apply {
            val item = getItem(position)
            bindData(item)
            view?.setTag(position)
            holder.view?.setOnClickListener(this@TikiRvAdapter)
            holder.view?.setTag(position)
            vhImpl?.clickViewList?.map {
                it?.setOnClickListener(this@TikiRvAdapter)
                it?.setTag(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item != null) {
            return item?.layoutId
        }
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return dataList?.size
    }

    open inline fun getItem(position: Int): T {
        if (position >= 0 && position < dataList?.size) {
           return dataList?.get(position)
        }
        return TikiBaseModel() as T
    }


    open fun setOrientation(orientation: Int) {
        this.orientation = orientation
    }

    open fun setRvConfig(isWaterflow: Boolean = false, recyclerView: RecyclerView?) {
        this.isWaterflow = isWaterflow



        recyclerView?.apply {
//            itemClickListener = TikiItemClickListener(this, this@TikiRvAdapter)
            setHasFixedSize(true)
            if (isWaterflow) {
                setSatggerLayoutManager(this)
            } else {
                val lm = GridLayoutManager(this@TikiRvAdapter.context, 6)
                lm.orientation = orientation
                lm.apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            var column:Int = getItem(position)?.column!!
                            return spanCount / if(column <= 0) 1 else column
                        }
                    }
                }
                layoutManager = lm
            }
            val kotlinBaseItemDecoration =
                TikiBaseItemDecoration(isWaterflow, this@TikiRvAdapter)
            addItemDecoration(kotlinBaseItemDecoration)

        }

    }

    private var isItemLoadMore: Boolean = false

    fun getStaggeredGridLM(spantCount: Int): StaggeredGridLayoutManager {
//        this.isStaaggerMixing = true
        val layoutManager = StaggeredGridLayoutManager(spantCount, StaggeredGridLayoutManager.VERTICAL)
        // 防止item 交换位置
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        return layoutManager
    }

    private fun setSatggerLayoutManager(recyclerView: RecyclerView) {
        val staggeredGridLM = getStaggeredGridLM(2)
        recyclerView.layoutManager = staggeredGridLM
        (recyclerView?.itemAnimator as SimpleItemAnimator)?.supportsChangeAnimations = false
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //                staggeredGridLM.invalidateSpanAssignments();
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (isItemLoadMore) {
                    val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                    var firstVisibleItem: IntArray? = null
                    firstVisibleItem = layoutManager!!.findFirstVisibleItemPositions(firstVisibleItem)
//                    Log.d("adapter", "firstVisibleItem: " + firstVisibleItem!![0] + " ： " + firstVisibleItem[1])
                    if (firstVisibleItem != null && firstVisibleItem[0] == 0) {
                        isItemLoadMore = false
                        recyclerView.post { recyclerView.invalidateItemDecorations() }

                    }

                }
            }

        })
    }


}