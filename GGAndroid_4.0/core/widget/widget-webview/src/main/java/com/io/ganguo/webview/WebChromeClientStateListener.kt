package com.io.ganguo.webview

import android.net.Uri
import android.webkit.*

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/02
 *     desc   : 带页面状态监听的WebChromeClient
 * </pre>
 */
class WebChromeClientStateListener(private val stateListener: IPageStateListener) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        stateListener.onProgressChanged(newProgress)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        stateListener.onReceivedTitle(title)
    }

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        return stateListener.onJsConfirm(url, message, result)
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        return stateListener.onJsAlert(url, message, result)
    }

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
        return stateListener.onJsPrompt(url, message, defaultValue, result)
    }

    override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
        return stateListener.onShowFileChooser(filePathCallback, fileChooserParams)
    }

	override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
		super.onGeolocationPermissionsShowPrompt(origin, callback)
		stateListener.onGeolocationPermissionsShowPrompt(origin, callback)
	}
}