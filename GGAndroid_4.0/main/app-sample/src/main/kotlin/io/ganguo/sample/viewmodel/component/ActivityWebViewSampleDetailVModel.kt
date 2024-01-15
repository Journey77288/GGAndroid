package io.ganguo.sample.viewmodel.component

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.ComponentWebviewBinding
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.WebViewModel
import io.ganguo.core.viewmodel.common.frame.HeaderContentFooterVModel
import io.ganguo.core.viewmodel.common.widget.ImageViewModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/04
 *     desc   : WebView Detail Demo
 * </pre>
 */
class ActivityWebViewSampleDetailVModel(progressStyle: WebViewModel.ProgressStyle)
	: HeaderContentFooterVModel<ActivityInterface<FrameHeaderContentFooterBinding>, WebViewModel, ComponentWebviewBinding>() {
	override val contentLayoutId: Int = io.ganguo.core.R.layout.component_webview
	override val contentViewModel: WebViewModel = WebViewModel("https://www.baidu.com/", WebViewModel.ContentType.TYPE_URL)
			.progressStyle(progressStyle)

	/**
	 * 设置标题栏
	 * @param header Function0<ViewGroup>
	 */
	override fun initHeader(header: () -> ViewGroup) {
		HeaderTitleVModel(false)
				.apply {
					appendLeftItem(createBackButtonVModel())
					appendLeftItem(createCloseButtonVModel())
					backgroundColor(getColor(io.ganguo.resources.R.color.colorPrimary))
				}
				.let {
					ViewModelHelper.bind(header.invoke(), this, it)
				}
	}

	/**
	 * 创建返回按钮ViewModel
	 */
	private fun createBackButtonVModel(): ImageViewModel = let {
		ImageViewModel.sampleTitleButtonVModel(io.ganguo.core.R.drawable.ic_back) {
			goBack()
		}
	}

    /**
     * 处理WebView内容返回上一页
     */
    private fun goBack() {
        if (contentViewModel.canGoBack()) {
            contentViewModel.goBack()
            return
        }
        viewIF.activity.finish()
    }

	/**
	 * 创建关闭按钮ViewModel
	 */
	private fun createCloseButtonVModel(): ImageViewModel = let {
		ImageViewModel.sampleTitleButtonVModel(io.ganguo.core.R.drawable.ic_close) {
			this.viewIF.activity.finish()
		}
	}

	override
	fun onViewAttached(view: View) {

	}

    fun onBackPressed() {
        goBack()
    }
}