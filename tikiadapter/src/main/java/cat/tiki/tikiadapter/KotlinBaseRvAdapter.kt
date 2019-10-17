package cat.tiki.tikiadapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * onBindViewHolder
 * onCreateViewHolder
 * getItemCount
 * Created by Yifa Liang on 2019-08-20.
 */
class KotlinBaseRvAdapter<T: KotlinBaseModel> : RecyclerView.Adapter<KotlinBaseVH<T>> {

    private var isWaterflow: Boolean = false
    var context: Context
    var dataList: MutableList<out T>
    var inflater: LayoutInflater
    // 布局文件layout 和Item 一一对应
    var viewHolderBinder: HashMap<Int, KotlinBaseVHImpl<out T>> = HashMap()

    constructor(context: Context, dataList: MutableList<out T>) : super() {
        this.context = context
        this.inflater = LayoutInflater.from(context)
        this.dataList = dataList
    }

    fun registerItem(layoutItemType: Int, vhImpl: KotlinBaseVHImpl<out T>) {
        viewHolderBinder?.put(layoutItemType, vhImpl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KotlinBaseVH<T> {
        val rootView = inflater?.inflate(viewType, parent, false)
        val vhImpl = viewHolderBinder?.get(viewType)
        return KotlinBaseVH(rootView, vhImpl)
    }

    override fun onBindViewHolder(holder: KotlinBaseVH<T>, position: Int) {
        holder?.apply {
            val item = getItem(position)
            bindData(item)
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

    open fun getItem(position: Int): T {
        val data = dataList?.get(position)
        return data
    }


    open fun setRvConfig(isWaterflow: Boolean = false, recyclerView: RecyclerView?) {
        this.isWaterflow = isWaterflow
        recyclerView?.apply {
            setHasFixedSize(true)
            if (isWaterflow) {
                setSatggerLayoutManager(this)
            } else {
                val lm = GridLayoutManager(this@KotlinBaseRvAdapter.context, 6)
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
                KotlinBaseItemDecoration(isWaterflow, this@KotlinBaseRvAdapter)
            addItemDecoration(kotlinBaseItemDecoration)

        }

    }

    var isItemLoadMore: Boolean = false

    fun getStaggeredGridLM(spantCount: Int): StaggeredGridLayoutManager {
//        this.isStaaggerMixing = true
        val layoutManager = StaggeredGridLayoutManager(spantCount, StaggeredGridLayoutManager.VERTICAL)
        // 防止item 交换位置
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        return layoutManager
    }

    private fun setSatggerLayoutManager(recyclerView: RecyclerView) {
        val staggeredGridLM = getStaggeredGridLM(2)
        recyclerView.setLayoutManager(staggeredGridLM)
//        recyclerView.setItemAnimator(null);
        (recyclerView.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations = false
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
                    Log.d("adapter", "firstVisibleItem: " + firstVisibleItem!![0] + " ： " + firstVisibleItem[1])
                    if (firstVisibleItem != null && firstVisibleItem[0] == 0/* || firstVisibleItem[0] == 1*/) {
                        isItemLoadMore = false
                        recyclerView.post { recyclerView.invalidateItemDecorations() }

                    }

                }
            }

        })
    }


}