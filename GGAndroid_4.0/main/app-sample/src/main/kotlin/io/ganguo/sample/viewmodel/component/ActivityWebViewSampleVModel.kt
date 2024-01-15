package io.ganguo.sample.viewmodel.component

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.WebViewModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.view.component.WebViewSampleDetailActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/04
 *     desc   : WebView Sample
 * </pre>
 */
class ActivityWebViewSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
	/**
	 * 设置标题栏
	 * @param header Function0<ViewGroup>
	 */
	override fun initHeader(header: () -> ViewGroup) {
		HeaderTitleVModel
				.sampleTitleVModel(viewIF.activity, "WebView Sample")
				.let {
					ViewModelHelper.bind(header.invoke(), this, it)
				}
	}

	override fun onViewAttached(view: View) {
        adapter.addAll(
            listOf(
                createMenuItemVModel("WebView With Horizontal Progress", WebViewModel.ProgressStyle.HORIZONTAL),
                createMenuItemVModel("WebView With Circle Progress", WebViewModel.ProgressStyle.CIRCLE),
                createMenuItemVModel("WebView With No Progress", WebViewModel.ProgressStyle.NONE)
            )
        )
	}

	/**
	 * 创建菜单项ViewModel
	 *
	 * @param name 选项名称
	 * @param progressStyle 进度条风格
	 * @return ItemMenuVModel
	 */
	private fun createMenuItemVModel(name: String, progressStyle: WebViewModel.ProgressStyle): ItemMenuVModel {
		return ItemMenuVModel.create(name) {
			WebViewSampleDetailActivity.start(context, progressStyle)
		}
	}
}