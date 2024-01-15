package io.ganguo.core.lifecycle

import androidx.lifecycle.LifecycleOwner
import io.ganguo.log.core.Logger
import io.ganguo.lifecycle.app.ApplicationLifecycleObserver as BaseAppLifecycleObserver

/**
 * <pre>
 *     author : leo
 *     time   : 2020/02/12
 *     desc   : app生命周期监听
 * </pre>
 */
open class AppLifecycleObserver : BaseAppLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        onEnterForeground()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        onEnterBackground()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }

    /**
     * App进入前台
     */
    override fun onEnterBackground() {
        Logger.i("ApplicationLifecycleObserver:app进入后台")
    }

    /**
     * App进入前台
     */
    override fun onEnterForeground() {
        Logger.i("ApplicationLifecycleObserver:App进入前台")
    }

    /**
     * App退出
     */
    override fun onAppExit() {
        Logger.i("ApplicationLifecycleObserver:App已退出")
    }
}
