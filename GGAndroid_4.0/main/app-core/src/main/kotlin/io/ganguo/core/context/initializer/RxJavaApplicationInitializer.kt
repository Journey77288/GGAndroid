package io.ganguo.core.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.log.core.Logger
import io.ganguo.rxjava.RxHelper
import io.ganguo.appcompat.Toasts

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/18
 *   @desc   : RxJavaApplicationInitializer
 * </pre>
 */
class RxJavaApplicationInitializer private constructor() : ApplicationInitializer {
    /**
     * Initialize in Application
     * @param application Application
     */
    override fun initialize(application: Application) {
        RxHelper.Builder().apply {
            logger = {
                Logger.e(it)
            }
            toast = {
                Toasts.show(it)
            }
            throwable = {
                Logger.d(it.toString())
            }
        }.let {
            RxHelper.initialize(it)
        }
    }


    /**
     * RxJavaApplicationInitializer Creator
     */
    companion object : ApplicationInitializerCreator<RxJavaApplicationInitializer> {
        /**
         * create RxJavaApplicationInitializer
         * @param parameter Map<String, Any>
         * @return ApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>): RxJavaApplicationInitializer {
            return RxJavaApplicationInitializer()
        }
    }
}
