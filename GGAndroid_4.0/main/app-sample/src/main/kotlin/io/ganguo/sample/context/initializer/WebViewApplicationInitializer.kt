package io.ganguo.sample.context.initializer

import android.app.Application
import com.io.ganguo.webview.WebViewPool
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import java.lang.Integer.min

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/09/16
 *     desc   : WebView init
 * </pre>
 */
class WebViewApplicationInitializer : ApplicationInitializer {

    override fun initialize(application: Application) {
        // 初始化WebView缓存池
        WebViewPool.getInstance().setMaxPoolSize(min(Runtime.getRuntime().availableProcessors(), DEFAULT_POOL_SIZE))
        WebViewPool.getInstance().init(application)
    }

    companion object : ApplicationInitializerCreator<WebViewApplicationInitializer> {
        const val DEFAULT_POOL_SIZE = 3

        override fun createInitializer(parameter: Map<String, Any>): WebViewApplicationInitializer {
            return WebViewApplicationInitializer()
        }
    }
}