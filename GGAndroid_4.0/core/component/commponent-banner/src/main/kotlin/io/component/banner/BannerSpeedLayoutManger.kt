package io.component.banner

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView


/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Supports LinearLayoutManager to change the sliding rate
 * </pre>
 */
class BannerSpeedLayoutManger(context: Context, var scrollMillisecond: Long, @RecyclerView.Orientation orientation: Int)
    : LinearLayoutManager(context, orientation, false) {
    var scrollSpeed = scrollMillisecond
    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
        val linearSmoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun calculateTimeForDeceleration(dx: Int): Int {
                return scrollSpeed.toInt()
            }
        }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}
