package io.ganguo.demo.view.activity.base

import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel

/**
 * HFRecyclerViewModel -- Activity
 * Created by leo on 2018/10/27.
 */
abstract class GGHFRVModelActivity<T : IncludeHfRecyclerBinding, B : HFRecyclerViewModel<*>> : GGVModelActivity<T, B>() {
    override fun onViewAttached(viewModel: B) {
        ViewModelHelper.bind(viewModel.headerContainer, viewModel, newHeaderVModel())
    }

}
