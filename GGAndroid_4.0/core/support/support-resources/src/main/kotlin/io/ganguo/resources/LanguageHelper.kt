package io.ganguo.resources

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.LocaleList
import com.yariksoffice.lingver.Lingver
import java.util.*

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/12
 *     desc   : 语言切换工具类
 * </pre>
 */
class LanguageHelper private constructor() {
    private lateinit var changeListener: OnLanguageChangeListener
    private lateinit var currentLocale: Locale
    private lateinit var context: Application


    /**
     * Application initialize
     * @param context Application
     * @param listener OnLanguageChangeListener
     */
    private fun initialize(context: Application, listener: OnLanguageChangeListener) {
        instance.context = context
        instance.changeListener = listener
        instance.currentLocale = listener.onLoadDefaultLanguage()
        Lingver.init(context, currentLocale)
    }


    /**
     * 应用语言设置，并重启Activity
     * @param context Context  上下文
     * @param locale Locale  语言配置
     */
    private fun changeLanguage(context: Context, locale: Locale): Boolean {
        if (locale.language == currentLocale.language) {
            return false
        }
        instance.changeListener.onCacheLanguageLocale(locale)
        instance.currentLocale = locale
        Lingver.getInstance().setLocale(context, locale)
        return true
    }


    /**
     * @property instance 单例对象
     */
    companion object {
        private lateinit var instance: LanguageHelper


        /**
         * 初始化默认语言配置，必须在Application中初始化
         * @param context Application
         * @param listener OnLanguageChangeListener
         */
        @JvmStatic
        fun init(context: Application, listener: OnLanguageChangeListener) {
            check(!::instance.isInitialized) { "Already initialized" }
            instance = LanguageHelper()
            instance.initialize(context, listener)
        }


        /**
         * 应用语言设置，并重启Activity
         * @param context Context 上下文
         * @param locale Locale 语言配置
         * @param restartActivityClazz Class<out Activity> 需要重启的Activity目标Class类型
         */
        @JvmStatic
        fun applyLanguage(
            context: Context,
            locale: Locale,
            restartActivityClazz: Class<out Activity>
        ): Boolean {
            var intent = Intent(context, restartActivityClazz)
            return applyLanguage(context, locale, intent)
        }


        /**
         * 应用语言设置，并重启Activity
         * @param context Context  上下文
         * @param locale Locale  语言配置
         * @param startIntent Intent 需要重启的Intent，注意必须设置对应Activity，可以在重启时传递一些参数，用于重启后回复
         */
        @JvmStatic
        fun applyLanguage(context: Context, locale: Locale, startIntent: Intent): Boolean {
            checkInit()
            var success = instance.changeLanguage(context, locale)
            if (success) {
                restartActivity(context, startIntent)
            }
            return success
        }


        /**
         * 重启Activity
         */
        @JvmStatic
        private fun restartActivity(context: Context, intent: Intent) {
            check(intent.component != null) {
                "Please set the target Activity that needs to be restarted!!!"
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }


        /**
         * 重启app首页
         * @param context Context
         * @param clazz
         */
        @JvmStatic
        private fun restartHomeActivity(context: Context, clazz: Class<out Activity>) {
            val intent = Intent(context, clazz)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }

        /**
         * 获取系统语言配置
         * @return Locale
         */
        @JvmStatic
        fun getSystemLocale(): Locale {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return LocaleList.getDefault().get(0)
            }
            return Locale.getDefault()
        }


        /**
         * 获取当前设置的语言环境
         * @return Locale
         */
        @JvmStatic
        fun getCurrentLocale(): Locale {
            checkInit()
            return get().currentLocale
        }


        /**
         * 初始化检测
         */
        private fun checkInit() {
            check(::instance.isInitialized) {
                "You have to initialize LanguageHelper.init(context,listener) in Application first!!"
            }
        }


        /**
         * 单例
         * @return LanguageHelper
         */
        @JvmStatic
        private fun get(): LanguageHelper {
            return instance
        }
    }


    /**
     * 语言类型，切换接口
     */
    interface OnLanguageChangeListener {

        /**
         * 加载并返回本地默认语言类型
         * @return Locale
         */
        fun onLoadDefaultLanguage(): Locale

        /**
         * 更新缓存接口回调
         * @param locale Locale
         */
        fun onCacheLanguageLocale(locale: Locale)
    }
}