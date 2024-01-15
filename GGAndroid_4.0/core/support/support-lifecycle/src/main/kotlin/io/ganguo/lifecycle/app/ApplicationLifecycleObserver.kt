package io.ganguo.lifecycle.app

import androidx.lifecycle.DefaultLifecycleObserver

/**
 * <pre>
 *     author : leo
 *     time   : 2020/02/06
 *     desc   : app状态监听
 * </pre>
 */
interface ApplicationLifecycleObserver : DefaultLifecycleObserver {

    /**
     * app进入后台
     */
    fun onEnterBackground()

    /**
     * app进入前台
     */
    fun onEnterForeground()


    /**
     * app退出
     */
    fun onAppExit()
}
