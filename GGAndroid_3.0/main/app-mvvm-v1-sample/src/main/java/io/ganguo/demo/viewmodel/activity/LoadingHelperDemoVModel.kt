package io.ganguo.demo.viewmodel.activity

import android.view.View
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityLoadingHelperDemoBinding
import io.ganguo.demo.viewmodel.page.TabPageViewModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.state.loading.ILoadingHandler
import io.ganguo.state.loading.LoadingHelper
import io.ganguo.utils.util.postDelayed
import io.ganguo.viewmodel.pack.common.loading.LoadingMaterialProgressVModel
import io.ganguo.viewmodel.pack.helper.LoadingVModelHelper.Companion.bindLoadingView
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : LoadingHelper Demo
 * </pre>
 */
class LoadingHelperDemoVModel : BaseViewModel<ActivityInterface<ActivityLoadingHelperDemoBinding>>(), ILoadingHandler {
    override val layoutId: Int by lazy {
        R.layout.activity_loading_helper_demo
    }
    override val loadingHelper: LoadingHelper by lazy {
        bindLoadingView(viewInterface.binding.flLoading, this, LoadingMaterialProgressVModel())
    }

    override fun onViewAttached(view: View) {
        showLoading()
        ViewModelHelper.bind(viewInterface.binding.includeHfSw, this, TabPageViewModel())
        //模拟网络请求，延时2秒后，隐藏Loading
        context.postDelayed(1000) { hideLoading() }

    }


}