package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.databinding.ActivityWebViewDemoBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.WebViewDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * WebViewModel - Demo
 * Created by leo on 2018/11/12.
 */
class WebViewDemoActivity : GGVModelActivity<ActivityWebViewDemoBinding, WebViewDemoVModel>() {

    override fun createViewModel(): WebViewDemoVModel {
        return WebViewDemoVModel()
    }


    override fun onViewAttached(viewModel: WebViewDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }


    override fun onBackPressed() {
        if (!viewModel.urlWebViewModel.isBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, WebViewDemoActivity::class.java)
        }
    }
}
