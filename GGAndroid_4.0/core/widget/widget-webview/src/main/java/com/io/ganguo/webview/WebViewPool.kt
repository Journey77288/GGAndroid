package com.io.ganguo.webview

import android.app.Application
import android.content.Context
import android.content.MutableContextWrapper
import android.util.Log
import java.util.*

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/09/15
 *     desc   : WebView缓存池
 * </pre>
 */
class WebViewPool private constructor() {

    companion object {
        private const val TAG = "WebViewPool"

        @Volatile
        private var instance: WebViewPool? = null

        @JvmStatic
        fun getInstance(): WebViewPool {
            return instance ?: synchronized(this) {
                instance ?: WebViewPool().also { instance = it }
            }
        }
    }

    private lateinit var application: Application
    private val pool = Stack<BaseWebView>()
    private val lock = byteArrayOf()
    private var maxSize = 1

    private fun isInitialize(): Boolean = let { ::application.isInitialized }

    /**
     * 设置 webview 池容量
     */
    fun setMaxPoolSize(size: Int) {
        synchronized(lock) { maxSize = size }
    }

    /**
     * 初始化webview 放在list中
     */
    fun init(context: Application, initSize: Int = maxSize) {
        this.application = context
        for (i in 0 until initSize) {
            val view = BaseWebView(MutableContextWrapper(application))
            pool.push(view)
        }
    }

    /**
     * 获取webview
     */
    fun getWebView(context: Context): BaseWebView {
        check(isInitialize()) { "You have to initialize WebViewPool.init(context) in Application first!!" }
        synchronized(lock) {
            val webView: BaseWebView
            if (pool.size > 0) {
                webView = pool.pop()
                Log.d(TAG, "webview from pool")
            } else {
                webView = BaseWebView(MutableContextWrapper(context))
                Log.d(TAG, "webview from create")
            }
            val contextWrapper = webView.context as MutableContextWrapper
            contextWrapper.baseContext = context
            return webView
        }
    }

    /**
     * 回收WebView
     */
    fun recycle(webView: BaseWebView) {
        webView.release()

        // 判断是否销毁
        val contextWrapper = webView.context as MutableContextWrapper
        contextWrapper.baseContext = application
        synchronized(lock) {
            if (pool.size < maxSize) {
                pool.push(webView)
            } else {
                try {
                    webView.destroy()
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
        }
    }
}