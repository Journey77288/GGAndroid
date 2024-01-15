package io.ganguo.incubator.view.main

import android.content.Context
import android.content.Intent
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.incubator.databinding.ActivityMainBinding
import io.ganguo.incubator.viewmodel.main.ActivityMainVModel
import io.ganguo.appcompat.Toasts
import io.ganguo.utils.Apps

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : 首页
 * </pre>
 */
class MainActivity : AppCoreVModelActivity<ActivityMainBinding, ActivityMainVModel>() {
    override fun createViewModel(): ActivityMainVModel {
        return ActivityMainVModel()
    }

    override fun onViewAttached(viewModel: ActivityMainVModel) {

    }

    override fun onBackPressed() {
        Apps.exitByDoublePressed(allActivity = ActivityHelper.allActivity())
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
