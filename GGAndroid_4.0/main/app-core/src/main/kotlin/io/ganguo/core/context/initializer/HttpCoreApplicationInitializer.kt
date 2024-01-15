package io.ganguo.core.context.initializer

import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.http.retrofit.RetrofitSetting

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Http Client Config
 * </pre>
 */
abstract class HttpCoreApplicationInitializer : ApplicationInitializer,
        ApplicationInitializerCreator<HttpCoreApplicationInitializer>, RetrofitSetting {


    /**
     * create HttpApplicationInitializer
     * @param parameter Carry configuration parameters
     * @return  Pair<String, T>
     */
    override fun createInitializer(parameter: Map<String, Any>): HttpCoreApplicationInitializer {
        return this
    }

}
