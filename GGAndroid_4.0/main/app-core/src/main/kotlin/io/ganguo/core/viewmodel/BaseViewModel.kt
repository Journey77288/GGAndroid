package io.ganguo.core.viewmodel

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/26
 *   @desc   : ViewModel基类，已封装通用属于。（原[BaseViewModel]已修改为[ViewModel],非用到通用属性的情况，请继承[ViewModel]）
 * </pre>
 */
abstract class BaseViewModel<V : ViewInterface<*>> : ViewModel<V>() {
    val backgroundDrawable = ObservableField<Drawable>()
    val width = ObservableInt()
    val height = ObservableInt()
    val paddingLeft = ObservableInt()
    val paddingRight = ObservableInt()
    val paddingBottom = ObservableInt()
    val paddingTop = ObservableInt()
    val marginLeft = ObservableInt()
    val marginRight = ObservableInt()
    val marginBottom = ObservableInt()
    val marginTop = ObservableInt()
    val enabled = ObservableBoolean()
    val clickable = ObservableBoolean()
    val visible = ObservableInt()
    val selected = ObservableBoolean()
    private var click: ((View) -> Unit)? = null


    init {
        width(ViewGroup.LayoutParams.MATCH_PARENT)
        height(ViewGroup.LayoutParams.WRAP_CONTENT)
        enabled(true)
        clickable(true)
        visible(View.VISIBLE)
    }


    /**
     * 点击回调
     * @return View.OnClickListener
     */
    open fun actionClick(): View.OnClickListener = let {
        View.OnClickListener {
            click?.invoke(it)
        }
    }

    /**
     * 点击事件设置
     * @param func Function1<TextView, Unit>
     */
    open fun click(func: ((View) -> Unit)) {
        click = func
    }


    /**
     * 设置是否可用
     * @param value Boolean
     */
    open fun enabled(value: Boolean) {
        enabled.set(value)
    }

    /**
     * 设置是否可点击
     * @param value Boolean
     */
    open fun clickable(value: Boolean) {
        clickable.set(value)
    }


    /**
     * 设置控件是否显示
     * @param visible Boolean
     */
    open fun visible(visible: Int = View.VISIBLE) {
        this.visible.set(visible)
    }


    /**
     * 设置控件宽度
     * @param sizeRes Int
     */
    open fun widthRes(@DimenRes sizeRes: Int) {
        width(getDimensionPixelOffset(sizeRes))
    }

    /**
     * 设置控件宽度
     * @param size Int
     */
    open fun width(@Px size: Int) {
        width.set(size)
    }


    /**
     * 设置控件高度
     * @param sizeRes Int
     */
    open fun heightRes(@DimenRes sizeRes: Int) {
        height(getDimensionPixelOffset(sizeRes))
    }

    /**
     * 设置控件高度
     * @param size Int
     */
    open fun height(@Px size: Int) {
        height.set(size)
    }


    /**
     * 设置控件尺寸
     * @param widthRes Int
     * @param heightRes Int
     */
    open fun sizeRes(@DimenRes widthRes: Int, @DimenRes heightRes: Int) {
        widthRes(widthRes)
        heightRes(heightRes)
    }


    /**
     * 设置控件尺寸
     * @param width Int
     * @param height Int
     */
    open fun size(@Px width: Int, @Px height: Int) {
        width(width)
        height(height)
    }


    /**
     * drawable资源
     * @param drawableRes Int
     */
    open fun backgroundDrawableRes(@DrawableRes drawableRes: Int) {
        getDrawable(drawableRes)?.let {
            backgroundDrawable(it)
        }
    }


    /**
     * drawable对象
     * @param drawable Drawable
     */
    open fun backgroundDrawable(drawable: Drawable) {
        backgroundDrawable.set(drawable)
    }


    /**
     * 颜色资源
     * @param colorRes Int
     */
    open fun backgroundColorRes(@ColorRes colorRes: Int) {
        getDrawable(colorRes)?.let {
            backgroundDrawable(it)
        }
    }

    /**
     * 颜色资源
     * @param color Int
     */
    open fun backgroundColor(@ColorInt color: Int) {
        backgroundDrawable(ColorDrawable(color))
    }


    /**
     * 设置内边距
     * @param padding Int
     */
    open fun padding(@Px padding: Int) {
        padding(padding, padding, padding, padding)
    }

    /**
     * 设置内边距
     * @param paddingRes Int
     */
    open fun paddingRes(@DimenRes paddingRes: Int) {
        padding(getDimensionPixelOffset(paddingRes))
    }

    /**
     * 设置内边距
     * @param left Int
     * @param top Int
     * @param right Int
     * @param bottom Int
     */
    fun padding(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int) {
        paddingLeft.set(left)
        paddingRight.set(right)
        paddingTop.set(top)
        paddingBottom.set(bottom)
    }

    /**
     * 设置内边距
     * @param leftRes Int
     * @param topRes Int
     * @param rightRes Int
     * @param bottomRes Int
     */
    fun paddingRes(@DimenRes leftRes: Int, @DimenRes topRes: Int, @DimenRes rightRes: Int, @DimenRes bottomRes: Int) {
        padding(
                getDimensionPixelOffset(leftRes),
                getDimensionPixelOffset(topRes),
                getDimensionPixelOffset(rightRes),
                getDimensionPixelOffset(bottomRes)
        )
    }

    /**
     * 设置上下内边距
     * @param paddingRes Int
     */
    fun paddingVerticalRes(@DimenRes paddingRes: Int) {
        paddingVertical(
                getDimensionPixelOffset(paddingRes)
        )
    }

    /**
     * 设置上下内边距
     * @param padding Int
     */
    fun paddingVertical(@Px padding: Int) {
        padding(
                0,
                padding,
                0,
                padding
        )
    }


    /**
     * 设置左右内边距
     * @param paddingRes Int
     */
    fun paddingHorizontalRes(@DimenRes paddingRes: Int) {
        paddingHorizontal(
                getDimensionPixelOffset(paddingRes)
        )
    }

    /**
     * 设置左右内边距
     * @param padding Int
     */
    fun paddingHorizontal(@Px padding: Int) {
        padding(
                padding,
                0,
                padding,
                0
        )
    }

    /**
     * 设置左侧内边距
     * @param padding Int
     */
    fun paddingLeft(@Px padding: Int) {
        paddingLeft.set(padding)
    }

    /**
     * 设置顶部内边距
     * @param padding Int
     */
    fun paddingTop(@Px padding: Int) {
        paddingTop.set(padding)
    }

    /**
     * 设置右侧内边距
     * @param padding Int
     */
    fun paddingRight(@Px padding: Int) {
        paddingRight.set(padding)
    }

    /**
     * 设置底部内边距
     * @param padding Int
     */
    fun paddingBottom(@Px padding: Int) {
        paddingBottom.set(padding)
    }


    /**
     * 设置左侧内边距
     * @param paddingRs Int
     */
    fun paddingLeftRes(@DimenRes paddingRs: Int) {
        paddingLeft.set(getDimensionPixelOffset(paddingRs))
    }

    /**
     * 设置顶部内边距
     * @param paddingRs Int
     */
    fun paddingTopRes(@DimenRes paddingRs: Int) {
        paddingTop.set(getDimensionPixelOffset(paddingRs))
    }

    /**
     * 设置右侧内边距
     * @param paddingRs Int
     */
    fun paddingRightRes(@DimenRes paddingRs: Int) {
        paddingRight.set(getDimensionPixelOffset(paddingRs))
    }

    /**
     * 设置底部内边距
     * @param paddingRs Int
     */
    open fun paddingBottomRes(@DimenRes paddingRs: Int) {
        paddingBottom.set(getDimensionPixelOffset(paddingRs))
    }

    /**
     * 设置外边距
     * @param margin Int
     */
    open fun margin(@Px margin: Int) {
        margin(margin, margin, margin, margin)
    }

    /**
     * 设置外边距
     * @param marginRes Int
     */
    open fun marginRes(@DimenRes marginRes: Int) {
        margin(getDimensionPixelOffset(marginRes))
    }

    /**
     * 设置外边距
     * @param left Int
     * @param top Int
     * @param right Int
     * @param bottom Int
     */
    open fun margin(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int) {
        marginLeft.set(left)
        marginRight.set(right)
        marginTop.set(top)
        marginBottom.set(bottom)
    }


    /**
     * 设置外边距
     * @param leftRes Int
     * @param topRes Int
     * @param rightRes Int
     * @param bottomRes Int
     */
    open fun marginRes(@DimenRes leftRes: Int, @DimenRes topRes: Int, @DimenRes rightRes: Int, @DimenRes bottomRes: Int) {
        margin(
                getDimensionPixelOffset(leftRes),
                getDimensionPixelOffset(topRes),
                getDimensionPixelOffset(rightRes),
                getDimensionPixelOffset(bottomRes)
        )
    }


    /**
     * 设置左侧外边距
     * @param margin Int
     */
    open fun marginLeft(@Px margin: Int) {
        marginLeft.set(margin)
    }

    /**
     * 设置顶部外边距
     * @param margin Int
     */
    open fun marginTop(@Px margin: Int) {
        marginTop.set(margin)
    }


    /**
     * 设置底部外边距
     * @param margin Int
     */
    open fun marginBottom(@Px margin: Int) {
        marginBottom.set(margin)
    }


    /**
     * 设置右侧外边距
     * @param margin Int
     */
    open fun marginRight(@Px margin: Int) {
        marginRight.set(margin)
    }


    /**
     * 设置左侧外边距
     * @param marginRes Int
     */
    open fun marginLeftRes(@DimenRes marginRes: Int) {
        marginLeft.set(getDimensionPixelOffset(marginRes))
    }

    /**
     * 设置顶部外边距
     * @param marginRes Int
     */
    open fun marginTopRes(@DimenRes marginRes: Int) {
        marginTop.set(getDimensionPixelOffset(marginRes))
    }


    /**
     * 设置底部外边距
     * @param marginRes Int
     */
    open fun marginBottomRes(@DimenRes marginRes: Int) {
        marginBottom.set(getDimensionPixelOffset(marginRes))
    }


    /**
     * 设置右侧外边距
     * @param marginRes Int
     */
    open fun marginRightRes(@DimenRes marginRes: Int) {
        marginRight.set(getDimensionPixelOffset(marginRes))
    }


    /**
     * 设置上下外边距
     * @param marginRes Int
     */
    open fun marginVerticalRes(@DimenRes marginRes: Int) {
        marginVertical(
                getDimensionPixelOffset(marginRes)
        )
    }

    /**
     * 设置上下外边距
     * @param margin Int
     */
    open fun marginVertical(@Px margin: Int) {
        marginTop(margin)
        marginBottom(margin)
    }


    /**
     * 设置左右外边距
     * @param marginRes Int
     */
    open fun marginHorizontalRes(@DimenRes marginRes: Int) {
        marginHorizontal(
                getDimensionPixelOffset(marginRes)
        )
    }

    /**
     * 设置左右外边距
     * @param margin Int
     */
    open fun marginHorizontal(@Px margin: Int) {
        marginLeft(margin)
        marginRight(margin)
    }


}
