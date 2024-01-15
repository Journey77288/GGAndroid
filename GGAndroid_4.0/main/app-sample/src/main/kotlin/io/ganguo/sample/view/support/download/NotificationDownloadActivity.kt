package io.ganguo.sample.view.support.download

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.download.ActivityNotificationDownloadVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/12/27
 *     desc   : Download With Notification Sample
 * </pre>
 */
class NotificationDownloadActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityNotificationDownloadVModel>() {
    override fun createViewModel(): ActivityNotificationDownloadVModel {
        return ActivityNotificationDownloadVModel()
    }

    override fun onViewAttached(viewModel: ActivityNotificationDownloadVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, NotificationDownloadActivity::class.java))
        }
    }
}