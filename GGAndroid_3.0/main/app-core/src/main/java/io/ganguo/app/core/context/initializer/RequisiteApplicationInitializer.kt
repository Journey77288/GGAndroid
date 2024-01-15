package io.ganguo.app.core.context.initializer

import android.app.Application
import io.ganguo.ggcache.sp.SharedPreferences
import io.ganguo.image.core.ImageHelper
import io.ganguo.image.glide.engine.GlideImageEngine
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.ApplicationInitializerCreator
import io.ganguo.log.core.Logger
import io.ganguo.utils.util.json.Gsons
import io.reactivex.plugins.RxJavaPlugins

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : 一些必备的初始化操作，例如用户信息、图片工具SDK等
 * </pre>
 */
class RequisiteApplicationInitializer : ApplicationInitializer, SharedPreferences.Converter {

    /**
     * Requisite Helper init
     * @param application Application
     */
    override fun initialize(application: Application) {
        initImageHelper()
        SharedPreferences.initialize(application, this)
        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
            Logger.e("RxJavaPlugins:ErrorHandler:" + it.message)
        }
    }

    /**
     * ImageHelper init
     */
    private fun initImageHelper() {
        ImageHelper
                .Builder()
                .setEngine(GlideImageEngine())
                .apply {
                    ImageHelper.init(this)
                }

    }


    companion object : ApplicationInitializerCreator<ApplicationInitializer> {

        /**
         * create RequisiteApplicationInitializer
         * @param parameter Map<String, Any>
         * @return ApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>): ApplicationInitializer {
            return io.ganguo.app.core.context.initializer.RequisiteApplicationInitializer()
        }
    }

    /**
     * object to json String
     * @param any Any
     * @return String
     */
    override fun toJsonString(any: Any): String {
        return Gsons.toJson(any)
    }

    /**
     * json String to object
     * @param jsonString String
     * @param clazz Class<T>
     * @return T?
     */
    override fun <T> toObject(jsonString: String, clazz: Class<T>): T? {
        return Gsons.fromJson(jsonString, clazz)
    }
}
