package io.commponent.indicator

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import top.defaults.drawabletoolbox.DrawableBuilder

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/23
 *   @desc   : Circle Dot indicator
 * </pre>
 */
class CircleDotIndicator(private val indicatorCount: Int, @ColorInt val unSelectColor: Int, @ColorInt val selectColor: Int) : BaseIndicator() {

    /**
     * create Indicator View
     * @param parent ViewGroup
     * @param position Int
     * @return View
     */
    override fun createView(parent: ViewGroup, position: Int): View = let {
        View(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(widthPx, heightPx)
            background = createDrawable()
        }
    }


    /**
     * background Drawable
     * @return Drawable
     */
    private fun createDrawable(): Drawable = let {
        DrawableBuilder()
                .rounded()
                .solidColorSelected(selectColor)
                .solidColor(unSelectColor)
                .build()
    }

    override fun getCount(): Int {
        return indicatorCount
    }
}
