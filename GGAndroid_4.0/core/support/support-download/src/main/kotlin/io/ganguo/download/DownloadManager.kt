package io.ganguo.download

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.URLUtil
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadEntity
import com.arialyy.aria.core.download.target.HttpNormalTarget
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import java.io.File

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/11/29
 *     desc   : 文件下载管理类
 * </pre>
 */
object DownloadManager {

    /**
     * 初始化
     * 需要在Application初始化一次
     *
     * @param context Context
     */
    @JvmStatic
    fun init(context: Context) {
        Aria.init(context)
    }

    @JvmStatic
    fun register(obj: Any) {
        Aria.download(obj).register()
    }

    @JvmStatic
    fun unregister(obj: Any) {
        Aria.download(obj).unRegister()
    }

    /**
     * 开始下载
     *
     * @param url String 下载文件链接
     * @param forceStart Boolean 是否强制重新下载
     */
    @JvmStatic
    fun start(url: String, forceStart: Boolean = false) {
        if (url.isBlank()) {
            return
        }
        val task = getTask(url)
        if (task == null) {
            createTask(url)
        } else {
            loadTask(task.id).resume(forceStart)
        }
    }

    /**
     * 带通知开始下载
     *
     * @param context Context
     * @param url String 下载文件链接
     * @param forceStart Boolean 是否强制重新下载
     */
    @JvmStatic
    fun startWithNotice(context: Context, url: String, forceStart: Boolean = false) {
        val intent = Intent(context, DownloadService::class.java)
        intent.putExtra(Constants.KEY_DOWNLOAD_URL, url)
        intent.putExtra(Constants.KEY_FORCE_START, forceStart)
        context.startService(intent)
    }

    /**
     * 创建下载任务
     */
    private fun createTask(url: String) {
        val cachePath = getDownloadCachePath()
        val guessName = URLUtil.guessFileName(url, null, null)
        val fileName = if (guessName.isNullOrBlank()) {
            val fileNameStart = url.lastIndexOf("/")
            url.substring(fileNameStart)
        } else {
            guessName
        }
        val filePath = "$cachePath/${fileName}"
        Log.i("Tag", "download file path: $filePath")
        Aria.download(this)
            .load(url)
            .setFilePath(filePath)
            .ignoreCheckPermissions()
            .create()
    }

    /**
     * 恢复文件下载
     */
    @JvmStatic
    fun resume(url: String) {
        val task = getTask(url)
        task?.let { loadTask(task.id).resume() }
    }

    /**
     * 暂停文件下载
     *
     * @param url String
     */
    @JvmStatic
    fun pause(url: String) {
        val task = getTask(url)
        task?.let { loadTask(task.id).stop() }
    }

    /**
     * 取消文件下载
     *
     * @param url String
     */
    @JvmStatic
    fun cancel(url: String) {
        val task = getTask(url)
        task?.let { loadTask(it.id).cancel() }
    }

    /**
     * 加载下载任务
     *
     * @param taskId Long
     * @return HttpNormalTarget
     */
    private fun loadTask(taskId: Long): HttpNormalTarget = let { Aria.download(this).load(taskId).ignoreCheckPermissions() }

    /**
     * 通过下载链接获取下载任务
     * 无效任务返回为空
     *
     * @param url String
     * @return DownloadEntity?
     */
    private fun getTask(url: String): DownloadEntity? {
        val task = Aria.download(this).getFirstDownloadEntity(url)
        return if (task.isValid()) {
            task
        } else {
            null
        }
    }

    /**
     * 判断下载任务有效性
     *
     * @return Boolean
     */
    fun DownloadEntity?.isValid(): Boolean {
        return this != null && this.id != -1L
    }

    /**
     * 获取文件下载缓存路径
     */
    private fun getDownloadCachePath(): String {
        return PathUtils.getInternalAppCachePath()
    }
}