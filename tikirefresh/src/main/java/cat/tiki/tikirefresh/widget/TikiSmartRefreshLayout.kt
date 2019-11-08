package cat.tiki.tikirefresh.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import cat.tiki.tikirefresh.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.lib_widget_smart_refresh_layout.view.*


/**
 * Created by Tikitoo on 2019-11-07.
 */
open class TikiSmartRefreshLayout : RelativeLayout {

    private lateinit var refreshRv: RecyclerView
    private lateinit var refreshLayout:SmartRefreshLayout

    constructor(context: Context): super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int?): super(context, attrs,
        defStyleAttr!!
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.lib_widget_smart_refresh_layout, this, true)
                refreshLayout = view.lib_widget_refresh_view_frame
                refreshRv = view.lib_widget_refresh_rcv

        refreshLayout?.apply {
            setOnRefreshListener {
                it.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                callback?.onRefreshBegin()
            }
            setOnLoadMoreListener {
                it.finishLoadMore(2000/*,false*/);//传入false表示刷新失败
                callback?.onLoadMoreBegin()
            }

            /** Header & Footer */
            setRefreshHeader(ClassicsHeader(context))
            setRefreshFooter(ClassicsFooter(context).setDrawableSize(20f))

        }
    }

    fun setPullToRefresh(pullToRefresh: Boolean) {
        if (refreshLayout != null) {
            refreshLayout.setEnableRefresh(pullToRefresh)
        }
    }

    fun enableLoadMore() {
        // TODO: 2019-11-07  新的Footer

    }

    fun refreshComplete() {
//        libWidgetRefreshViewFooter.setLoadEnable(true)
        refreshLayout.finishRefresh()

    }

    fun loadMoreComplete() {
        refreshLayout.finishLoadMore()
//        libWidgetRefreshViewFooter.loadMoreComplete()
//        libWidgetRefreshPbLoadMore.stopRefreshing()
//        libWidgetRefreshPbLoadMore.setVisibility(View.INVISIBLE)
    }

    private var callback: Callback? = null


    open fun getRecyclerView():RecyclerView {
        return refreshRv
    }
    open fun setCallback(callback: Callback) {
        this.callback = callback
    }
    interface Callback {
        /**
         * 开始下拉 自定的Header
         */
        fun onPullDownBegin(currentPercent: Float)

        /**
         * 开始刷新
         */
        fun onRefreshBegin()

        /**
         * 开始加载更多数据
         */
        fun onLoadMoreBegin()
    }

}