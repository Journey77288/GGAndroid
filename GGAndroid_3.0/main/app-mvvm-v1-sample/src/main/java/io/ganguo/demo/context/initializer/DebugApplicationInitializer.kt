package io.ganguo.demo.context.initializer

import android.app.Application
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.ApplicationInitializerCreator

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : debug sdk initializer
 * </pre>
 */
class DebugApplicationInitializer private constructor() : ApplicationInitializer {
    /**
     * debug sdk init
     * @param application Application
     */
    override fun initialize(application: Application) {

    }


    companion object : ApplicationInitializerCreator<DebugApplicationInitializer> {

        /**
         * create DebugApplicationInitializer
         * @return  Pair<String, T>
         */
        override fun createInitializer(parameter: Map<String, Any>): DebugApplicationInitializer {
            return DebugApplicationInitializer()
        }

    }
}