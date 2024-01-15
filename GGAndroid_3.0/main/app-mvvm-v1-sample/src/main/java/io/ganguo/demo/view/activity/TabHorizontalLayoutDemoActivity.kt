package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.databinding.ActivityTabLayoutDemoBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.TabHorizontalLayoutDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * Created by leo on 16/7/12.
 * TabLayoutDemo - activity
 */
class TabHorizontalLayoutDemoActivity : GGVModelActivity<ActivityTabLayoutDemoBinding, TabHorizontalLayoutDemoVModel>() {

    override fun createViewModel(): TabHorizontalLayoutDemoVModel {
        return TabHorizontalLayoutDemoVModel()
    }


    override fun onViewAttached(viewModel: TabHorizontalLayoutDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }


    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, TabHorizontalLayoutDemoActivity::class.java)
        }
    }
}
