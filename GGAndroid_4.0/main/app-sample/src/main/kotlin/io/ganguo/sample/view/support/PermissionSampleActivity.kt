package io.ganguo.sample.view.support

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.ActivityPermissionSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Permission Sample
 * </pre>
 */
class PermissionSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityPermissionSampleVModel>() {
    override fun createViewModel(): ActivityPermissionSampleVModel {
        return ActivityPermissionSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityPermissionSampleVModel) {

    }
    
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, PermissionSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}