package com.io.ganguo.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.io.ganguo.BuildConfig

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/02
 *     desc   : WebView封装
 * </pre>
 */
class WebViewWrapLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), DefaultLifecycleObserver {
    companion object {
        const val HTML_MIME_TYPE = "text/html"
        const val UTF_8 = "UTF-8"
    }

    private var webView: BaseWebView? = null

    init {
        initWebView()
    }

    /**
     * 初始化WebView
     */
    private fun initWebView() {
        if (webView == null) {
            webView = WebViewPool.getInstance().getWebView(context)
        }
        webView!!.initSelf()
        addView(webView)
    }

    /**
     * 添加JsBridget接口
     *
     * @param [name] 接口名称
     * @param [jsInterface] jsBridget接口
     */
    @SuppressLint("JavascriptInterface")
    fun addJsInterface(name: String, jsInterface: Any) {
        webView?.addJavascriptInterface(jsInterface, name)
    }

    /**
     * 移除JsBridget接口
     *
     * @param [name] 接口名称
     */
    fun removeJsInterface(name: String) {
        webView?.removeJavascriptInterface(name)
    }

    fun getWebView(): WebView? {
        return webView
    }

    /**
     * 设置WebView监听
     *
     * @param [pageStateListener] 监听
     */
    fun setUpWebViewListener(pageStateListener: IPageStateListener) {
        webView ?: return
        webView?.apply {
            webChromeClient = WebChromeClientStateListener(pageStateListener)
            webViewClient = WebViewClientStateListener(pageStateListener)
        }
    }

    /**
     * 加载链接
     *
     * @param [url] 链接
     */
    fun loadUrl(url: String) {
        webView?.loadUrl(url)
    }

    /**
     * 加载Html数据
     *
     * @param [data] html数据
     */
    fun loadHtml(data: String) {
        webView?.loadData(data, HTML_MIME_TYPE, UTF_8)
    }

    override fun onResume(owner: LifecycleOwner) {
        webView?.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        webView?.onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        webView?.let { WebViewPool.getInstance().recycle(it) }
    }
}