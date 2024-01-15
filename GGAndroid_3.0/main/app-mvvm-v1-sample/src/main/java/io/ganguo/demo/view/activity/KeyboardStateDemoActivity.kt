package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityKeyBoardStateDemoBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.KeyboardStateDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/27
 *     desc   : KeyBoardWatchHelper
 * </pre>
 */
class KeyboardStateDemoActivity : GGVModelActivity<ActivityKeyBoardStateDemoBinding, KeyboardStateDemoVModel>() {
    override fun createViewModel(): KeyboardStateDemoVModel {
        return KeyboardStateDemoVModel()
    }

    override fun onViewAttached(viewModel: KeyboardStateDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }

    companion object {

        /**
         * 打开 KeyBoardWatchDemoActivity
         * @param context Context
         */
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, KeyboardStateDemoActivity::class.java))
        }
    }

}