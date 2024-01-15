package io.ganguo.sample.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.sticky_header.StickyHeaderHandler

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Widget init
 * </pre>
 */
class WidgetApplicationInitializer : ApplicationInitializer {
    override fun initialize(application: Application) {
        StickyHeaderHandler.initPinYinLibrary(application)
    }

    companion object : ApplicationInitializerCreator<WidgetApplicationInitializer> {
        override fun createInitializer(parameter: Map<String, Any>): WidgetApplicationInitializer {
            return WidgetApplicationInitializer()
        }
    }
}