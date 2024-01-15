package io.ganguo.sample.viewmodel.support

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.update.ApkFiles
import io.ganguo.core.update.AppVersions
import io.ganguo.core.update.listener.ApkDownloadListener
import io.ganguo.core.update.model.AppVersionModel
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.utils.runOnUiThread
import java.io.File

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Version Update Sample
 * </pre>
 */
class ActivityVersionUpdateSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>(), ApkDownloadListener {
    private val progressVModel: TextViewModel by lazy {
        createProgressVModel()
    }
    private val versionData: AppVersionModel by lazy {
        AppVersionModel(
                title = getString(R.string.str_version_title),
                describe = getString(R.string.str_version_describe),
                versionName = "1.01",
                versionCode = 2,
                link = "https://oss.pgyer.com/6223cc0eb33cd8c897febd4c8d76286c.apk?auth_key=1612852255-3f0bde0e1c335e18865968bb795ddd67-0-2de06807f395cc0e6aefce921a5c1496&response-content-disposition=attachment%3B+filename%3DHeyTap_Store_1.0_3223e9a_210205.apk"
        )
    }

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_version_update_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            addAll(
                listOf(
                    progressVModel,
                    createButtonVModel(R.string.str_version_update_now, io.ganguo.resources.R.dimen.dp_125) {
                        startUpdateNewVersion()
                    },
                    createButtonVModel(R.string.str_version_update_cancel, io.ganguo.resources.R.dimen.dp_110) {
                        progressVModel.text("")
                        AppVersions.cancelDownload()
                    },
                    createButtonVModel(R.string.str_version_update_delete_apk, io.ganguo.resources.R.dimen.dp_130) {
                        progressVModel.text("")
                        AppVersions.deleteApkFile()
                    }
                )
            )
        }
    }

    /**
     * start download new apk file
     */
    private fun startUpdateNewVersion() {
        val isLatestVersion = AppVersions.isLatestVersion(versionData)
        if (isLatestVersion) {
            Toasts.show(R.string.str_version_already_latest_version)
            return
        }
        AppVersions.startDownloadApkFile(versionData, this)
    }

    override fun downloadApkFileProcess(process: Int) {
        progressVModel.text(getString(R.string.str_version_download_progress_value, process))
    }

    override fun downloadApkFileFail(error: Throwable) {
        progressVModel.text(getString(R.string.str_version_update_fail, error.message.orEmpty()))
    }

    override fun downloadApkFileSuccessful(file: File) {
        try {
            ApkFiles.installApk(context, file)
        } catch (e: Exception) {
            e.printStackTrace()
            Logger.e("apk file install error: ${e.message}")
            runOnUiThread {
                Toasts.show(getString(R.string.str_version_install_fail))
            }
        }
    }

    /**
     * create progress ViewModel
     *
     * @return TextViewModel
     */
    private fun createProgressVModel(): TextViewModel {
        return TextViewModel()
                .also {
                    it.textSizeRes(io.ganguo.resources.R.dimen.font_14)
                    it.textColorRes(io.ganguo.resources.R.color.black)
                    it.marginLeftRes(io.ganguo.resources.R.dimen.dp_15)
                    it.marginRightRes(io.ganguo.resources.R.dimen.dp_15)
                    it.gravity(Gravity.CENTER)
                    it.marginTopRes(io.ganguo.resources.R.dimen.dp_100)
                    it.text("")
                }
    }

    /**
     * create button ViewModel
     *
     * @param titleRes
     * @param leftMarginRes
     * @param callback
     * @return TextViewModel
     */
    private fun createButtonVModel(@StringRes titleRes: Int,
                                   @DimenRes leftMarginRes: Int,
                                   callback: ((View) -> Unit)): TextViewModel {
        return TextViewModel()
                .also {
                    it.paddingRes(io.ganguo.resources.R.dimen.dp_15, io.ganguo.resources.R.dimen.dp_10, io.ganguo.resources.R.dimen.dp_15, io.ganguo.resources.R.dimen.dp_10)
                    it.marginLeftRes(leftMarginRes)
                    it.marginTopRes(io.ganguo.resources.R.dimen.dp_10)
                    it.gravity(Gravity.CENTER)
                    it.text(getString(titleRes))
                    it.width(ViewGroup.LayoutParams.WRAP_CONTENT)
                    it.backgroundDrawableRes(R.drawable.ripple_button)
                    it.textSizeRes(io.ganguo.resources.R.dimen.font_14)
                    it.textColorRes(io.ganguo.resources.R.color.black)
                    it.click(callback)
                }
    }
}