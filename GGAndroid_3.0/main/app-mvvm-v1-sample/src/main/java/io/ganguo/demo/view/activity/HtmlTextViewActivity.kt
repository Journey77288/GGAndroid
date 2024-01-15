package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.databinding.ActivityHtmlTextBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.HtmlTextVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/18
 *     desc   : TextView 加载Html图文（只适用通用文字、图片格式，复杂及还原度要求高的还是建议用WebView）
 * </pre>
 */
class HtmlTextViewActivity : GGVModelActivity<ActivityHtmlTextBinding, HtmlTextVModel>() {
    override fun createViewModel(): HtmlTextVModel {
        return HtmlTextVModel()
    }


    override fun onViewAttached(viewModel: HtmlTextVModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, HtmlTextViewActivity::class.java)
        }
    }
}