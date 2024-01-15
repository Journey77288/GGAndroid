package io.component.banner

import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Adapter Data Change Listener
 * </pre>
 */
class BannerAdapterDataObserver(banner: Banner) : RecyclerView.AdapterDataObserver() {
    private val weakBanner = WeakReference(banner)

    override fun onChanged() {
        val banner = weakBanner.get() ?: return
        if (banner.itemCount <= 1) {
            banner.stop()
        } else {
            banner.start()
        }
    }
}
