package io.ganguo.core.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.appcompat.Toasts
import io.ganguo.resources.ResourcesHelper
import io.ganguo.screen.adapter.ScreenAdapter
import io.ganguo.screen.adapter.ScreenConfig
import io.ganguo.screen.pattern.AdapterPattern
import io.image.ImageLoader
import io.image.ImageLoader.Companion.init
import io.image.coil.CoilImageEngine

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : 视图层模块初始化
 * </pre>
 */
class ViewLayerApplicationInitializer : ApplicationInitializer {

    /**
     * Initialize in Application
     * @param application Application
     */
    override fun initialize(application: Application) {
        ScreenAdapter.initialize(application, ScreenConfig.Builder(AdapterPattern.DESIGN_WIDTH, 360f))
        ResourcesHelper.initialize(application)
        Toasts.initialize(application)
        ImageLoader
                .Builder(CoilImageEngine.Builder(application).build())
//                .Builder(GlideImageEngine())
                .apply {
                    init(this)
                }
    }


    /**
     * ServiceApplicationInitializer Creator
     */
    companion object : ApplicationInitializerCreator<ViewLayerApplicationInitializer> {
        /**
         * create ViewLayerApplicationInitializer
         * @param parameter Map<String, Any>
         * @return ApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>): ViewLayerApplicationInitializer {
            return ViewLayerApplicationInitializer()
        }
    }
}
