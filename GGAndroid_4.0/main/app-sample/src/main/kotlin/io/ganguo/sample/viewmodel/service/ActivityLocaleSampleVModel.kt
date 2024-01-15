package io.ganguo.sample.viewmodel.service

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.resources.LanguageHelper
import io.ganguo.sample.R
import io.ganguo.sample.view.SampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel
import java.util.Locale

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/13
 *     desc   : 语言切换 Demo
 * </pre>
 */
class ActivityLocaleSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    companion object {
        const val ENGLISH_LANG = "en"
        const val CHINESE_LANG = "zh"
        const val CHINESE_COUNTRY = "CN"
    }

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_language_sample))
            .let {
                ViewModelHelper.bind(header.invoke(), this, it)
            }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            addAll(
                ItemMenuVModel.create("English") {
                    changeLanguage(Locale(ENGLISH_LANG))
                },
                ItemMenuVModel.create("中文") {
                    changeLanguage(Locale(CHINESE_LANG, CHINESE_COUNTRY))
                }
            )
        }
    }

    private fun ViewModelAdapter.changeLanguage(locale: Locale) {
        LanguageHelper.applyLanguage(context, locale, SampleActivity::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ActivityHelper.currentActivity()
                ?.overrideActivityTransition(
                    Activity.OVERRIDE_TRANSITION_CLOSE,
                    io.ganguo.resources.R.anim.slide_in_from_left,
                    io.ganguo.resources.R.anim.slide_out_to_right
                )
        } else {
            ActivityHelper.currentActivity()
                ?.overridePendingTransition(
                    io.ganguo.resources.R.anim.slide_in_from_left,
                    io.ganguo.resources.R.anim.slide_out_to_right
                )
        }
    }
}