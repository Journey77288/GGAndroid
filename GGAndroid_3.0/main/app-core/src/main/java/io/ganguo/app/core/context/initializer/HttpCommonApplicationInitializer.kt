package io.ganguo.app.core.context.initializer

import android.app.Application
import io.ganguo.http.retrofit.RetrofitSetting
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.ApplicationInitializerCreator
import io.ganguo.log.core.Logger

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Http Client Config
 * </pre>
 */
abstract class HttpCommonApplicationInitializer : ApplicationInitializer, ApplicationInitializerCreator<HttpCommonApplicationInitializer>, RetrofitSetting {
    /**
     * Http Setting
     * @param application Application
     */
    override fun initialize(application: Application) {
        setting()
    }

    /**
     * print log message
     * @param message String
     */
    override fun log(message: String) {
        Logger.tag(javaClass.simpleName).e(message)
    }


    /**
     * create HttpApplicationInitializer
     * @param parameter Carry configuration parameters
     * @return  Pair<String, T>
     */
    override fun createInitializer(parameter: Map<String, Any>): HttpCommonApplicationInitializer {
        return this
    }

}