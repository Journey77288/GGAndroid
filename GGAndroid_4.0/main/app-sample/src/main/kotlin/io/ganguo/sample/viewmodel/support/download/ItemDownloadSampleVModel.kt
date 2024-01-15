package io.ganguo.sample.viewmodel.support.download

import android.util.Log
import android.view.View
import android.webkit.URLUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.arialyy.annotations.Download
import com.arialyy.aria.core.task.DownloadTask
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.appcompat.Toasts.Companion.show
import io.ganguo.download.DownloadManager
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.permission.requestStoragePermissions
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemDownloadSampleBinding
import io.ganguo.utils.moveToExternalDownloads
import java.io.File
import java.lang.Exception

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/11/29
 *     desc   : Download Sample ItemViewModel
 * </pre>
 */
class ItemDownloadSampleVModel(private val downloadUrl: String) : ViewModel<ViewInterface<ItemDownloadSampleBinding>>() {
    override val layoutId: Int = R.layout.item_download_sample
    val name = ObservableField<String>()
    val speed = ObservableField<String>()
    val downloadProgress = ObservableInt()
    val isPaused = ObservableBoolean()

    init {
        this.name.set(URLUtil.guessFileName(downloadUrl, null, null))
        this.speed.set("")
        this.downloadProgress.set(0)
        this.isPaused.set(true)
    }

    override fun onViewAttached(view: View) {
        DownloadManager.register(this)
    }

    @Download.onTaskStart
    fun onTaskStart(task: DownloadTask?) {
        if (task?.key == downloadUrl) {
            isPaused.set(false)
        }
    }

    @Download.onTaskResume
    fun onTaskResume(task: DownloadTask?) {
        if (task?.key == downloadUrl) {
            isPaused.set(false)
        }
    }

    @Download.onTaskRunning
    fun onTaskRunning(task: DownloadTask?) {
        Log.i("Tag", "onTaskRunning: ${task?.taskName}")
        if (task?.key == downloadUrl) {
            downloadProgress.set(task.percent)
            speed.set(task.convertSpeed)
        }
    }

    @Download.onTaskStop
    fun onTaskStop(task: DownloadTask?) {
        if (task?.key == downloadUrl) {
            isPaused.set(true)
        }
    }

    @Download.onTaskComplete
    fun onTaskComplete(task: DownloadTask?) {
        if (task?.key == downloadUrl) {
            downloadProgress.set(100)
            isPaused.set(true)
            val file = File(task.filePath)
            file.moveToExternalDownloads(context) {
                show("Download Complete: ${it.absolutePath}")
                Log.i("Tag", "Download Complete: ${it.absolutePath}")
            }
        }
    }

    @Download.onTaskFail
    fun onTaskFail(task: DownloadTask?, e: Exception?) {
        if (task?.key == downloadUrl) {
            isPaused.set(true)
            show("Download Fail")
        }
    }

    @Download.onTaskCancel
    fun onTaskCancel(task: DownloadTask?) {
        if (task?.key == downloadUrl) {
            downloadProgress.set(0)
            isPaused.set(true)
        }
    }

    /**
     * 点击开始下载
     */
    fun onStartClick(view: View) {
        if (isPaused.get()) {
            ActivityHelper.currentActivity()?.requestStoragePermissions {
                if (it.success) {
                    DownloadManager.start(downloadUrl, true)
                } else {
                    show(R.string.str_get_permission_fail)
                }
            }
        } else {
            DownloadManager.pause(downloadUrl)
        }
    }

    /**
     * 点击取消下载
     */
    fun onCancelClick(view: View) {
        DownloadManager.cancel(downloadUrl)
    }

    override fun onDestroy() {
        DownloadManager.unregister(this)
        super.onDestroy()
    }
}