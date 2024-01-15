package io.ganguo.tab.callback

import android.view.View

/**
 * <pre>
 *   @author : leo
 *    time   : 2020/08/15
 *    desc   : Tab栏选中状态回调
 * </pre>
 * <p>
 *     每次Tab栏点击或者对应ViewPager滑动，默认都会把所有Tab栏状态，用for循环回调出去
 * </p>
 */
interface OnTabSelectedChangeListener {
    /**
     * Tab选中状态回到
     * @param position Int
     * @param oldPosition View
     */
    fun onTabSelectedChange(position: Int, oldPosition: Int)
}