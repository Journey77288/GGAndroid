package io.ganguo.core.ui.bindingadapter.textview

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html
import android.text.InputType
import android.util.TypedValue
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ViewBindingAdapter
import io.ganguo.core.R
import io.ganguo.core.context.BaseApp.Companion.me

/**
 * <pre>
 * author : leo
 * time   : 2018/05/29
 * desc   : TextView DataBinding绑定工具类
</pre> *
 */
object BindingTextAdapter : ViewBindingAdapter() {
    /**
     * xml绑定设置TextView DrawableLeft
     *
     * @param view
     * @param id   图片资源Id
     */
    @BindingAdapter("android:bind_text_drawableLeft_res")
    fun onBindTextDrawableLeftRes(view: TextView, @DrawableRes id: Int) {
        view.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
    }

    /**
     * xml绑定设置TextView DrawableRight
     *
     * @param view
     * @param id   图片资源Id
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter("android:bind_text_drawableRight_res")
    fun onBindTextDrawableRightRes(view: TextView, @DrawableRes id: Int) {
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)
    }

    /**
     * xml绑定设置TextView Html文本
     *
     * @param view
     * @param text html文本
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter("android:bind_text")
    fun onBindStringToTextView(view: TextView, text: CharSequence?) {
        view.text = text
    }

    /**
     * xml绑定设置TextView Html文本
     *
     * @param view
     * @param text html文本
     */
    @BindingAdapter("android:bind_text_html")
    fun onBindHtmlToTextView(view: TextView, text: String?) {
        view.text = Html.fromHtml(text)
    }

    /**
     * xml绑定设置TextView字体颜色
     *
     * @param view
     * @param colorRes 颜色资源Id
     */
    @SuppressLint("ResourceType")
    @BindingAdapter("android:bind_text_colorRes")
    fun onBindTextColorRes(view: TextView, @ColorRes colorRes: Int) {
        if (colorRes < 0) {
            return
        }
        view.setTextColor(ContextCompat.getColorStateList(me(), colorRes))
    }

    /**
     * xml绑定设置TextView字体颜色
     *
     * @param view
     * @param color
     */
    @kotlin.jvm.JvmStatic
    @SuppressLint("ResourceType")
    @BindingAdapter("android:bind_text_color")
    fun onBindTextColor(view: TextView, @ColorInt color: Int) {
        view.setTextColor(color)
    }

    /**
     * xml绑定设置TextView 选中颜色
     *
     * @param view
     * @param textSelectColor 颜色资源Id
     */
    @kotlin.jvm.JvmStatic
    @SuppressLint("ResourceType")
    @BindingAdapter("android:bind_text_select_colorRes")
    fun onBindTextSelectColor(view: TextView, @ColorRes textSelectColor: Int) {
        if (textSelectColor <= 0) {
            return
        }
        view.setTextColor(ContextCompat.getColorStateList(me(), textSelectColor))
    }

    /**
     * xml绑定设置TextView 长度
     *
     * @param textView
     * @param maxLength 文本字符长度
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["android:bind_text_maxLength"])
    fun onBindMaxLength(textView: TextView, maxLength: Int) {
        if (maxLength == -1) {
            return
        }
        if (maxLength >= textView.text.length) {
            return
        }
        textView.text = textView.text.subSequence(0, maxLength)
    }

    /**
     * xml绑定设置TextView 字体大小
     *
     * @param view TextView
     * @param unit size单位 see[TypedValue]
     * @param size 字体大小sp
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["android:bind_text_sizeUnit", "android:bind_text_textSize"], requireAll = false)
    fun onBindTextSize(view: TextView, unit: Int, size: Int) {
        var unit = unit
        if (unit < 0) {
            unit = TypedValue.COMPLEX_UNIT_SP
        }
        view.setTextSize(unit, size.toFloat())
    }

    /**
     * xml绑定设置TextView text Style
     *
     * @param view
     * @param style see  [android.graphics.Typeface.NORMAL]
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter("android:bind_text_style")
    fun onBindTextStyle(view: TextView, style: Int) {
        if (style <= 0) {
            return
        }
        view.setTypeface(view.typeface, style)
    }

    /**
     * xml绑定设置EditText密码可见状态
     *
     * @param editText
     * @param isVisible
     */
    @BindingAdapter(value = ["android:bind_isPswVisible"])
    fun onBindTextPasswordVisible(editText: EditText, isVisible: Boolean) {
        val selectionEnd = editText.selectionEnd
        editText.inputType = InputType.TYPE_CLASS_TEXT or if (isVisible) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD else InputType.TYPE_TEXT_VARIATION_PASSWORD
        editText.setSelection(selectionEnd)
    }

    /**
     * xml绑定设置EditText背景可见状态
     *
     * @param editText
     * @param isVisible
     */
    @BindingAdapter(value = ["android:bind_edit_text_bg_visible"])
    fun onBindEditTextBackground(editText: EditText, isVisible: Boolean) {
        if (!isVisible) {
            editText.setBackgroundResource(R.color.transparent)
            return
        }
        var transparentDrawable: Drawable? = ColorDrawable(Color.TRANSPARENT)
        if (editText.background == transparentDrawable) {
            transparentDrawable = null
            return
        }
        var editText1: EditText? = EditText(editText.context)
        var drawable = editText1!!.background
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            editText.background = drawable
        } else {
            editText.setBackgroundDrawable(drawable)
        }
        transparentDrawable = null
        drawable = null
        editText1 = null
    }
}