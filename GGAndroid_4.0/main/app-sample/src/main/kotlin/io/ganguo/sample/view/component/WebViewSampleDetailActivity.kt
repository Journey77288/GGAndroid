package io.ganguo.sample.view.component

import android.content.Context
import android.content.Intent
import android.os.Build
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.core.viewmodel.common.component.WebViewModel
import io.ganguo.sample.bean.Keys
import io.ganguo.sample.viewmodel.component.ActivityWebViewSampleDetailVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/04
 *     desc   : WebView Detail Demo
 * </pre>
 */
class WebViewSampleDetailActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityWebViewSampleDetailVModel>() {
	override fun createViewModel(): ActivityWebViewSampleDetailVModel {
        val progressStyle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(Keys.Intent.Common.DATA, WebViewModel.ProgressStyle::class.java)!!
        } else {
            intent.getSerializableExtra(Keys.Intent.Common.DATA) as WebViewModel.ProgressStyle
        }
		return ActivityWebViewSampleDetailVModel(progressStyle)
	}

	override fun onViewAttached(viewModel: ActivityWebViewSampleDetailVModel) {

	}

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

	companion object {
		@JvmStatic
		fun start(context: Context, progressStyle: WebViewModel.ProgressStyle) {
			val intent = Intent(context, WebViewSampleDetailActivity::class.java)
					.putExtra(Keys.Intent.Common.DATA, progressStyle)
			context.startActivity(intent)
		}
	}
}