@file:JvmName("ViewBindingAdapter")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/29
 *     desc   : View DataBinding Adapter
 * </pre>
 */
package io.ganguo.databinding

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.shapes.Shape
import android.os.Build
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewOutlineProvider
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import io.ganguo.databinding.bean.CornerType
import io.ganguo.databinding.widget.ThrottleClickListener


/**
 * Binding sets the View size
 * @receiver View
 * @param height Int
 * @param width Int
 */
@BindingAdapter(
		value = [
			"android:bind_width_to_view",
			"android:bind_height_to_view"],
		requireAll = false
)
fun View.bindSizeDimension(@Dimension width: Int?, @Dimension height: Int?) {
    if (width != null) {
        layoutParams.width = width
    }
    if (height != null) {
        layoutParams.height = height
    }
}


/**
 * Binding sets the View size
 * @receiver View
 * @param widthResId Int
 * @param heightResId Int
 */
@BindingAdapter(
		value = [
			"android:bind_width_res_to_view",
			"android:bind_height_res_to_view"],
		requireAll = false
)
fun View.bindSizeDimenRes(@DimenRes widthResId: Int = 0, @DimenRes heightResId: Int = 0) {
    var params = layoutParams
    if (widthResId != 0) {
        params.width = resources.getDimensionPixelOffset(widthResId)
    }
    if (heightResId != 0) {
        params.height = resources.getDimensionPixelOffset(heightResId)
    }
    layoutParams = params
}

/**
 * Binding sets the View background resource
 * @receiver View
 * @param drawableRes Int
 */
@BindingAdapter("android:bind_drawable_res_to_view")
fun View.bindBackgroundResource(@DrawableRes drawableRes: Int?) {
    drawableRes?.let {
        ResourcesCompat
                .getDrawable(resources, drawableRes, null)
                ?.let {
                    ViewCompat.setBackground(this, it)
                }
    }
}


/**
 * Binding sets the View background drawable
 * @receiver View
 * @param drawable Drawable?
 */
@BindingAdapter("android:bind_background_drawable_to_view")
fun View.bindBackgroundDrawable(drawable: Drawable?) {
    drawable?.let {
        ViewCompat.setBackground(this, it)
    }
}


/**
 * Binding sets the View background color
 * @receiver View
 * @param color Int?
 */
@BindingAdapter("android:bind_background_color_to_view")
fun View.bindBackgroundColor(color: Int?) {
    color?.let {
        this.setBackgroundColor(color)
    }
}

/**
 * Binding sets the View background color Resource
 * View
 * @param colorRes Int?
 */
@BindingAdapter("android:bind_background_color_res_to_view")
fun View.bindBackgroundColorResource(@ColorRes colorRes: Int?) {
    colorRes?.let {
        bindBackgroundResource(colorRes)
    }
}


/**
 * Binding sets the view to display state
 * View
 * @param visibility Int?
 */
@BindingAdapter("android:bind_visibility_to_view")
fun View.bindVisibility(visibility: Int?) {
    visibility?.let {
        when (it) {
			View.VISIBLE -> setVisibility(View.VISIBLE)
			View.INVISIBLE -> setVisibility(View.INVISIBLE)
			View.GONE -> setVisibility(View.GONE)
            else -> {
                //ToDo: Do nothing
            }
        }
    }
}


/**
 * Binding sets the view to display state
 * @receiver View
 * @param isVisible Boolean?
 */
@BindingAdapter("android:bind_visible_boolean_to_view")
fun View.bindVisible(isVisible: Boolean?) {
    isVisible?.let {
        visibility = if (it) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}

/**
 * Binding sets the outer margin of the View
 * @receiver View
 * @param leftMargin Int?
 * @param topMargin Int?
 * @param rightMargin Int?
 * @param bottomMargin Int?
 */
@BindingAdapter(
		value = [
			"android:bind_margin_left_to_view",
			"android:bind_margin_top_to_view",
			"android:bind_margin_right_to_view",
			"android:bind_margin_bottom_to_view"],
		requireAll = false
)
fun View.bindMargin(
		leftMargin: Int? = null,
		topMargin: Int? = null,
		rightMargin: Int? = null,
		bottomMargin: Int? = null
) {
    if (layoutParams == null) {
        layoutParams =
                MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
    }
    if (layoutParams !is MarginLayoutParams) {
        return
    }
    var params = layoutParams as MarginLayoutParams
    if (leftMargin != null) {
        params.leftMargin = leftMargin
    }
    if (topMargin != null) {
        params.topMargin = topMargin
    }
    if (rightMargin != null) {
        params.rightMargin = rightMargin
    }
    if (bottomMargin != null) {
        params.bottomMargin = bottomMargin
    }
    this.layoutParams = params
}


/**
 * Binding sets the View selected state
 * View
 * @param select Boolean
 */
fun View.bindSelected(select: Boolean) {
    isSelected = select
}

/**
 * Binding sets the View inner margin
 * @receiver View
 * @param paddingLeft Int
 * @param paddingTop Int
 * @param paddingRight Int
 * @param paddingBottom Int
 */
@BindingAdapter(
		value = [
			"android:bind_padding_left_to_view",
			"android:bind_padding_top_to_view",
			"android:bind_padding_right_to_view",
			"android:bind_padding_bottom_to_view"],
		requireAll = false
)
fun View.bindPadding(
		paddingLeft: Int? = null,
		paddingTop: Int? = null,
		paddingRight: Int? = null,
		paddingBottom: Int? = null
) {
    var left = paddingLeft ?: getPaddingLeft()
    var right = paddingRight ?: getPaddingRight()
    var top = paddingTop ?: getPaddingTop()
    var bottom = paddingBottom ?: getPaddingBottom()
    setPadding(left, top, right, bottom)
}


/**
 * Setting ViewGroup Elevation Height
 * @receiver ViewGroup
 * @param isEnable Boolean
 */
@BindingAdapter(value = ["android:bind_enable_elevation_to_layout", "android:bind_enable_elevation_size"])
fun View.bindEnableElevation(isEnable: Boolean, size: Float = 10f) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        outlineProvider = if (isEnable) {
            ViewOutlineProvider.BOUNDS
        } else {
            ViewOutlineProvider.BACKGROUND
        }
        elevation = if (isEnable) {
            size
        } else {
            0f
        }
    }
}

/**
 * Single Click View
 * @receiver View
 * @param listener OnClickListener?
 */
@BindingAdapter(value = ["android:onSingleClick"])
fun View.onSingleClick(listener: View.OnClickListener?) {
    setOnClickListener(ThrottleClickListener(listener = listener))
}

/**
 * Bind drawable to View's background
 *
 * setCornerRadii 是设置左上、右上、右下、左下四个边角的圆角半径，分别设置x和y轴半径值，所以需要传入长度为8的Float数组
 */
@BindingAdapter(
    value = [
        "android:bind_drawable_solid_color",
        "android:bind_drawable_radius",
        "android:bind_drawable_radius_type",
        "android:bind_drawable_stroke_color",
        "android:bind_drawable_stroke_width"
    ],
    requireAll = false
)
fun View.bindBackgroundDrawable(
    @ColorInt color: Int = Color.TRANSPARENT,
    @Dimension radius: Float = 0f,
    cornerType: CornerType? = CornerType.ALL,
    @ColorInt strokeColor: Int = Color.TRANSPARENT,
    @Dimension strokeWidth: Float = 0f
) {
    background = GradientDrawable()
        .apply {
            shape = GradientDrawable.RECTANGLE
            setColor(color)
            when(cornerType) {
                CornerType.TOP_LEFT -> cornerRadii = floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, 0f, 0f)
                CornerType.TOP_RIGHT -> cornerRadii = floatArrayOf(0f, 0f, radius, radius, 0f, 0f, 0f, 0f)
                CornerType.BOTTOM_LEFT -> cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, radius, radius)
                CornerType.BOTTOM_RIGHT -> cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, radius, radius, 0f, 0f)
                CornerType.TOP -> cornerRadii = floatArrayOf(radius, radius, radius, radius, 0f, 0f, 0f, 0f)
                CornerType.BOTTOM -> cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, radius, radius, radius, radius)
                CornerType.LEFT -> cornerRadii = floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
                CornerType.RIGHT -> cornerRadii = floatArrayOf(0f, 0f, radius, radius, radius, radius, 0f, 0f)
                else -> cornerRadius = radius
            }
            setStroke(strokeWidth.toInt(), strokeColor)
        }
}

/**
 * Bind oval drawable to View's background
 */
@BindingAdapter(
    value = [
        "android:bind_drawable_oval_solid_color",
        "android:bind_drawable_stroke_color",
        "android:bind_drawable_stroke_width"
    ],
    requireAll = false
)
fun View.bindBackgroundOvalDrawable(
    @ColorInt color: Int = Color.TRANSPARENT,
    @ColorInt strokeColor: Int = Color.TRANSPARENT,
    @Dimension strokeWidth: Float = 0f
) {
    background = GradientDrawable()
        .apply {
            shape = GradientDrawable.OVAL
            setColor(color)
            setStroke(strokeWidth.toInt(), strokeColor)
        }
}



