package io.ganguo.sample.view.support.download

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.download.ActivitySingleTaskDownloadVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/12/27
 *     desc   : Single Task Download Sample
 * </pre>
 */
class SingleTaskDownloadActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySingleTaskDownloadVModel>() {
    override fun createViewModel(): ActivitySingleTaskDownloadVModel {
        return ActivitySingleTaskDownloadVModel()
    }

    override fun onViewAttached(viewModel: ActivitySingleTaskDownloadVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, SingleTaskDownloadActivity::class.java))
        }
    }
}