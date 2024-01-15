package io.ganguo.sample.view.support

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.ActivityVersionUpdateSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Version Update Sample
 * </pre>
 */
class VersionUpdateSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityVersionUpdateSampleVModel>() {
    override fun createViewModel(): ActivityVersionUpdateSampleVModel {
        return ActivityVersionUpdateSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityVersionUpdateSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, VersionUpdateSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}