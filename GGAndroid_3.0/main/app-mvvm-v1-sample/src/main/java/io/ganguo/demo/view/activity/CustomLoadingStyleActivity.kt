package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.CustomLoadingStyleVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * 自定义HFSRecyclerViewModel页面Loading
 * Created by leo on 2018/9/14.
 */
class CustomLoadingStyleActivity : GGVModelActivity<IncludeHfSwipeRecyclerBinding, CustomLoadingStyleVModel>() {

    override fun createViewModel(): CustomLoadingStyleVModel {
        return CustomLoadingStyleVModel()
    }


    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, CustomLoadingStyleActivity::class.java)
        }
    }

    override fun onViewAttached(viewModel: CustomLoadingStyleVModel) {
        ViewModelHelper.bind(binding.llyHeader, viewModel, newHeaderVModel())
    }
}
