package io.ganguo.core.context

import android.content.Context
import androidx.multidex.MultiDexApplication
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.DefaultApplicationInitializer
import io.ganguo.utils.helper.screen.ScreenAdaptorHelper
import io.ganguo.utils.util.Systems

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Application is the base class
 * </pre>
 * @property [initializerMap] 初始化构造器集合
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseApp : MultiDexApplication() {
    private val initializerMap: LinkedHashMap<String, ApplicationInitializer> by lazy {
        LinkedHashMap<String, ApplicationInitializer>()
    }

    init {
        applicationInitializer()
    }


    /**
     * 1、添加[ApplicationInitializer]对应接口实现类
     * PS:切 记此处不可直接初始化项目模块或者sdk，因为onCreate函数还未跑，可能会导致模块初始化失败
     */
    open fun applicationInitializer() {
        addInitializer(DefaultApplicationInitializer.create())
    }

    /**
     * add Initializer
     * @param pair Pair<String, out ApplicationInitializer>
     */
    protected open fun addInitializer(pair: Pair<String, ApplicationInitializer>) {
        initializerMap[pair.first] = pair.second
    }

    /**
     * remove Initializer
     * @param initializerKey String
     */
    protected fun removeInitializer(initializerKey: String) {
        initializerMap.remove(initializerKey)
    }


    override fun attachBaseContext(base: Context) {
        val context = initScreenAdaptors(base)
        super.attachBaseContext(context)
    }

    /**
     * 应用启动
     */
    override fun onCreate() {
        super.onCreate()
        APP = this
        onCallApplicationInitializer()
    }


    /**
     * Call all ApplicationInitializer
     */
    private fun onCallApplicationInitializer() {
        initializerMap
                .takeIf {
                    //判断是否是主进程。默认只在主进程初始化，避免sdk重复初始化，资源浪费
                    Systems.getProcessName(this) == packageName
                }
                ?.map {
                    it.value
                }
                ?.forEach {
                    it.initialize(this)
                }
    }


    /**
     * 初始化屏幕适配工具类
     *
     * @param context
     * @return
     */
    protected open fun initScreenAdaptors(context: Context?): Context {
        ScreenAdaptorHelper.get().init(context)
        return ScreenAdaptorHelper.get().onBindScreenAdapter(context)
    }

    companion object {
        private var APP: BaseApp? = null

        /**
         * 单例
         *
         * @return
         */
        @JvmStatic
        fun <T : BaseApp> me(): T {
            return APP as T
        }
    }
}