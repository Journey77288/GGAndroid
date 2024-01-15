package io.component.banner

import java.lang.ref.WeakReference

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/12/26
 *   @desc   : Lifecycle Callback
 * </pre>
 */
class BannerLifecycleObserver(banner: Banner) {
    private val weakBanner = WeakReference(banner)

    /**
     * onCreate
     */
    fun onCreate() {
    }

    /**
     * onStart
     */
    fun onStart() {
        weakBanner.get()?.start()
    }

    /**
     * onResume
     */
    fun onResume() {
        weakBanner.get()?.start()
    }

    /**
     * onStop
     */
    fun onStop() {
        weakBanner.get()?.stop()
    }

    /**
     * onPause
     */
    fun onPause() {
        weakBanner.get()?.stop()
    }


    /**
     * onDestroy
     */
    fun onDestroy() {
        weakBanner.get()?.destroy()
    }
}
