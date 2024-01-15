package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.databinding.ActivityViewPagerDemoBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.ViewPagerDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * Created by leo on 16/7/8.
 * BasePagerViewAdapter - demo
 */
class ViewPagerDemoActivity : GGVModelActivity<ActivityViewPagerDemoBinding, ViewPagerDemoVModel>() {

    override fun createViewModel(): ViewPagerDemoVModel {
        return ViewPagerDemoVModel()
    }

    override fun onViewAttached(viewModel: ViewPagerDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }


    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, ViewPagerDemoActivity::class.java)
        }
    }


}
