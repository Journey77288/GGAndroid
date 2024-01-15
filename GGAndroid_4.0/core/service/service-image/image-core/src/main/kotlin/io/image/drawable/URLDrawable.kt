package io.image.drawable

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer

/**
 * <pre>
 * author : leo
 * time   : 2019/07/18
 * desc   : URL Drawable
 * </pre>
 */
class URLDrawable : DrawableContainer() {
    private var drawable: Drawable? = null
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (drawable != null) {
            drawable!!.draw(canvas)
        }
    }

    fun setDrawable(drawable: Drawable?): URLDrawable {
        this.drawable = drawable
        return this
    }
}
