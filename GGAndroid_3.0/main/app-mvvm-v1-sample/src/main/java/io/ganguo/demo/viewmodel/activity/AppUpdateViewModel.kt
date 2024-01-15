package io.ganguo.demo.viewmodel.activity

import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.app.update.ApkFiles
import io.ganguo.app.update.AppVersions
import io.ganguo.app.update.Roots
import io.ganguo.app.update.listener.ApkDownloadListener
import io.ganguo.app.update.model.AppVersionModel
import io.ganguo.demo.BuildConfig
import io.ganguo.demo.R
import io.ganguo.demo.context.AppContext
import io.ganguo.demo.databinding.ActivityAppUpdateBinding
import io.ganguo.demo.view.dialog.AppUpdateDialog
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.runOnUiThread
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.base.viewmodel.BaseActivityVModel
import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import java.io.File


/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/28
 *     desc   : app update demo
 * </pre>
 */

class AppUpdateViewModel : BaseActivityVModel<ActivityAppUpdateBinding>(), ApkDownloadListener {
    override val layoutId: Int by lazy { R.layout.activity_app_update }
    var process = ObservableField<String>()
    var installType = InstallType.DEFAULT_INSTALL


    init {
        AppVersions.initialize(AppContext.get(), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

    override fun onViewAttached(view: View) {
        initHeader()
    }


    private fun initHeader() {
        val headerViewModel = HeaderViewModel.Builder()
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel("App update Demo"))
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.activity))
                .build()
        ViewModelHelper.bind(viewInterface.binding.includeHeader, this, headerViewModel)
    }

    /**
     * 检查更新
     */
    private fun actionCheckUpdate() {
        var model = createTestModel()
        var isLatestVersion = AppVersions.isLatestVersion(model)
        if (isLatestVersion) {
            ToastHelper.showMessage("当前已经是最新版本")
            return
        }
        AppUpdateDialog(context, model, {
            ToastHelper.showMessage("cancel")
        }, {
            AppVersions.startDownloadApkFile(model, this)
        }).show()
    }

    /**
     * 默认安装方式
     */
    fun actionDefaultInstall() {
        installType = InstallType.DEFAULT_INSTALL
        actionCheckUpdate()
    }

    /**
     * 静默安装方式（需要设备拥有root权限）
     */
    fun actionSilentInstall() {
        if (!Roots.isDeviceRooted()) {
            ToastHelper.showMessage("Unable to install silently without root")
            return
        }
        installType = InstallType.SILENT_INSTALL
        actionCheckUpdate()
    }

    private fun createTestModel(): AppVersionModel {
        return AppVersionModel(
                title = "有新的客户端",
                describe = "更新内容文本更新内容文本",
                versionCode = 2,
                versionName = "1.0.1",
                link = "http://qiniu-cdn.dev.ganguomob.com/debug_sample_io.ganguo.incubator_2_v1.0.1_2020_05_29_16_39_58.apk",
                md5 = "1aeaeac8fbee253b964e14e9d38640ec")
    }

    /**
     * 取消下载
     */
    fun actionCancelDownload() {
        AppVersions.cancelDownload()
    }

    /**
     * 删除 APK 文件
     */
    fun actionDeleteApkFile() {
        AppVersions.deleteApkFile()
        runOnUiThread {
            ToastHelper.showMessage("delete success")
        }
    }

    /**
     * apk下载进度回调
     * @param process Int
     */
    override fun downloadApkFileProcess(process: Int) {
        this.process.set("${process}%")
    }

    /**
     * apk下载失败
     * @param error Throwable
     */
    override fun downloadApkFileFail(error: Throwable) {
        this.process.set("download fail: ${error.message}")
        runOnUiThread {
            ToastHelper.showMessage("AppUpdateViewModel  ${error.message}")
        }
    }

    /**
     * apk下载成功
     * @param file File
     */
    override fun downloadApkFileSuccessful(file: File) {
        this.process.set("download successful")
        try {
            if (installType == InstallType.DEFAULT_INSTALL) {
                ApkFiles.installApk(context, file)
            } else {
                ApkFiles.silentInstall(file.absolutePath)
            }
            ToastHelper.showMessage("Installation success!!")
        } catch (e: Exception) {
            e.printStackTrace()
            ToastHelper.showMessage("Installation failed!!")
        }
    }

    /**
     * apk下载任务取消
     */
    override fun downloadApkFileCancel() {
        this.process.set("download canceled")
    }

    override fun onDestroy() {
        AppVersions.clearListener(this)
        super.onDestroy()
    }

    enum class InstallType {
        DEFAULT_INSTALL,
        SILENT_INSTALL
    }


}