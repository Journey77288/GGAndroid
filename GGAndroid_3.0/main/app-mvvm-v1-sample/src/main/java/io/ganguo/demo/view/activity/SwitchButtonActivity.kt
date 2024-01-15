package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivitySwitchButtonBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.SwitchButtonDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/09
 *     desc   : SwitchButton Demo
 * </pre>
 */
class SwitchButtonActivity : GGVModelActivity<ActivitySwitchButtonBinding, SwitchButtonDemoVModel>() {
    override fun createViewModel(): SwitchButtonDemoVModel {
        return SwitchButtonDemoVModel()
    }


    override fun onViewAttached(viewModel: SwitchButtonDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }

    companion object {

        /**
         * 打开SwitchButtonActivity
         * @param context Context
         */
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SwitchButtonActivity::class.java))
        }
    }
}