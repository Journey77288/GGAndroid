package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityRxDemoBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.RxDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * Created by Roger on 7/26/16.
 */
class RxDemoActivity : GGVModelActivity<ActivityRxDemoBinding, RxDemoVModel>() {

    override fun createViewModel(): RxDemoVModel {
        return RxDemoVModel()
    }

    override fun onViewAttached(viewModel: RxDemoVModel) {
        ViewModelHelper.bind<HeaderViewModel>(binding.includeHeader, viewModel, newHeaderVModel())
    }


    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, RxDemoActivity::class.java)
        }
    }

}
