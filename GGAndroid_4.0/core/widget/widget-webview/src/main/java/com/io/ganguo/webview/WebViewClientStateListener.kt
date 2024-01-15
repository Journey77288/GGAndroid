package com.io.ganguo.webview

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebViewClientCompat

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/02
 *     desc   : 带状态监听的WebViewClient
 * </pre>
 */
class WebViewClientStateListener(private val stateListener: IPageStateListener): WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        stateListener.onStarted(url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        stateListener.onFinished(url)
    }

    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        stateListener.onError(errorCode, description, failingUrl)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return stateListener.shouldOverrideUrlLoading(url)
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return stateListener.shouldOverrideUrlLoading(request.url.toString())
    }
}