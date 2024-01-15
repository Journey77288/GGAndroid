package io.ganguo.core.callback

import androidx.viewpager2.widget.ViewPager2
import io.ganguo.tab.callback.TabLayoutMediator

/**
 * <pre>
 *   @author : leo
 *   @time : 2020/07/14
 *   @desc :ViewPagerChangeListener
 * </pre>
 */
class TabMediatorWrapper(private val mediator: TabLayoutMediator) : ViewPager2.OnPageChangeCallback() {
    override fun onPageScrollStateChanged(state: Int) {
        super.onPageScrollStateChanged(state)
        mediator.onPageScrollStateChanged(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        mediator.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        mediator.onPageSelected(position)
    }
}
