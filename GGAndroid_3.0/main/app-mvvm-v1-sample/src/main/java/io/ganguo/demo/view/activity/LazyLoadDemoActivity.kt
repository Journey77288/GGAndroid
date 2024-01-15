package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityLazyLoadDemoBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.LazyLoadDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : 页面懒加载Demo
 * </pre>
 */
class LazyLoadDemoActivity : GGVModelActivity<ActivityLazyLoadDemoBinding, LazyLoadDemoVModel>() {
    override fun createViewModel(): LazyLoadDemoVModel {
        return LazyLoadDemoVModel()
    }


    override fun onViewAttached(viewModel: LazyLoadDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, LazyLoadDemoActivity::class.java)
        }
    }
}