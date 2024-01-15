package io.ganguo.lifecycle.helper

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import io.ganguo.lifecycle.LifecycleListener
import io.ganguo.lifecycle.observer.BaseLifecycleObserver

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   :  ViewModel LifecycleHelper
 * </pre>
 */
class LifecycleHelper(private val lifecycleListener: LifecycleListener) : LifecycleOwner, BaseLifecycleObserver {
    private val lifecycleRegistry: LifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    init {
        onLifecycleCreate()
    }


    fun bindLifecycle(listener: BaseLifecycleObserver) {
        lifecycleRegistry.addObserver(listener)
    }

    override fun onLifecycleCreate() {
        super.onLifecycleCreate()
        lifecycleListener.onCreate()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

    }

    override fun onLifecycleStart() {
        super.onLifecycleStart()
        lifecycleListener.onStart()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onLifecycleResume() {
        super.onLifecycleResume()
        lifecycleListener.onResume()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onLifecycleStop() {
        super.onLifecycleStop()
        lifecycleListener.onStop()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onLifecyclePause() {
        super.onLifecyclePause()
        lifecycleListener.onPause()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun onLifecycleDestroy() {
        super.onLifecycleDestroy()
        lifecycleListener.onDestroy()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}