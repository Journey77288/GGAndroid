package io.ganguo.sample.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.cache.sp.SPHelper
import io.ganguo.resources.LanguageHelper
import io.ganguo.resources.entity.LocaleEntity
import io.ganguo.sample.bean.Keys
import java.util.Locale

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/13
 *     desc   : Language Config
 * </pre>
 */
class LanguageApplicationInitializer : ApplicationInitializer, LanguageHelper.OnLanguageChangeListener {

    override fun initialize(application: Application) {
        LanguageHelper.init(application, this)
    }

    companion object : ApplicationInitializerCreator<LanguageApplicationInitializer> {
        override fun createInitializer(parameter: Map<String, Any>): LanguageApplicationInitializer {
            return LanguageApplicationInitializer()
        }
    }

    override fun onLoadDefaultLanguage(): Locale {
        val cache = SPHelper.getObject<LocaleEntity>(Keys.Cache.Common.LANGUAGE)
        return if (cache == null) {
            Locale.ENGLISH
        } else if (cache.country.isNullOrBlank()) {
            Locale(cache.language)
        } else {
            Locale(cache.language, cache.country!!)
        }
    }

    override fun onCacheLanguageLocale(locale: Locale) {
        val cache = if (locale.country.isNullOrBlank()) {
            LocaleEntity(locale.language)
        } else {
            LocaleEntity(locale.language, locale.country)
        }
        SPHelper.putObject(Keys.Cache.Common.LANGUAGE, cache)
    }
}