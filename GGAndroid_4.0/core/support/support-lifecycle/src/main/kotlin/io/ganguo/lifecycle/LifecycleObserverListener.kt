package io.ganguo.lifecycle

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Lifecycle Listener
 * </pre>
 */
interface LifecycleObserverListener {

    fun onStart()

    fun onResume()

    fun onPause()

    fun onDestroy()


    fun onStop() {
    }

    fun onCreate() {
    }

    fun onRelease() {

    }


}
