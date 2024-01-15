package io.ganguo.app.core.lifecycle

import io.ganguo.log.core.Logger
import io.ganguo.lifecycle.observer.AppLifecycleObserver as BaseAppLifecycleObserver

/**
 * <pre>
 *     author : leo
 *     time   : 2020/02/12
 *     desc   : app生命周期监听
 * </pre>
 */
class AppLifecycleObserver : BaseAppLifecycleObserver {

    /**
     * App进入前台
     */
    override fun onEnterBackground() {
        Logger.d("AppLifecycleObserver:app进入后台")
    }

    /**
     * App进入前台
     */
    override fun onEnterForeground() {
        Logger.d("AppLifecycleObserver:App进入前台")
    }

    /**
     * App退出
     */
    override fun onAppExit() {
        Logger.d("AppLifecycleObserver:App已退出")
    }
}