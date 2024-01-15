package io.ganguo.core.viewmodel.common.component

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.WebView
import androidx.annotation.ColorRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.io.ganguo.webview.IPageStateListener
import com.io.ganguo.webview.WebViewWrapLayout
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.core.databinding.ComponentWebviewBinding
import io.ganguo.core.viewmodel.common.component.WebViewModel.ContentType.TYPE_HTML
import io.ganguo.core.viewmodel.common.component.WebViewModel.ContentType.TYPE_URL
import io.ganguo.core.viewmodel.common.component.WebViewModel.ProgressStyle.CIRCLE
import io.ganguo.core.viewmodel.common.component.WebViewModel.ProgressStyle.HORIZONTAL
import io.ganguo.core.viewmodel.common.component.WebViewModel.ProgressStyle.NONE
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.permission.requestLocationPermissions
import io.ganguo.resources.R

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/04
 *     desc   : WebView ViewModel
 * </pre>
 */
open class WebViewModel(protected val data: String, private val type: ContentType)
	: ViewModel<ViewInterface<ComponentWebviewBinding>>(), IPageStateListener {
	override val layoutId: Int = io.ganguo.core.R.layout.component_webview
	val progressStyle = ObservableField<ProgressStyle>()
	val progress = ObservableInt(0)
	@ColorRes
	private var progressColorRes: Int = R.color.yellow
	val isLoading = ObservableBoolean(true)
	private var titleCallback: ((String?) -> Unit?)? = null
    private var webListener: IWebListener? = null
    private var isDarkMode = false

	init {
		progressStyle.set(NONE)
	}

	override fun onViewAttached(view: View) {
        lifecycleOwner.lifecycle.addObserver(getWebViewLayout())
		initProgressColor()
		getWebViewLayout().setUpWebViewListener(this)
		loadData()
	}

	/**
	 * 设置进度颜色
	 */
	private fun initProgressColor() {
		val colorStateList = ColorStateList.valueOf(getColor(progressColorRes))
		viewIF.binding.pbHorizontal.progressTintList = colorStateList
		viewIF.binding.pbCircle.indeterminateTintList = colorStateList
	}

	/**
	 * 加载数据
	 */
	private fun loadData() {
		if (type == TYPE_URL) {
			getWebViewLayout().loadUrl(data)
		} else {
			getWebViewLayout().loadHtml(data)
		}
	}

	/**
	 * 设置进度显示风格
	 *
	 * @param [style] 风格枚举值
	 * @return WebViewModel
	 */
	fun progressStyle(style: ProgressStyle): WebViewModel = apply {
		progressStyle.set(style)
	}

	/**
	 * 设置进度颜色
	 *
	 * @param [progressColorRes] 进度颜色id值
	 * @return WebViewModel
	 */
	fun progressColor(@ColorRes progressColorRes: Int): WebViewModel = apply {
		this.progressColorRes = progressColorRes
	}

	/**
	 * 设置标题获取回调
	 *
	 * @param [callback] 回调
	 * @return WebViewModel
	 */
	fun titleCallback(callback: ((String?) -> Unit)): WebViewModel = apply {
		this.titleCallback = callback
	}

    /**
     * 网页重定向回调
     *
     * @param listener IWebListener
     * @return WebViewModel
     */
    fun setWebListener(listener: IWebListener): WebViewModel = apply {
        this.webListener = listener
    }

    override fun shouldOverrideUrlLoading(url: String?): Boolean {
        return (webListener?.onUrlOverride(url)) ?: super.shouldOverrideUrlLoading(url)
    }

	private fun getWebViewLayout(): WebViewWrapLayout {
		return viewIF.binding.webview
	}

	fun getWebView(): WebView? {
		if (!isAttach()) {
			return null
		}
		return getWebViewLayout().getWebView()
	}

	/**
	 * 判断是否可以返回上一页
	 *
	 * @return Boolean
	 */
	fun canGoBack(): Boolean {
		return getWebView()?.canGoBack() == true
	}

	/**
	 * 返回上一页
	 */
	fun goBack() {
		getWebView()?.goBack()
	}

	/**
	 * 重新加载
	 */
	fun reload() {
		getWebView()?.reload()
	}

	/**
	 * 添加JsBridget接口
	 *
	 * @param [name] 接口名称
	 * @param [jsInterface] 接口
	 */
	fun addJsInterface(name: String, jsInterface: Any) {
		getWebViewLayout().addJsInterface(name, jsInterface)
	}

	/**
	 * 移除JsBridget接口
	 *
	 * @param [name] 接口名称
	 */
	fun removeJsInterface(name: String) {
		getWebViewLayout().removeJsInterface(name)
	}

	override fun onStarted(url: String?, favicon: Bitmap?) {
		isLoading.set(true)
	}

	override fun onFinished(url: String?) {
		isLoading.set(false)
	}

	override fun onError(errorCode: Int?, description: String?, failingUrl: String?) {
		isLoading.set(false)
	}

	override fun onProgressChanged(newProgress: Int) {
		progress.set(newProgress)
	}

	override fun onReceivedTitle(title: String?) {
		super.onReceivedTitle(title)
		titleCallback?.invoke(title)
	}

	override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
        // TODO: 加上权限作用提示PermissionHintDialog
		ActivityHelper.currentActivity()?.requestLocationPermissions {
			callback?.invoke(origin, it.success, false)
		}
	}

	/**
	 * 进度条样式枚举类
	 *
	 * @property [HORIZONTAL] 顶部横向进度条
	 * @property [CIRCLE] 页面居中加载转圈
	 * @property [NONE] 不显示
	 */
	enum class ProgressStyle {
		HORIZONTAL, CIRCLE, NONE
	}

	/**
	 * 网页加载类型枚举类
	 *
	 * @property [TYPE_URL] 网址
	 * @property [TYPE_HTML] 富文本/HTML代码块
	 */
	enum class ContentType {
		TYPE_URL,
		TYPE_HTML
	}

    interface IWebListener {
        fun onUrlOverride(url: String?): Boolean
    }
}