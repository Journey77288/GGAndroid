package io.ganguo.demo.view.activity.base

import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel

/**
 * HFSRecyclerViewModel - Activity
 * Created by leo on 2018/7/30.
 */
abstract class GGHFSRVModelActivity<T : IncludeHfSwipeRecyclerBinding, B : HFSRecyclerViewModel<*>> : GGVModelActivity<T, B>() {


    override fun onViewAttached(viewModel: B) {
        ViewModelHelper.bind(viewModel.headerContainer, viewModel, newHeaderVModel())
    }
}
