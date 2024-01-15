package io.ganguo.sample.view.component

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.component.ActivityWebViewSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/04
 *     desc   : WebView Sample
 * </pre>
 */
class WebViewSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityWebViewSampleVModel>() {
	override fun createViewModel(): ActivityWebViewSampleVModel {
		return ActivityWebViewSampleVModel()
	}

	override fun onViewAttached(viewModel: ActivityWebViewSampleVModel) {

	}

	companion object {
		@JvmStatic
		fun start(context: Context) {
			val intent = Intent(context, WebViewSampleActivity::class.java)
			context.startActivity(intent)
		}
	}
}