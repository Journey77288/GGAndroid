package io.ganguo.core.context.initializer

import android.app.Application
import com.didichuxing.doraemonkit.DoKit
import com.facebook.stetho.Stetho
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator

/**
 * <pre>
 *     author : leo
 *     time   : 2020/08/06
 *     desc   : Debug调试框架初始化
 * </pre>
 */
class DebugToolsApplicationInitializer private constructor() : ApplicationInitializer {
    /**
     * Http Setting
     * @param application Application
     */
    override fun initialize(application: Application) {
        Stetho.initializeWithDefaults(application)
        DoKit.Builder(application).build()
    }

    /**
     * ServiceApplicationInitializer Creator
     */
    companion object : ApplicationInitializerCreator<DebugToolsApplicationInitializer> {
        /**
         * create RequisiteApplicationInitializer
         * @param parameter Map<String, Any>
         * @return ApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>): DebugToolsApplicationInitializer {
            return DebugToolsApplicationInitializer()
        }
    }
}
