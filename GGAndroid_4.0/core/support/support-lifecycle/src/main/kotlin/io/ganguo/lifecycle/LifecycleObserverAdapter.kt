package io.ganguo.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/07
 *   @desc   : LifecycleObserverAdapter
 * </pre>
 */
class LifecycleObserverAdapter(private val listener: LifecycleObserverListener) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        listener.onCreate()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        listener.onStart()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        listener.onResume()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        listener.onStop()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        listener.onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        listener.onRelease()
        listener.onDestroy();
    }
}
