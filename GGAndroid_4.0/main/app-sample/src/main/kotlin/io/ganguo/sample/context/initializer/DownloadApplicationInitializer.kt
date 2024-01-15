package io.ganguo.sample.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.download.DownloadManager

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/11/29
 *     desc   : Download init
 * </pre>
 */
class DownloadApplicationInitializer : ApplicationInitializer {

    override fun initialize(application: Application) {
        DownloadManager.init(application)
    }

    companion object : ApplicationInitializerCreator<DownloadApplicationInitializer> {
        override fun createInitializer(parameter: Map<String, Any>): DownloadApplicationInitializer {
            return DownloadApplicationInitializer()
        }
    }
}