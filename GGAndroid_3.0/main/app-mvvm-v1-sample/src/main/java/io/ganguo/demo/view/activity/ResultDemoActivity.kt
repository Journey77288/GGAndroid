package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityResultBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.ResultVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * View model sendMessage & handle Message demo
 * 利用Handler实现onActivityResult,用于ViewModel之间通信
 * Created by Roger on 6/7/16.
 */
class ResultDemoActivity : GGVModelActivity<ActivityResultBinding, ResultVModel>() {

    override fun createViewModel(): ResultVModel {
        return ResultVModel()
    }


    override fun onViewAttached(viewModel: ResultVModel) {
        ViewModelHelper.bind<HeaderViewModel>(binding.includeHeader, viewModel, newHeaderVModel())
    }

    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, ResultDemoActivity::class.java)
        }
    }

}
