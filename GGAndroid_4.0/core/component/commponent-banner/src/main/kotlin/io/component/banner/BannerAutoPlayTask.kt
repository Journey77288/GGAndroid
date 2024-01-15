package io.component.banner

import java.lang.ref.WeakReference

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Automatically play Runnable Task
 * </pre>
 */
class BannerAutoPlayTask(banner: Banner) : Runnable {
    private val weakBanner: WeakReference<Banner> = WeakReference(banner)

    override fun run() {
        val banner = weakBanner.get()
        if (banner != null && banner.isAutoPlay) {
            val count = banner.itemCount
            if (count == 0) {
                return
            }
            val next = (banner.currentItem + 1) % count
            banner.setCurrentItem(next)
            banner.postDelayed(banner.loopTask, banner.shufflingIntervalMillisecond)
        }
    }
}
