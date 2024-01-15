package io.ganguo.core.viewmodel.common.widget

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import io.ganguo.core.databinding.WidgetTextViewBinding
import io.ganguo.core.viewmodel.BaseViewModel
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.resources.R
import io.support.recyclerview.adapter.diffuitl.IDiffComparator
import io.ganguo.annotation.Gravity as GGravity

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/23
 *   @desc   : TextViewModel
 * </pre>
 */
class TextViewModel : BaseViewModel<ViewInterface<WidgetTextViewBinding>>(), IDiffComparator<String> {
    override val layoutId: Int = io.ganguo.core.R.layout.widget_text_view
    val drawableLeft = ObservableField<Drawable>()
    val drawableRight = ObservableField<Drawable>()
    val drawableTop = ObservableField<Drawable>()
    val drawableBottom = ObservableField<Drawable>()
    val drawablePadding = ObservableInt()
    val text = ObservableField<String>()
    val hint = ObservableField<String>()
    val textSize = ObservableFloat()
    val textColor = ObservableField<ColorStateList>()
    val maxLines = ObservableInt()
    val maxLength = ObservableInt()
    val typeface = ObservableInt()
    val singleLine = ObservableBoolean()
    val gravity = ObservableInt()
    val ellipsize = ObservableField<TextUtils.TruncateAt>()


    init {
        textSizeRes(R.dimen.font_15)
        textColor(Color.BLACK)
        maxLines(Int.MAX_VALUE)
        maxLength(Int.MAX_VALUE)
        gravity(Gravity.START)
        fontTypeface(Typeface.NORMAL)
        ellipsize(TextUtils.TruncateAt.END)
    }


    /**
     * 居中位置
     * @param gravity Int
     */
    fun gravity(@GGravity gravity: Int) {
        this.gravity.set(gravity)
    }

    /**
     * 设置超出一行...显示位置
     * @param ellipsize TruncateAt
     * @return TextViewModel<V>
     */
    fun ellipsize(ellipsize: TextUtils.TruncateAt = TextUtils.TruncateAt.END) {
        this.ellipsize.set(ellipsize)
    }

    /**
     * 是否为单行
     * @param isLine Boolean
     * @return TextViewModel<V>
     */
    fun singleLine(isLine: Boolean) {
        singleLine.set(isLine)
    }


    /**
     * 最大行数
     * @param lines Int
     * @return TextViewModel<V>
     */
    fun maxLines(@IntRange lines: Int) {
        maxLines.set(lines)
    }


    /**
     * 文字最大长度
     * @param length Int
     * @return TextViewModel<V>
     */
    fun maxLength(@IntRange length: Int) {
        maxLength.set(length)
    }


    /**
     * 设置字体大小
     * @param sizeRes Int
     * @return TextViewModel<V>
     */
    fun textSizeRes(@DimenRes sizeRes: Int) {
        textSize(getDimension(sizeRes))
    }


    /**
     * 设置字体大小
     * @param size Int
     * @return TextViewModel<V>
     */
    fun textSize(@Px size: Float) {
        textSize.set(size)
    }

    /**
     * 设置字体风格（普通、粗体、斜体）
     * @param typeface Int
     * @return TextViewModel<V>
     */
    fun fontTypeface(typeface: Int = Typeface.NORMAL) {
        this.typeface.set(typeface)
    }


    /**
     * 设置空文字提示
     * @param strRes Int
     * @return TextViewModel<V>
     */
    fun hintRes(@StringRes strRes: Int) {
        hint(getString(strRes))
    }


    /**
     * 设置空文字提示
     * @param str String
     * @return TextViewModel<V>
     */
    fun hint(str: String) {
        hint.set(str)
    }


    /**
     * 设置文字
     * @param strRes Int
     * @return TextViewModel<V>
     */
    fun textRes(@StringRes strRes: Int) {
        text(getString(strRes))
    }


    /**
     * 设置文字
     * @param str String
     * @return TextViewModel<V>
     */
    fun text(str: String) {
        text.set(str)
    }


    /**
     * TextView 左侧图片
     * @param drawable Drawable
     * @return TextViewModel<V>
     */
    fun drawableLeft(drawable: Drawable) {
        drawableLeft.set(drawable)
    }

    /**
     * TextView 左侧图片
     * @param drawableRes Int
     * @return TextViewModel<V>
     */
    fun drawableLeftRes(@DrawableRes drawableRes: Int) {
        getDrawable(drawableRes)?.let {
            drawableLeft(it)
        }
    }


    /**
     * TextView 右侧图片
     * @param drawable Drawable
     * @return TextViewModel<V>
     */
    fun drawableRight(drawable: Drawable) {
        drawableRight.set(drawable)
    }

    /**
     * TextView 右侧图片
     * @param drawableRes Int
     * @return TextViewModel<V>
     */
    fun drawableRightRes(@DrawableRes drawableRes: Int) {
        getDrawable(drawableRes)?.let {
            drawableRight(it)
        }
    }


    /**
     * TextView 顶部图片
     * @param drawable Drawable
     * @return TextViewModel<V>
     */
    fun drawableTop(drawable: Drawable) {
        drawableTop.set(drawable)
    }

    /**
     * TextView 顶部图片
     * @param drawableRes Int
     * @return TextViewModel<V>
     */
    fun drawableTopRes(@DrawableRes drawableRes: Int) {
        getDrawable(drawableRes)?.let {
            drawableTop(it)
        }
    }


    /**
     * TextView 左侧图片
     * @param drawable Drawable
     * @return TextViewModel<V>
     */
    fun drawableBottom(drawable: Drawable) {
        drawableBottom.set(drawable)
    }

    /**
     * TextView 底部图片
     * @param drawableRes Int
     * @return TextViewModel<V>
     */
    fun drawableBottomRes(@DrawableRes drawableRes: Int) {
        getDrawable(drawableRes)?.let {
            drawableBottom(it)
        }
    }

    /**
     * drawable 与 文字间距
     * @param padding Int
     */
    fun drawablePadding(@Dimension padding: Int) {
        drawablePadding.set(padding)
    }

    /**
     * drawable 与 文字间距
     * @param paddingRes Int
     */
    fun drawablePaddingRes(@DimenRes paddingRes: Int) {
        drawablePadding(getDimensionPixelOffset(paddingRes))
    }


    /**
     * 设置颜色值
     * @param colorList ColorStateList
     */
    fun textColor(colorList: ColorStateList) {
        textColor.set(colorList)
    }

    /**
     * 设置颜色值
     * @param colorRes Int
     */
    fun textColorRes(@ColorRes colorRes: Int) {
        textColor.set(ColorStateList.valueOf(getColor(colorRes)))
    }

    /**
     * 设置颜色值
     * @param color Int
     */
    fun textColor(color: Int) {
        textColor.set(ColorStateList.valueOf(color))
    }


    override fun onViewAttached(view: View) {
    }

    override fun itemEquals(t: String): Boolean {
        return t == getItem()
    }

    override fun getItem(): String {
        return text.get().orEmpty()
    }


    companion object {


        /**
         * sample 说明
         * @param title String
         * @return ViewModel<*>
         */
        @JvmStatic
        fun sampleExplainVModel(title: String,click:()->Unit = {}): ViewModel<*> = let {
            TextViewModel().apply {
                gravity(Gravity.CENTER)
                text(title)
                height(ViewGroup.LayoutParams.WRAP_CONTENT)
                width(ViewGroup.LayoutParams.MATCH_PARENT)
                paddingHorizontalRes(R.dimen.dp_10)
                paddingVerticalRes(R.dimen.dp_10)
                textSizeRes(R.dimen.font_12)
                textColorRes(R.color.gray_dim)
                click {
                    click.invoke()
                }
            }
        }
    }

}
