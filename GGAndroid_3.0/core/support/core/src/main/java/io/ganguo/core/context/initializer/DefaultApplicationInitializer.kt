package io.ganguo.core.context.initializer

import android.app.Application
import io.ganguo.log.core.LoggerConfig
import io.ganguo.log.gg.LoggerPrinter
import io.ganguo.utils.helper.ResHelper
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.helper.screen.ScreenAdaptorHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Default ApplicationInitializer
 * </pre>
 */
class DefaultApplicationInitializer private constructor() : ApplicationInitializer {
    /**
     * Initialize the base class library
     * @param application Application
     */
    override fun initialize(application: Application) {
        application.run {
            LoggerConfig.initialize(LoggerPrinter())
            ToastHelper.initialize(this)
            ResHelper.initialize(this)
            ScreenAdaptorHelper.get().registerComponentCallbacks(this)
        }
    }

    companion object : ApplicationInitializerCreator<DefaultApplicationInitializer> {
        /**
         * create DefaultApplicationInitializer
         * @param parameter Carry configuration parameters
         * @return DefaultApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>) = DefaultApplicationInitializer()
    }
}