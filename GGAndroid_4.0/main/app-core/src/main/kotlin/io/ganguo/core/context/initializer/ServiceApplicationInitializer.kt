package io.ganguo.core.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.log.core.LoggerCore
import io.ganguo.log.gg.LoggerPrinter

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : 常用服务初始化
 * </pre>
 */
class ServiceApplicationInitializer private constructor() : ApplicationInitializer {

    /**
     * Initialize in Application
     * @param application Application
     */
    override fun initialize(application: Application) {
        LoggerCore.initialize(LoggerPrinter())
    }


    /**
     * ServiceApplicationInitializer Creator
     */
    companion object : ApplicationInitializerCreator<ServiceApplicationInitializer> {
        /**
         * create RequisiteApplicationInitializer
         * @param parameter Map<String, Any>
         * @return ApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>): ServiceApplicationInitializer {
            return ServiceApplicationInitializer()
        }
    }


}
