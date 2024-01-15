package io.ganguo.lifecycle.app

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner

/**
 * <pre>
 *     author : leo
 *     time   : 2020/02/12
 *     desc   : App生命周期回调
 * </pre>
 */
object ApplicationLifecycleHelper : ILifecycleHandler<LifecycleObserver> {
    /**
     * add app LifecycleObserver
     * @param observer LifecycleObserver
     */
    override fun addObserver(observer: LifecycleObserver) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }

    /**
     * remove app LifecycleObserver
     * @param observer LifecycleObserver
     */
    override fun removeObserver(observer: LifecycleObserver) {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(observer)
    }
}
