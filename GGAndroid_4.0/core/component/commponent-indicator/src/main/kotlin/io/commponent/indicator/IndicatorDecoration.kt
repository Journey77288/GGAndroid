package io.commponent.indicator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/23
 *   @desc   : Indicator Space ItemDecoration
 * </pre>
 */
class IndicatorDecoration(@RecyclerView.Orientation val orientation: Int, private val itemSpace: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (itemSpace == 0) {
            return
        }
        val space = itemSpace / 2
        if (orientation == RecyclerView.HORIZONTAL) {
            outRect.set(space, 0, space, 0)
        } else {
            outRect.set(0, space, 0, space)
        }
    }
}
