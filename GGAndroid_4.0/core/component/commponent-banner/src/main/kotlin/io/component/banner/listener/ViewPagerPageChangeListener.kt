package io.component.banner.listener

import androidx.viewpager2.widget.ViewPager2
import io.component.banner.Banner
import io.component.banner.BannerHelper
import java.lang.ref.WeakReference

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/12/26
 *   @desc   : ViewPager Page Change Callback（Use only in packages）
 * </pre>
 */
internal class ViewPagerPageChangeListener(banner: Banner) : ViewPager2.OnPageChangeCallback() {
    private val weakBanner = WeakReference(banner)
    private var tempPosition = Banner.INVALID_VALUE
    private var isScrolled = false

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        weakBanner.get()?.let {
            val realPosition = getRealPosition(position)
            it.pageChangeListener?.onPageScrolled(realPosition, positionOffset, positionOffsetPixels)
        }
    }

    override fun onPageSelected(position: Int) {
        weakBanner.get()?.let {
            tempPosition = position
            if (isScrolled) {
                it.pageChangeListener?.onPageSelected(getRealPosition(position))
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        weakBanner.get()?.let {
            if (state == ViewPager2.SCROLL_STATE_DRAGGING || state == ViewPager2.SCROLL_STATE_IDLE) {
                isScrolled = state == ViewPager2.SCROLL_STATE_DRAGGING
                if (tempPosition == Banner.INVALID_VALUE || !it.isLoop) {
                    return
                }
                if (tempPosition == 1) {
                    it.setCurrentItem(it.realCount + 1, false)
                } else if (tempPosition == it.realCount + 2) {
                    it.setCurrentItem(2, false)
                }
            } else {
                isScrolled = true
            }
            it.pageChangeListener?.onPageScrollStateChanged(state)
        }
    }

    /**
     * get real position
     * The default position is not correct when the loop is turned on and the real position needs to be calculated
     * @param position Int
     * @return Int
     */
    private fun getRealPosition(position: Int): Int = let {
        weakBanner.get()?.let {
            BannerHelper.getRealPosition(it.isLoop, position, it.realCount)
        } ?: 0
    }
}

