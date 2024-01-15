package com.io.ganguo.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.io.ganguo.BuildConfig

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/09/16
 *     desc   : WebView基类
 * </pre>
 */
class BaseWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : WebView(context, attrs) {
    companion object {
        const val BLANK_URL = "about:blank"
    }

    private val jsInterfaces = HashSet<String>()

    /**
     * 初始化WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    fun initSelf() {
        initCookieManager()
        if (BuildConfig.DEBUG) {// 只有在debug模式下才能进行调试
            setWebContentsDebuggingEnabled(true)
        }
        isScrollbarFadingEnabled = true// 滚动条自动消失
        isHorizontalScrollBarEnabled = false// 水平滚动条不显示
        isVerticalScrollBarEnabled = false// 垂直滚动条不显示
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY// 滚动条在WebView外侧显示
        settings.apply {
            textZoom = 100
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = false
            setSupportZoom(false)
            supportMultipleWindows()
            loadsImagesAutomatically = true
            domStorageEnabled = true
            databaseEnabled = true
            // WebView安全配置
            allowFileAccess = false
            allowFileAccessFromFileURLs = false
            allowUniversalAccessFromFileURLs = false
            savePassword = false
        }
        removeUselessJsInterface()
    }

    /**
     * 初始化Cookie
     */
    private fun initCookieManager() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(this, true)
    }

    /**
     * 移除没有使用的Js接口，避免安全漏洞
     */
    private fun removeUselessJsInterface() {
        removeJavascriptInterface("searchBoxJavaBridge_")
        removeJavascriptInterface("accessibility")
        removeJavascriptInterface("accessibilityTraversal")
    }

    @SuppressLint("JavascriptInterface")
    override fun addJavascriptInterface(`object`: Any, name: String) {
        super.addJavascriptInterface(`object`, name)
        jsInterfaces.add(name)
    }

    override fun removeJavascriptInterface(name: String) {
        super.removeJavascriptInterface(name)
        jsInterfaces.remove(name)
    }

    /**
     * 判断是否可返回上一页
     *
     * @return Boolean
     */
    override fun canGoBack(): Boolean {
        val list = copyBackForwardList()
        val currentIndex = list.currentIndex - 1
        if (currentIndex >= 0) {
            val item = list.getItemAtIndex(currentIndex)
            if (item != null && TextUtils.equals(item.url, BLANK_URL)) {//空页面则退出
                return false
            }
        }
        return super.canGoBack()
    }

    /**
     * 释放资源
     */
    fun release() {
        if (jsInterfaces.isNotEmpty()) {
            jsInterfaces.forEach { removeJavascriptInterface(it) }
        }
        setOnLongClickListener(null)
        setOnTouchListener(null)
        (parent as ViewGroup?)?.removeView(this)
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK) && isVersionSupportForceDark()) {
            WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_AUTO)
        }
        settings.javaScriptEnabled = false
        removeAllViews()
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        stopLoading()
        webChromeClient = null
        webViewClient = WebViewClient()
        loadUrl(BLANK_URL)
        clearHistory()
    }

    override fun onResume() {
        super.onResume()
        initDarkMode()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        initDarkMode()
    }

    /**
     * 初始化暗色模式
     */
    private fun initDarkMode() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK) && isVersionSupportForceDark()) {
            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
            } else {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_OFF)
            }
        }
    }

    /**
     * 判断系统版本是否支持WebView的ForceDark属性自定义
     *
     * @return Boolean
     */
    private fun isVersionSupportForceDark(): Boolean {
        return Build.VERSION_CODES.Q <= Build.VERSION.SDK_INT && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
    }
}