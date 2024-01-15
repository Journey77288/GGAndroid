package io.ganguo.app.update

import android.app.Application
import android.os.Build
import android.os.StrictMode
import io.ganguo.app.ApkDownloadTask
import io.ganguo.app.Md5Helper
import io.ganguo.app.update.listener.ApkDownloadListener
import io.ganguo.app.update.model.AppVersionModel
import java.io.File

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/28
 *     desc   : APP 版本更新工具类
 * </pre>
 */

class AppVersions private constructor() : ApkDownloadListener {
    private var versionName: String = ""
    private var versionCode = 0
    private var downloadListeners = mutableListOf<ApkDownloadListener>()
    private var downloadTask: ApkDownloadTask? = null
    private var downloadModel: AppVersionModel? = null
    private var fileDirPath = ""
    private lateinit var context: Application

    /**
     * 初始化
     * @param context Context
     * @param versionName String
     * @param versionCode Int
     */
    private fun init(context: Application, versionName: String, versionCode: Int) {
        fileDirPath = context.externalCacheDir!!.absolutePath + File.pathSeparator + "apk"
        this.versionName = versionName
        this.versionCode = versionCode
        this.context = context
    }


    /**
     * 重置，资源释放
     */
    private fun reset() {
        downloadListeners.clear()
        downloadTask = null
        downloadModel = null
    }


    /**
     * apk文件下载进度回调
     * @param process Int
     */
    override fun downloadApkFileProcess(process: Int) {
        downloadListeners.forEach {
            it.downloadApkFileProcess(process)
        }
    }

    /**
     * apk文件下载失败回调
     * @param error Throwable
     */
    override fun downloadApkFileFail(error: Throwable) {
        downloadListeners.forEach {
            it.downloadApkFileFail(error)
        }
        release()
    }

    /**
     * apk文件下载成功回调
     * @param file File
     */
    override fun downloadApkFileSuccessful(file: File) {
        if (downloadModel != null &&
                !downloadModel!!.md5.isNullOrEmpty() &&
                !Md5Helper.checkFileCompleteness(file, downloadModel!!.md5)) {
            downloadApkFileFail(Throwable("MD5 hash does not match"))
            return
        }
        downloadListeners.forEach {
            it.downloadApkFileSuccessful(file)
        }
        release()
    }

    /**
     * apk文件下载取消
     */
    override fun downloadApkFileCancel() {
        downloadListeners.forEach {
            it.downloadApkFileCancel()
        }
        release()
    }


    companion object {
        private const val DEFAULT_APK_NAME = "app.apk"
        private lateinit var instance: AppVersions
        fun get(): AppVersions {
            check(::instance.isInitialized) {
                "You have to initialize AppVersionHelper.initialize(context,listener) in Application first!!"
            }
            return instance
        }

        /**
         * 初始化
         * @param context Context
         * @param versionName String
         * @param versionCode Int
         */
        @JvmStatic
        fun initialize(context: Application, versionName: String, versionCode: Int) {
            check(!::instance.isInitialized) { "Already initialized" }
            instance = AppVersions()
            instance.init(context, versionName, versionCode)
            onAdaptationFileUri()
        }

        /**
         * 适配FileProvider，避免读取安装文件导致失败
         */
        private fun onAdaptationFileUri() {
            val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.detectFileUriExposure()
            }
        }


        /**
         * 判断是否是最新版本
         * @return Boolean
         */
        @JvmStatic
        fun isLatestVersion(model: AppVersionModel): Boolean {
            if (compareVersionCode(model)) {
                return true
            }
            return compareVersionCode(model) && compareVersionName(model)
        }


        /**
         * 比较版本名
         * @param model AppVersionModel
         * @return Boolean
         */
        @JvmStatic
        private fun compareVersionName(model: AppVersionModel): Boolean {
            var remoteVersion = model.versionName.replace(".", "")
            var currentVersion = get().versionName.replace(".", "")
            if (remoteVersion.isEmpty() || currentVersion.isEmpty()) {
                return false
            }
            return currentVersion.toInt() >= remoteVersion.toInt()
        }


        /**
         * 比较版本号
         * @param model AppVersionModel
         * @return Boolean
         */
        @JvmStatic
        private fun compareVersionCode(model: AppVersionModel): Boolean {
            return get().versionCode >= model.versionCode
        }


        /**
         * 设置文件保存路径
         * @param path String
         */
        @JvmStatic
        fun setDownloadPath(path: String) {
            check(path.isNotEmpty()) {
                "path not empty"
            }
            get().fileDirPath = path
        }

        /**
         * 删除本地 APK 文件
         */
        @JvmStatic
        fun deleteApkFile() {
            ApkFiles.deleteFile(get().fileDirPath)
        }


        /**
         * 启动下载
         * @param model AppVersionModel
         * @param downloadListener DownloadCallback
         */
        @JvmStatic
        fun startDownloadApkFile(model: AppVersionModel, downloadListener: ApkDownloadListener) {
            var apkFile = ApkFiles.createApkFile(get().fileDirPath, model.link)
            if (checkAPKExist(model, apkFile)) {
                downloadListener.downloadApkFileSuccessful(apkFile)
                return
            }
            if (!get().downloadListeners.contains(downloadListener)) {
                get().downloadListeners.add(downloadListener)
            }
            if (get().downloadTask != null) {
                return
            }
            ApkFiles.deleteFile(apkFile)
            get().downloadTask = ApkDownloadTask(model.link, apkFile.absolutePath, get())
            get().downloadTask?.startDownload()
            get().downloadModel = model
        }


        /**
         * 检查 APK 是否存在
         * @param model AppVersionModel
         * @param file File
         * @return Boolean
         */
        @JvmStatic
        private fun checkAPKExist(model: AppVersionModel, file: File): Boolean {
            return file.exists() && Md5Helper.checkFileCompleteness(file, model.md5)
        }

        /**
         * 清除回调
         * @param callback DownloadCallback
         */
        @JvmStatic
        fun clearListener(callback: ApkDownloadListener) {
            get().downloadListeners.remove(callback)
        }

        /**
         * 取消下载
         */
        @JvmStatic
        fun cancelDownload() {
            get().downloadTask?.cancelDownload()
        }

        /**
         * 清除资源
         */
        private fun release() {
            get().reset()
        }
    }


}