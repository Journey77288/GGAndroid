package io.ganguo.app.context

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import androidx.multidex.MultiDexApplication
import io.ganguo.app.context.initializer.ApplicationInitializer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Application is the base class
 * </pre>
 * @property [initializerMap] 初始化构造器集合
 */
@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BaseApplication : MultiDexApplication() {
    private val initializerMap: LinkedHashMap<String, ApplicationInitializer> by lazy {
        LinkedHashMap()
    }

    init {
        applicationInitializer()
    }


    /**
     * 添加[ApplicationInitializer]对应接口实现类
     * 注意：此处不可直接初始化项目模块或者sdk，导致模块初始化失败
     */
    open fun applicationInitializer() {
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


    /**
     * 应用启动
     */
    override fun onCreate() {
        super.onCreate()
        onCallApplicationInitializer()
    }


    /**
     * Call all ApplicationInitializer
     */
    private fun onCallApplicationInitializer() {
        initializerMap
                //.takeIf {
                ////弊端：进程判断初始化，会导致部分sdk初始化失败，导致调用异常，且很难排查，暂时注解掉，不要轻易使用
                ////判断是否是主进程。默认只在主进程初始化，避免sdk重复初始化，资源浪费
                //Apps.getProcessName() == packageName
                //}
                .map {
                    it.value
                }
                .forEach {
                    it.initialize(this)
                }
    }
}
