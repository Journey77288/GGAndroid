package io.ganguo.sample.view.support

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.ActivitySupportSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : Support Sample
 * </pre>
 */
class SupportSampleActivity :AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySupportSampleVModel>() {
    override fun createViewModel(): ActivitySupportSampleVModel {
        return ActivitySupportSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivitySupportSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SupportSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}