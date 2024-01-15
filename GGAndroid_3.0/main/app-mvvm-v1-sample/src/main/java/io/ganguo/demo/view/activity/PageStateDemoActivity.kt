package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityPageStateBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.PageStateDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/09
 *     desc   : 页面状态Demo
 * </pre>
 */
class PageStateDemoActivity : GGVModelActivity<ActivityPageStateBinding, PageStateDemoVModel>() {
    override fun createViewModel(): PageStateDemoVModel {
        return PageStateDemoVModel()
    }


    override fun onViewAttached(viewModel: PageStateDemoVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }


    companion object {

        /**
         * 打开PageStateDemoActivity
         * @param context Context
         */
        @JvmStatic
        fun startActivity(context: Context) {

            context.startActivity(Intent(context, PageStateDemoActivity::class.java))
        }
    }

}