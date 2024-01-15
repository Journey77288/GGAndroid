package io.ganguo.picker.ui.widget

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import io.ganguo.picker.R

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/07/22
 *     desc   :
 * </pre>
 *
 * @param
 * @see
 * @author Raynor
 * @property
 */
class CheckRadioView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    private var mDrawable: Drawable? = null

    private var mSelectedColor = 0
    private var mUnSelectUdColor = 0

    constructor(context: Context) : this(context, null) {
    }

    init {
        mSelectedColor = ResourcesCompat.getColor(resources, R.color.item_checkCircle_backgroundColor, getContext().theme)
        mUnSelectUdColor == ResourcesCompat.getColor(resources, R.color.original_radio_disable, getContext().theme)
        setChecked(false)
    }

    fun setChecked(enable: Boolean) {
        if (enable) {
            setImageResource(R.drawable.ic_preview_radio_on)
            mDrawable = drawable
            mDrawable?.setColorFilter(mSelectedColor, PorterDuff.Mode.SRC_IN)
        } else {
            setImageResource(R.drawable.ic_preview_radio_off)
            mDrawable = drawable
            mDrawable?.setColorFilter(mUnSelectUdColor, PorterDuff.Mode.SRC_IN)
        }
    }

    fun setColor(color: Int) {
        if (mDrawable == null) {
            mDrawable = drawable
        }
        mDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}