package io.ganguo.sample.view.service

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.service.ActivityPaySampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Pay Sample
 * </pre>
 */
class PaySampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityPaySampleVModel>() {
    override fun createViewModel(): ActivityPaySampleVModel {
        return ActivityPaySampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityPaySampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, PaySampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}