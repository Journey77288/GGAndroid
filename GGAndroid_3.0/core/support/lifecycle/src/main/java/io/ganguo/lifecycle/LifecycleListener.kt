package io.ganguo.lifecycle

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Lifecycle Listener
 * </pre>
 */
interface LifecycleListener  {

    fun onStart()

    fun onResume()

    fun onPause()

    fun onDestroy()


    fun onStop() {

    }

    fun onCreate() {
    }


}