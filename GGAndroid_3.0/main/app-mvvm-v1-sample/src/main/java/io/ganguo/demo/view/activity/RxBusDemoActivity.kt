package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityRxbusDemoBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.RxBusDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * RxBusDemo
 * Created by leo on 2018/8/15.
 */
class RxBusDemoActivity : GGVModelActivity<ActivityRxbusDemoBinding, RxBusDemoVModel>() {

    override fun createViewModel(): RxBusDemoVModel {
        return RxBusDemoVModel()
    }

    override fun onViewAttached(viewModel: RxBusDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }


    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, RxBusDemoActivity::class.java)
        }
    }

}
