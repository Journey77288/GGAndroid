package io.ganguo.lifecycle.observer

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * <pre>
 *     author : leo
 *     time   : 2020/02/11
 *     desc   : LifecycleObserver 基类
 * </pre>
 */
interface BaseLifecycleObserver : LifecycleObserver {
    /**
     * onCreate
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onLifecycleCreate() {
        Log.i("${javaClass.simpleName}", "onLifecycleCreate")
    }

    /**
     * onStart
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifecycleStart() {
        Log.i("${javaClass.simpleName}", "onLifecycleStart")
    }

    /**
     * onResume
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onLifecycleResume() {
        Log.i("${javaClass.simpleName}", "onLifecycleResume")
    }

    /**
     * onStop
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onLifecycleStop() {
        Log.i("${javaClass.simpleName}", "onLifecycleStop")
    }

    /**
     * onDestroy
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onLifecyclePause() {
        Log.i("${javaClass.simpleName}", "onLifecyclePause")
    }


    /**
     * onDestroy
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleDestroy() {
        Log.i("${javaClass.simpleName}", "onLifecycleDestroy")
    }
}