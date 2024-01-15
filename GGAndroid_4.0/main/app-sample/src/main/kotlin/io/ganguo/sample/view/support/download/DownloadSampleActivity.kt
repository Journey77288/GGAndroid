package io.ganguo.sample.view.support.download

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.download.ActivityDownloadSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/11/29
 *     desc   : Download Sample
 * </pre>
 */
class DownloadSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityDownloadSampleVModel>() {
    override fun createViewModel(): ActivityDownloadSampleVModel {
        return ActivityDownloadSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityDownloadSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, DownloadSampleActivity::class.java))
        }
    }
}