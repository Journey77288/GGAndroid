package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityLoadingHelperDemoBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.LoadingHelperDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : LoadingHelper 工具类
 * </pre>
 */
class LoadingHelperDemoActivity : GGVModelActivity<ActivityLoadingHelperDemoBinding, LoadingHelperDemoVModel>() {
    override fun createViewModel(): LoadingHelperDemoVModel {
        return LoadingHelperDemoVModel()
    }


    override fun onViewAttached(viewModel: LoadingHelperDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, LoadingHelperDemoActivity::class.java)
        }
    }
}