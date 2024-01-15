package com.io.ganguo.webview

import android.graphics.Bitmap
import android.net.Uri
import android.webkit.*

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/02
 *     desc   : 页面状态接口
 * </pre>
 */
interface IPageStateListener {

    /**
     * 页面开始加载回调
     *
     * @param [url] 页面链接
     * @param [favicon] 页面favicon
     */
    fun onStarted(url: String?, favicon: Bitmap?)

    /**
     * 页面加载完成回调
     *
     * @param [url] 页面链接
     */
    fun onFinished(url: String?)

    /**
     * 页面加载错误回调
     *
     * @param [errorCode] 错误码
     * @param [description] 错误描述
     * @param [failingUrl] 加载错误的链接地址
     */
    fun onError(errorCode: Int?, description: String?, failingUrl: String?)

    /**
     * 页面重定向跳转监听
     *
     * @param [url] 重定向链接
     * @return Boolean
     */
    fun shouldOverrideUrlLoading(url: String?): Boolean {
    	return false
    }

    /**
     * 页面加载进度回调
     *
     * @param [newProgress] 新进度
     */
    fun onProgressChanged(newProgress: Int)

    /**
     * 获取页面标题监听
     *
     * @param [title] 页面标题
     */
    fun onReceivedTitle(title: String?) {

    }

    /**
     * 页面Js确定弹窗回调
     * 原生替换页面的Js确定弹窗需要重写该方法
     *
     * @param [url] 页面链接
     * @param [message] 确定弹窗信息
     * @param [result] 确定弹窗回调结果
     * @return Boolean
     */
    fun onJsConfirm(url: String?, message: String?, result: JsResult?): Boolean {
    	return false
    }

    /**
     * 页面Js警告弹窗回调
     * 原生替换页面的Js警告弹窗需要重写该方法
     *
     * @param [url] 页面链接
     * @param [message] 警告弹窗信息
     * @param [result] 警公告弹窗回调结果
     * @return Boolean
     */
    fun onJsAlert(url: String?, message: String?, result: JsResult?): Boolean {
    	return false
    }

    /**
     * 页面Js输入弹窗回调
     * 原生替换页面的Js输入弹窗需要重写该方法
     *
     * @param [url] 页面链接
     * @param [message] 输入弹窗消息
     * @param [defaultValue] 默认输入值
     * @param [result] 输入弹窗回调结果
     * @return Boolean
     */
    fun onJsPrompt(url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
    	return false
    }

    /**
     * 上传文件回调
     *
     * @param [filePathCallback] 上传文件回调结果
     * @param [fileChooserParams] 上传文件选择器参数(判断文件类型等信息)
     * @return Boolean
     */
    fun onShowFileChooser(filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: WebChromeClient.FileChooserParams?): Boolean {
    	return false
    }

	/**
	 * 请求Geolocation权限回调
	 *
	 * @param [origin] 请求Geolocation权限源
	 * @param [callback] 请求Geolocation权限回调
	 */
	fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?)
}