package io.ganguo.demo.viewmodel.dialog

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import io.ganguo.demo.context.AppContext
import io.ganguo.demo.R
import io.ganguo.demo.view.activity.MainActivity
import io.ganguo.core.helper.activity.ActivityHelper
import io.ganguo.utils.helper.LanguageHelper
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.viewmodel.pack.common.TextViewModel
import io.ganguo.viewmodel.pack.common.dialog.DialogBottomVModel
import io.ganguo.viewmodel.databinding.DialogGgBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import java.util.*

/**
 *
 * 语言选择
 * Created by leo on 2018/9/30.
 */
open class LanguageDialogVModel : DialogBottomVModel<DialogGgBinding>() {


    /**
     * Chinese ViewModel
     * @return
     */
    private val chineseVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .content(getString(R.string.str_language_chinese))
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .textColorRes(R.color.black)
                .onClickListener {
                    changeLanguage(Locale.CHINESE)
                }
                .build()

    /**
     * English ViewModel
     * @return
     */
    private val englishVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .content(getString(R.string.str_language_english))
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .onClickListener {
                    changeLanguage(Locale.ENGLISH)
                }
                .textColorRes(R.color.black)
                .build()


    /**
     * 改变app语言
     * @param locale Locale
     */
    private fun changeLanguage(locale: Locale) {
        val setSuccess = LanguageHelper.applyLanguage(AppContext.get(), locale, MainActivity::class.java)
        if (setSuccess) {
            ActivityHelper.finishOtherActivity(MainActivity::class.java)
            viewInterface.dialog.dismiss()
        } else {
            ToastHelper.showMessage(R.string.str_language_is_already)
        }
    }


    /**
     * function: Footer ViewModel
     *
     * @return
     */
    private val cancelVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .content(getString(R.string.str_cancel))
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .backgroundRes(R.color.blue_light_translucent)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .onClickListener { viewInterface.dialog.dismiss() }
                .textColorRes(R.color.black)
                .build()

    override fun initContent(container: ViewGroup) {
        super.initContent(container)
        ViewModelHelper.bind(container, this, chineseVModel)
        ViewModelHelper.bind(container, this, englishVModel)
        ViewModelHelper.bind(container, this, cancelVModel)
    }

    override fun onViewAttached(view: View) {
    }

}