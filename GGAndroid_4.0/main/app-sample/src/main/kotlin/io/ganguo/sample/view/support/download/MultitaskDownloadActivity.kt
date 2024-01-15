package io.ganguo.sample.view.support.download

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.download.ActivityMultitaskDownloadVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/12/27
 *     desc   : Multitask Download Sample
 * </pre>
 */
class MultitaskDownloadActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityMultitaskDownloadVModel>() {
    override fun createViewModel(): ActivityMultitaskDownloadVModel {
        return ActivityMultitaskDownloadVModel()
    }

    override fun onViewAttached(viewModel: ActivityMultitaskDownloadVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, MultitaskDownloadActivity::class.java))
        }
    }
}