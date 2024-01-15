package io.ganguo.sample.viewmodel.support

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.ComponentWebviewBinding
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.WebViewModel
import io.ganguo.core.viewmodel.common.frame.HeaderContentFooterVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/03/26
 *     desc   : Scheme跳转 Demo
 * </pre>
 */
class ActivitySchemeSampleVModel : HeaderContentFooterVModel<ActivityInterface<FrameHeaderContentFooterBinding>, WebViewModel, ComponentWebviewBinding>(),
    WebViewModel.IWebListener {
    companion object {
        const val SCHEME_HTML= "file:///android_asset/scheme.html"
    }

    override val contentLayoutId: Int = io.ganguo.core.R.layout.component_webview
    override val contentViewModel: WebViewModel = WebViewModel(SCHEME_HTML, WebViewModel.ContentType.TYPE_URL)

    /**
     * 设置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_scheme_sample))
            .let {
                ViewModelHelper.bind(header.invoke(), this, it)
            }
    }

    override fun onViewAttached(view: View) {
        initWebView()
    }

    private fun initWebView() {
        contentViewModel.setWebListener(this)
    }

    override fun onUrlOverride(url: String?): Boolean {
        if (url?.startsWith("ganguo://sample/app") == true) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
            return true
        }
        return false
    }
}