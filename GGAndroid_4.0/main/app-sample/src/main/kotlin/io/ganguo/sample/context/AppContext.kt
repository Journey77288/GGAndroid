package io.ganguo.sample.context

import io.ganguo.core.context.AppCoreContext
import io.ganguo.sample.context.initializer.SmartRefreshApplicationInitializer
import io.ganguo.sample.AppEnvironment
import io.ganguo.sample.context.initializer.*


/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Main AppContext
 * </pre>
 */
class AppContext : AppCoreContext() {


    init {
        APP = this
    }

    override fun applicationInitializer() {
        super.applicationInitializer()
        addInitializer(SmartRefreshApplicationInitializer.create())
        addInitializer(CacheApplicationInitializer.create(AppEnvironment.isDebug))
        addInitializer(LanguageApplicationInitializer.create())
        addInitializer(HttpApplicationInitializer.create())
        addInitializer(OpenSdkApplicationInitializer.create())
        addInitializer(VersionUpdateApplicationInitializer.create())
        addInitializer(WidgetApplicationInitializer.create())
        addInitializer(DownloadApplicationInitializer.create())
        addInitializer(ImagePickerApplicationInitializer.create())
        addInitializer(WebViewApplicationInitializer.create())
    }


    companion object {
        private lateinit var APP: AppContext

        /**
         * The global app context
         * @return T
         */
        @JvmStatic
        fun me(): AppContext {
            return APP
        }
    }

}
