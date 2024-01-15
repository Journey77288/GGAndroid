package io.ganguo.demo.context.initializer

import android.app.Application
import io.ganguo.ggcache.sp.SharedPreferences
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.ApplicationInitializerCreator
import io.ganguo.utils.helper.LanguageHelper
import java.util.*

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   :
 * </pre>
 */
class LanguageApplicationInitializer private constructor() : ApplicationInitializer, LanguageHelper.OnLanguageChangeListener {

    /**
     * LanguageHelper initialize
     * @param application Application
     */
    override fun initialize(application: Application) {
        LanguageHelper.initialize(application, this)
    }

    /**
     * load default language locale
     * @return Locale
     */
    override fun onLoadDefaultLanguage(): Locale {
        var locale: Locale? = SharedPreferences.getObject(APP_LANGUAGE, Locale::class.java)
        if (locale == null) {
            locale = LanguageHelper.getSystemLanguage()
        }
        return locale
    }

    /**
     * cache language locale
     * @param locale Locale
     */
    override fun onCacheLanguageLocale(locale: Locale) {
        SharedPreferences.putObject(APP_LANGUAGE, locale)
    }

    companion object : ApplicationInitializerCreator<LanguageApplicationInitializer> {
        private const val APP_LANGUAGE = "app_language"

        /**
         * create  ApplicationInitializerCreator
         * @param parameter Carry configuration parameters
         * @return LanguageApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>): LanguageApplicationInitializer {
            return LanguageApplicationInitializer()
        }

    }
}