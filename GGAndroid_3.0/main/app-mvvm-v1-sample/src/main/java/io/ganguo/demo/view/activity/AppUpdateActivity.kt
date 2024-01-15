package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityAppUpdateBinding
import io.ganguo.demo.viewmodel.activity.AppUpdateViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.base.activity.BaseVModelActivity

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/28
 *     desc   : app update demo
 * </pre>
 */


class AppUpdateActivity : BaseVModelActivity<ActivityAppUpdateBinding, AppUpdateViewModel>() {
    override fun createViewModel(): AppUpdateViewModel {
        return AppUpdateViewModel()
    }


    companion object {
        fun start(context: Context) {
            var intent = Intent(context, AppUpdateActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onViewAttached(viewModel: AppUpdateViewModel) {
    }

}