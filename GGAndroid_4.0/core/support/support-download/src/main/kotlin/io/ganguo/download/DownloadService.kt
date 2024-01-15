package io.ganguo.download

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.ArrayMap
import android.util.ArraySet
import android.util.Log
import com.arialyy.annotations.Download
import com.arialyy.aria.core.task.DownloadTask
import com.blankj.utilcode.util.UriUtils
import io.ganguo.utils.moveToExternalDownloads
import java.io.File
import java.util.concurrent.ConcurrentHashMap

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/12/27
 *     desc   : 下载文件服务
 * </pre>
 */
class DownloadService : Service() {
    private val notifications: ConcurrentHashMap<String ,DownloadTaskNotification> = ConcurrentHashMap()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val downloadUrl = intent?.getStringExtra(Constants.KEY_DOWNLOAD_URL)
        val forceStart = intent?.getBooleanExtra(Constants.KEY_FORCE_START, false)?: false
        if (!downloadUrl.isNullOrBlank()) {
            startDownload(downloadUrl, forceStart)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startDownload(url: String, forceStart: Boolean) {
        notifications[url] = DownloadTaskNotification(applicationContext, url)
        DownloadManager.start(url, forceStart)
    }

    @Download.onTaskStart
    fun onTaskStart(task: DownloadTask?) {

    }

    @Download.onTaskRunning
    fun onTaskRunning(task: DownloadTask?) {
        task?.let {
            val progress = (it.currentProgress * 100 / it.fileSize).toInt()
            val url = it.key
//            Log.i("Tag", "task downloading: ${it.taskName}   progress: $progress")
            notifications[url]?.update(progress)
        }
    }

    @Download.onTaskComplete
    fun onTaskComplete(task: DownloadTask?) {
        task?.let {
            val file = File(task.filePath)
            val url = it.key
            file.moveToExternalDownloads(applicationContext) { file ->
                notifications[url]?.complete(UriUtils.file2Uri(file))
                notifications.remove(url)
            }
        }
    }

    @Download.onTaskFail
    fun onTaskFail(task: DownloadTask?, e: Exception?) {
        task?.let {
            val url = it.key
            notifications[url]?.fail(url)
        }
        e?.let {
            Log.e("Tag", "Download fail: ${it.message}")
        }
    }

    override fun onCreate() {
        super.onCreate()
        DownloadManager.register(this)
    }

    override fun onDestroy() {
        DownloadManager.unregister(this)
        super.onDestroy()
    }
}