package io.ganguo.viewmodel.pack.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.R
import io.ganguo.viewmodel.pack.common.base.BaseHFRViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding

/**
 * Header Footer RecyclerView 组合ViewModel (不带下拉刷新)
 * Created by Roger on 6/22/16.
 */
abstract class HFRecyclerViewModel<T : ViewInterface<IncludeHfRecyclerBinding>> : BaseHFRViewModel<IncludeHfRecyclerBinding, T>() {
    override val layoutId: Int by lazy {
        R.layout.include_hf_recycler
    }
    override val headerContainer: ViewGroup by lazy {
        viewInterface.binding.llyHeader
    }

    override val footerContainer: ViewGroup by lazy {
        viewInterface.binding.llyFooter
    }

    override val statusContainer: ViewGroup by lazy {
        viewInterface.binding.llyState
    }

    override val recyclerRootView: ViewDataBinding by lazy {
        viewInterface.binding.includeRecycler
    }

}