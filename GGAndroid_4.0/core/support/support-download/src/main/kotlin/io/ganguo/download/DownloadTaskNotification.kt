package io.ganguo.download

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import com.blankj.utilcode.util.NotificationUtils

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/12/27
 *     desc   : 下载任务通知
 * </pre>
 */
class DownloadTaskNotification(private val context: Context, private val url: String) {
    companion object {
        const val PROGRESS_MAX = 100
    }
    private val notificationId: Int by lazy { url.hashCode() }
    private val taskName: String by lazy { URLUtil.guessFileName(url, null, null) }

    init {
        showDownloadingNotification(0)
    }

    /**
     * 更新下载进度
     *
     * @param progress Int 进度
     */
    fun update(progress: Int) {
        showDownloadingNotification(progress)
    }

    /**
     * 下载完成
     */
    fun complete(uri: Uri) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = uri
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        NotificationUtils.notify(notificationId) {
            it.setGroup(taskName)
            it.setSmallIcon(R.drawable.ic_launcher)
            it.setContentTitle(context.getString(R.string.str_download_complete, taskName))
            it.setContentIntent(pendingIntent)
            it.setSilent(true)
        }
    }

    /**
     * 下载失败
     */
    fun fail(url: String) {
        val intent = Intent(context, DownloadService::class.java)
        intent.putExtra(Constants.KEY_DOWNLOAD_URL, url)
        NotificationUtils.notify(notificationId) {
            it.setGroup(taskName)
            it.setSmallIcon(R.drawable.ic_launcher)
            it.setContentTitle(context.getString(R.string.str_download_fail, taskName))
            it.addAction(0, context.getString(R.string.str_retry), PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_IMMUTABLE))
            it.setSilent(true)
        }
    }

    /**
     * 显示下载中通知
     *
     * @param progress Int 进度
     */
    private fun showDownloadingNotification(progress: Int) {
        NotificationUtils.notify(notificationId) {
            it.setGroup(taskName)
            it.setSmallIcon(R.drawable.ic_launcher)
            it.setContentTitle(context.getString(R.string.str_downloading, taskName))
            it.setProgress(PROGRESS_MAX, progress, false)
            it.setSilent(true)
        }
    }
}