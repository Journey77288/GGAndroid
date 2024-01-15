package io.ganguo.viewmodel.pack.common.base

import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ObservableBoolean
import com.tencent.smtt.sdk.WebView
import io.ganguo.core.helper.activity.ActivityHelper
import io.ganguo.utils.util.Strings
import io.ganguo.viewmodel.R
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.databinding.IncludeWebviewBinding
import io.ganguo.viewmodel.pack.base.viewmodel.BaseSupportStateViewModel
import io.ganguo.viewmodel.pack.helper.WebViewHelper
import io.ganguo.viewmodel.pack.interfaces.webview.IWebViewLoadStateListener
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

/**
 * WebViewModel- 基类
 * Created by leo on 2018/11/12.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseWebViewModel<T : ViewInterface<IncludeWebviewBinding>>(private val contentType: WebContentType,
                                                                          private val content: String) : BaseSupportStateViewModel<T>(),
        IWebViewLoadStateListener {
    override val stateLayout: ViewGroup by lazy {
        viewInterface.binding.flCenterLoading
    }
    override val layoutId: Int by lazy {
        R.layout.include_webview
    }

    /**
     * get webView
     *
     * @return
     */
    var webView: WebView? = null
    var isShowHorizontalBar = ObservableBoolean(false)
    var isShowCenterLoading = ObservableBoolean(true)

    override fun onAttach() {
        super.onAttach()
        initCenterLoadView()
        initWebView()
        loadContent()
    }

    /**
     * init WebView
     */
    protected open fun initWebView() {
        if (webView != null) {
            return
        }
        webView = WebView(ActivityHelper.currentActivity())
        WebViewHelper.bindTbsWebView(webView, this)
        WebViewHelper.bindTbsWebViewSetting(webView)
        webContainer.removeAllViews()
        webContainer.addView(webView, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    /**
     * 加载网页内容
     */
    private fun loadContent() {
        if (contentType == WebContentType.HTML) {
            loadHtmlContent()
        } else {
            loadUrlContent()
        }
    }

    /**
     * 加载Url
     */
    protected open fun loadUrlContent() {
        if (Strings.isNotEmpty(content)) {
            webView!!.loadUrl(content)
        }
    }

    /**
     * 加载Html内容
     */
    protected open fun loadHtmlContent() {
        if (Strings.isNotEmpty(content)) {
            webView!!.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
        }
    }

    /**
     * 配置页面中心Loading
     */
    private fun initCenterLoadView() {
        if (!isShowCenterLoading.get()) {
            return
        }
        showLoadingView()
    }

    /**
     * set isShowHorizontalBar
     *
     * @param isShowHorizontalBar 是否显示水平进度
     * @return
     */
    fun <S : BaseWebViewModel<T>> setShowHorizontalBar(isShowHorizontalBar: Boolean): S {
        this.isShowHorizontalBar.set(isShowHorizontalBar)
        return this as S
    }

    /**
     * set isShowCenterLoading
     *
     * @param isShowCenterLoading 是否显示中心Loading
     */
    fun <S : BaseWebViewModel<T>> setShowCenterLoading(isShowCenterLoading: Boolean): S {
        this.isShowCenterLoading.set(isShowCenterLoading)
        return this as S
    }

    /**
     * WebView ViewGroup
     *
     * @return
     */
    protected open val webContainer: ViewGroup by lazy {
        viewInterface.binding.flWeb
    }

    /**
     * 中心Loading ViewGroup
     *
     * @return
     */
    protected val loadingContainer: ViewGroup by lazy {
        viewInterface.binding.flCenterLoading
    }

    /**
     * 水平进度条 MaterialProgressBar
     *
     * @return
     */
    protected open val horizontalBar: MaterialProgressBar by lazy {
        viewInterface.binding.mpHorizontalBar
    }


    /**
     * WebView加载失败
     *
     * @param webView
     * @param s
     * @param i
     * @param s1
     */
    override fun onWebLoadError(webView: WebView?, i: Int, s: String?, s1: String?) {
        hideLoading()
    }

    /**
     * WebView加载完成
     *
     * @param webView
     * @param url
     */
    override fun onWebLoadFinished(webView: WebView?, s: String?) {
        hideLoading()
    }

    /**
     * WebView加载进度变化
     *
     * @param webView
     * @param progress
     */
    override fun onWebLoadProgress(webView: WebView?, progress: Int) {
        val isLoadFinish = progress >= 100
        updateWebLoadProgress(progress)
        if (isLoadFinish) {
            onWebLoadFinished(webView, webView?.url)
        }
    }

    /**
     * WebView开始加载
     *
     * @param webView
     * @param bitmap
     */
    override fun onWebLoadStart(webView: WebView?, s: String?, bitmap: Bitmap?) {}


    /**
     * 更新加载进度
     *
     * @param progress 进度百分比
     */
    protected open fun updateWebLoadProgress(progress: Int) {
        if (!isAttach) {
            return
        }
        if (!isShowHorizontalBar.get()) {
            return
        }
        horizontalBar.progress = progress
        horizontalBar.showProgressBackground = true
    }

    /**
     * 隐藏Loading
     *
     * @return
     */
    fun hideLoading() {
        if (isShowCenterLoading.get()) {
            showContentView()
            isShowCenterLoading.set(false)
        }
        if (isShowHorizontalBar.get()) {
            horizontalBar.progress = 0
            horizontalBar.showProgressBackground = false
        }
    }

    override fun showLoadingView() {
        if (isShowCenterLoading.get()) {
            super.showLoadingView()
        }
    }

    /**
     * 释放 WebView资源
     */
    private fun releaseWebView() {
        if (!isAttach || webView == null) {
            return
        }
        val viewGroup = webView!!.parent
        if (viewGroup != null) {
            (viewGroup as ViewGroup).removeView(webView)
        }
        webView!!.clearHistory()
        webView!!.destroy()
        webView = null
    }

    /**
     * 判断WebView页面是否关闭。存在上一个则不关闭，返回上一页
     *
     * @return
     */
    fun isBackPressed(): Boolean {
        if (!isAttach) {
            return true
        }
        if (!webView!!.canGoBack()) {
            return true
        }
        webView!!.goBack()
        return false
    }

    override fun onDestroy() {
        releaseWebView()
        super.onDestroy()
    }

    /**
     * 加载网页类型
     *
     * @return
     */
    enum class WebContentType(private val type: String) {
        HTML("html"), URL("url");

    }

}