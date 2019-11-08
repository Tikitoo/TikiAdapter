package cat.tiki.tikirefresh.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * Created by Yifa Liang on 2019-08-26.
 */

inline fun <reified T : ViewModel> FragmentActivity.viewModel() =
        lazy { ViewModelProviders.of(this).get(T::class.java) }

inline fun <reified T : ViewModel> FragmentActivity.viewModel(crossinline block: T.() -> Unit) =
        lazy {
            ViewModelProviders.of(this).get(T::class.java).apply(block)
        }

inline fun <reified T : ViewModel> Fragment.viewModel() =
        lazy { ViewModelProviders.of(this).get(T::class.java) }

