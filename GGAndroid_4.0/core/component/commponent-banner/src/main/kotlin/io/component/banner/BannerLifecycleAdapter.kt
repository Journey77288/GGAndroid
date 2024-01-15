package io.component.banner

import android.util.Log
import androidx.lifecycle.*

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/12/26
 *   @desc   : Lifecycle Adapter
 * </pre>
 */
open class BannerLifecycleAdapter(private val observer: BannerLifecycleObserver) : DefaultLifecycleObserver {

    /**
     * onCreate
     */
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.i("${javaClass.simpleName}", "onLifecycleCreate")
        observer.onCreate()
    }

    /**
     * onStart
     */
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.i("${javaClass.simpleName}", "onLifecycleStart")
        observer.onStart()
    }

    /**
     * onResume
     */
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.i("${javaClass.simpleName}", "onLifecycleResume")
        observer.onResume()
    }

    /**
     * onStop
     */
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.i("${javaClass.simpleName}", "onLifecycleStop")
        observer.onStop()
    }

    /**
     * onPause
     */
    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.i("${javaClass.simpleName}", "onLifecyclePause")
        observer.onPause()
    }


    /**
     * onDestroy
     */
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i("${javaClass.simpleName}", "onLifecycleDestroy")
        observer.onDestroy()
    }


}
