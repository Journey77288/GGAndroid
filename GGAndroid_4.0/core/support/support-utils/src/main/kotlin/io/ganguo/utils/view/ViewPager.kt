package io.ganguo.utils.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2


/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : ViewPager related tools methods
 * </pre>
 */

/**
 * Set up the LinearLayoutManager for ViewPager2 through reflection
 * @receiver ViewPager2
 * @param layoutManager LayoutManager
 */
fun ViewPager2.setLayoutManager(manager: LinearLayoutManager) {
    try {
        val rv = getChildAt(0) as RecyclerView
        rv.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        rv.layoutManager = manager

        val field = ViewPager2::class.java.getDeclaredField("mLayoutManager")
        field.isAccessible = true
        field.set(this, manager)
        val transformerAdapterField = ViewPager2::class.java.getDeclaredField("mPageTransformerAdapter")
        transformerAdapterField.isAccessible = true
        val transformerAdapter = transformerAdapterField.get(this)
        if (transformerAdapter != null) {
            val aClass: Class<*> = transformerAdapter.javaClass
            val layoutManager = aClass.getDeclaredField("mLayoutManager")
            layoutManager.isAccessible = true
            layoutManager.set(transformerAdapter, manager)
        }
        val eventAdapterField = ViewPager2::class.java.getDeclaredField("mScrollEventAdapter")
        eventAdapterField.isAccessible = true
        val scrollEventAdapter = eventAdapterField.get(this)
        if (scrollEventAdapter != null) {
            val aClass: Class<*> = scrollEventAdapter.javaClass
            val layoutManager = aClass.getDeclaredField("mLayoutManager")
            layoutManager.isAccessible = true
            layoutManager.set(scrollEventAdapter, manager)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 调整ViewPager的滑动灵敏度
 * 避免RecyclerView上下滑动容易触发ViewPager的左右滑动情况
 *
 * @receiver ViewPager2
 */
fun ViewPager2.changeViewPagerTouchSlop() {
    try {
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true

        val recyclerView = recyclerViewField.get(this)

        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true

        val touchSlop = touchSlopField.get(recyclerView) as Int
        touchSlopField.set(recyclerView, touchSlop * 2)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 设置ViewPager2的Overscroll属性
 *
 * @receiver ViewPager2
 * @param overscrollMode Int
 */
fun ViewPager2.setOverscrollMode(overscrollMode: Int) {
    val firstChild = getChildAt(0)
    if (firstChild is RecyclerView) {
        firstChild.overScrollMode = overscrollMode
    }
}