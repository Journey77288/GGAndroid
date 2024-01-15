package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.CoordinatorAppBarDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.databinding.IncludeCoordinatorLayoutAppBarBinding
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * CoordinatorAppBarVModel Demo
 * Created by leo on 2018/7/13.
 */
class CoordinatorAppBarDemoActivity : GGVModelActivity<IncludeCoordinatorLayoutAppBarBinding, CoordinatorAppBarDemoVModel>() {

    override fun createViewModel(): CoordinatorAppBarDemoVModel {
        return CoordinatorAppBarDemoVModel()
    }

    override fun onViewAttached(viewModel: CoordinatorAppBarDemoVModel) {
        ViewModelHelper.bind(viewModel.headerContainer, viewModel, newHeaderVModel())
    }

    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, CoordinatorAppBarDemoActivity::class.java)
        }
    }

}
