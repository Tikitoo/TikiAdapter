package cat.tiki.tikirefresh

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * T 做一个基类
 * Created by Tikitoo on 2019-11-07.
 */
open class TikiBaseViewModel : ViewModel {
    open var refreshType: RefreshType =
        RefreshType.LOADING
    open var page = MutableLiveData<Int>()

    constructor() : super()

    companion object {
        open fun <S> getService(service: Class<S>): S {
            return TikiRetrofitClient().getRetrofit().create(service)
        }
    }

    open fun loadMore() {
        page.value = (page.value ?: 0) + 1
        refreshType = RefreshType.LOADMORE
    }

    open fun refresh() {
        page.value  = 1
        refreshType = RefreshType.REFRESH
    }

    open fun loading() {
        page.value  = 1
        refreshType = RefreshType.LOADING

    }

    enum class RefreshType {
        REFRESH, LOADMORE, LOADING
    }


}