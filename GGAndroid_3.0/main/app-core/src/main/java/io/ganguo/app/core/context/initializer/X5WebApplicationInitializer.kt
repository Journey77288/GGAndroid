package io.ganguo.app.core.context.initializer

import android.app.Application
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.ApplicationInitializerCreator

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : 腾讯X5 WebView内核初始化
 * </pre>
 */
class X5WebApplicationInitializer private constructor() : ApplicationInitializer {


    override fun initialize(application: Application) {
        application.run {
            val map = HashMap<String, Any>()
            map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
            map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
            QbSdk.initTbsSettings(map)
            QbSdk.initX5Environment(this, null)
        }
    }


    companion object : ApplicationInitializerCreator<io.ganguo.app.core.context.initializer.X5WebApplicationInitializer> {
        /**
         * create ApplicationInitializerCreator
         * @return X5WebApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>) = io.ganguo.app.core.context.initializer.X5WebApplicationInitializer()

    }

}