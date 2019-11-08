package cat.tiki.tikirefresh

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import cat.tiki.tikiadapter.TikiBaseModel
import cat.tiki.tikiadapter.TikiRvAdapter
import cat.tiki.tikirefresh.widget.TikiLoadMoreCircleFooter
import cat.tiki.tikirefresh.widget.TikiErrorView
import kotlinx.android.synthetic.main.lib_arch_activity_kotlin_base.*
import cat.tiki.tikirefresh.widget.TikiSmartRefreshLayout

/**
 * Created by Tikitoo on 2019-11-07.
 */
open abstract class TikiBaseRefreshActivity<M: Any, VM: TikiBaseViewModel>: AppCompatActivity(), TikiSmartRefreshLayout.Callback {

    private lateinit var refreshRvLayout: TikiSmartRefreshLayout
    lateinit var dataList: MutableList<out TikiBaseModel>
    lateinit var rvAdapter: TikiRvAdapter<TikiBaseModel>
    open val viewModel: VM? = null
    lateinit var refreshType: TikiBaseViewModel.RefreshType
    //    var loadingView = biz_show_kotlin_base_loading_view
    private var loadingView: TikiLoadMoreCircleFooter? = null
    private var errorView: TikiErrorView? = null
    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lib_arch_activity_kotlin_base)

        refreshRvLayout = biz_show_kotlin_base_refresh_rv_layout
        errorView = biz_show_kotlin_base_error_view
        setRvAdapter()
        loadDataNet()
        initCircleLoadingView()
    }

    private fun setRvAdapter() {
        recyclerView = refreshRvLayout?.getRecyclerView()
        dataList = arrayListOf()
        rvAdapter = TikiRvAdapter(
            applicationContext,
            dataList
        )
        recyclerView?.apply {
            adapter = rvAdapter
            rvAdapter.setRvConfig(false, this)
            adapter?.notifyDataSetChanged()

        }
        refreshRvLayout.setCallback(this)
        refreshRvLayout.setPullToRefresh(true)
        refreshRvLayout.enableLoadMore()
    }


    fun loadDataNet() {
        createLiveData()?.observe(this, Observer {
            it?.apply {
                refreshType = viewModel?.refreshType!!
                when(refreshType) {
                    TikiBaseViewModel.RefreshType.LOADING -> {
                        dismissLoading()
                        refreshRvLayout.refreshComplete()
                        refreshRvLayout.loadMoreComplete()
                    }
                    TikiBaseViewModel.RefreshType.REFRESH -> {
                        refreshRvLayout.refreshComplete()
                        refreshRvLayout.loadMoreComplete()
                    }
                    TikiBaseViewModel.RefreshType.LOADMORE -> {
                        refreshRvLayout.loadMoreComplete()
                    }
                }


                refreshRvLayout?.loadMoreComplete()
                if (it is ApiSuccessResponse<M>) {
                    it.body?.apply {
                        onSuccCallback(it?.body)
                    }
                    errorView?.visibility = View.GONE
//                    LogUtils.d("load data success.....")
                } else if(it is ApiErrorResponse<*>) {
                    // 设置错误数据
                    it?.apply {
                        errorView?.errorText(it.errorMessage)
                        errorView?.visibility = View.VISIBLE
                    }
//                    LogUtils.e("load data failed.....")
                }
            }
        })
    }

    fun updateData(showDataList: MutableList<out TikiBaseModel>) {
        rvAdapter?.apply {
            if (refreshType == TikiBaseViewModel.RefreshType.LOADMORE) {
                dataList?.addAll(showDataList as Collection<Nothing>)
            } else{
                dataList = showDataList
            }
            notifyDataSetChanged()
        }
    }

    override fun onPullDownBegin(currentPercent: Float) {
//        LogUtils.d()
    }

    open fun onFirstLoad() {
//        LogUtils.d()
        showLoading()
        viewModel?.loading()
    }

    override fun onRefreshBegin() {
//        LogUtils.d()
        viewModel?.refresh()
    }

    override fun onLoadMoreBegin() {
//        LogUtils.d()
        viewModel?.loadMore()
    }



    private fun initCircleLoadingView() {
        if (loadingView == null) {
            val layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            loadingView = TikiLoadMoreCircleFooter(this)
            loadingView?.setOnClickListener{

            }
            addContentView(loadingView, layoutParams)
            loadingView?.setVisibility(View.GONE)
        }
    }

    fun showLoading() {
        loadingView?.show()
    }

    fun dismissLoading() {
        loadingView?.dismiss()
    }


    abstract fun onSuccCallback(body: M)
    abstract fun createLiveData(): LiveData<TikiApiResponse<M>>?


}