package io.ganguo.lifecycle.observer

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

/**
 * <pre>
 *     author : leo
 *     time   : 2020/02/06
 *     desc   : app状态监听
 * </pre>
 */
interface AppLifecycleObserver : BaseLifecycleObserver {

    /**
     * App进入前台
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onLifecycleResume() {
        Log.i("${javaClass.simpleName}", ": app enter onLifecycleResume!")
        onEnterForeground()
    }

    /**
     * App进入后台
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLifecycleStop() {
        Log.i("${javaClass.simpleName}", ": app enter onLifecycleStop!")
        onEnterBackground()
    }

    /**
     * App销毁
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onLifecycleDestroy() {
        Log.i("${javaClass.simpleName}", ": app enter onLifecycleDestroy!")
        onAppExit()
    }


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