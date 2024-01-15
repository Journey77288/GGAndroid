package io.ganguo.core.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.utils.LocationHelper

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/06/04
 *     desc   : Location Initializer
 * </pre>
 */
class LocationApplicationInitializer : ApplicationInitializer {
    override fun initialize(application: Application) {
        LocationHelper.init(application)
    }

    companion object : ApplicationInitializerCreator<LocationApplicationInitializer> {
        override fun createInitializer(parameter: Map<String, Any>): LocationApplicationInitializer {
            return LocationApplicationInitializer()
        }
    }
}