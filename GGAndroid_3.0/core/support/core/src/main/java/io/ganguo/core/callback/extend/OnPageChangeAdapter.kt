package io.ganguo.core.callback.extend

import androidx.viewpager.widget.ViewPager.OnPageChangeListener


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : viewpager OnPageChangeListener
 * </pre>
 */
abstract class OnPageChangeAdapter : OnPageChangeListener {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
}