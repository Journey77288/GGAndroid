package io.ganguo.sample.viewmodel.support.download

import android.view.View
import android.view.ViewGroup
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.appcompat.Toasts.Companion.show
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.download.DownloadManager
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.permission.requestStoragePermissions
import io.ganguo.sample.R
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.ganguo.utils.Notifications

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/12/27
 *     desc   : Download With Notification Sample ViewModel
 * </pre>
 */
class ActivityNotificationDownloadVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    companion object {
        const val TEST_URL = "https://y.qq.com/download/import/QQMusic-import-1.2.1.zip"
    }

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_download_with_notification))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            add(ItemMenuVModel.create(getString(R.string.str_start_download)) {
                startDownloadService()
            })
        }
    }

    /**
     * 开始下载服务
     */
    private fun startDownloadService() {
        ActivityHelper.currentActivity()?.requestStoragePermissions {
            if (it.success) {
                if (Notifications.isAppNotificationEnabled(context)) {
                    DownloadManager.startWithNotice(context, TEST_URL, true)
                } else {
                    Notifications.openAppNotification(context)
                }
            } else {
                show(R.string.str_get_permission_fail)
            }
        }
    }
}