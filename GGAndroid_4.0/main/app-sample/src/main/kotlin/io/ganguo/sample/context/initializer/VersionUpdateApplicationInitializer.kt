package io.ganguo.sample.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.core.update.AppVersions
import io.ganguo.sample.BuildConfig

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Version Update init
 * </pre>
 */
class VersionUpdateApplicationInitializer : ApplicationInitializer {
    override fun initialize(application: Application) {
        AppVersions.initialize(application, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

    companion object : ApplicationInitializerCreator<VersionUpdateApplicationInitializer> {
        override fun createInitializer(parameter: Map<String, Any>): VersionUpdateApplicationInitializer {
            return VersionUpdateApplicationInitializer()
        }
    }
}