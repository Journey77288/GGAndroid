package io.ganguo.library.ui.bindingadapter.textview;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.databinding.adapters.ViewBindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.InputType;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.TextView;

import io.ganguo.library.BaseApp;
import io.ganguo.library.R;

import static android.os.Build.*;

/**
 * function：TextView DataBinding绑定工具类
 * update by leo on 2018/05/29.
 */
public final class BindingTextAdapter extends ViewBindingAdapter {
    /**
     * function:xml绑定设置TextView DrawableLeft
     *
     * @param view
     * @param id   图片资源Id
     */
    @BindingAdapter("android:bind_text_drawableLeft")
    public static void onBindTextDrawableLeft(TextView view, @DrawableRes int id) {
        view.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);

    }

    /**
     * function:xml绑定设置TextView DrawableRight
     *
     * @param view
     * @param id   图片资源Id
     */
    @BindingAdapter("android:bind_text_drawableRight")
    public static void onBindTextDrawableRight(TextView view, @DrawableRes int id) {
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0);
    }


    /**
     * function:xml绑定设置TextView Html文本
     *
     * @param view
     * @param text html文本
     */
    @BindingAdapter("android:bind_text")
    public static void onBindStringToTextView(TextView view, CharSequence text) {
        view.setText(text);
    }

    /**
     * function:xml绑定设置TextView Html文本
     *
     * @param view
     * @param text html文本
     */
    @BindingAdapter("android:bind_text_html")
    public static void onBindHtmlToTextView(TextView view, String text) {
        view.setText(Html.fromHtml(text));
    }


    /**
     * function:xml绑定设置TextView字体颜色
     *
     * @param view
     * @param colorRes 颜色资源Id
     */
    @SuppressLint("ResourceType")
    @BindingAdapter("android:bind_text_colorRes")
    public static void onBindTextColorRes(TextView view, @ColorRes int colorRes) {
        if (colorRes < 0) {
            return;
        }
        view.setTextColor(ContextCompat.getColorStateList(BaseApp.me(), colorRes));
    }

    /**
     * function:xml绑定设置TextView字体颜色
     *
     * @param view
     * @param color
     */
    @SuppressLint("ResourceType")
    @BindingAdapter("android:bind_text_color")
    public static void onBindTextColor(TextView view, @ColorInt int color) {
        view.setTextColor(color);
    }


    /**
     * function:xml绑定设置TextView 选中颜色
     *
     * @param view
     * @param textSelectColor 颜色资源Id
     */
    @SuppressLint("ResourceType")
    @BindingAdapter("android:bind_text_select_colorRes")
    public static void onBindTextSelectColor(TextView view, @ColorRes int textSelectColor) {
        if (textSelectColor <= 0) {
            return;
        }
        view.setTextColor(ContextCompat.getColorStateList(BaseApp.me(), textSelectColor));
    }

    /**
     * function:xml绑定设置TextView 长度
     *
     * @param textView
     * @param maxLength 文本字符长度
     */
    @BindingAdapter(value = {"android:bind_text_maxLength"})
    public static void onBindMaxLength(TextView textView, int maxLength) {
        if (maxLength == -1) {
            return;
        }
        if (maxLength >= textView.getText().length()) {
            return;
        }
        textView.setText(textView.getText().subSequence(0, maxLength));
    }


    /**
     * function:xml绑定设置TextView 字体大小
     *
     * @param view TextView
     * @param unit size单位 see{@link TypedValue}
     * @param size 字体大小sp
     */
    @BindingAdapter(value = {"android:bind_text_sizeUnit", "android:bind_text_textSize"}, requireAll = false)
    public static void onBindTextSize(TextView view, int unit, int size) {
        if (unit < 0) {
            unit = TypedValue.COMPLEX_UNIT_SP;
            view.setTextSize(unit, (float) size);
        }
    }


    /**
     * function:xml绑定设置TextView text Style
     *
     * @param view
     * @param style see  {@link android.graphics.Typeface#NORMAL}
     */
    @BindingAdapter("android:bind_textStyle")
    public static void onBindTextStyle(TextView view, int style) {
        if (style <= 0) {
            return;
        }
        view.setTypeface(view.getTypeface(), style);
    }

    /**
     * function:xml绑定设置EditText密码可见状态
     *
     * @param editText
     * @param isVisible
     */
    @BindingAdapter(value = {"android:bind_isPswVisible"})
    public static void onBindTextPasswordVisible(EditText editText, boolean isVisible) {
        int selectionEnd = editText.getSelectionEnd();
        editText.setInputType(InputType.TYPE_CLASS_TEXT | (isVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        editText.setSelection(selectionEnd);
    }


    /**
     * function:xml绑定设置EditText背景可见状态
     *
     * @param editText
     * @param isVisible
     */
    @BindingAdapter(value = {"android:bind_edit_text_bg_visible"})
    public static void onBindEditTextBackground(EditText editText, boolean isVisible) {
        if (!isVisible) {
            editText.setBackgroundResource(R.color.transparent);
            return;
        }

        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        if (editText.getBackground().equals(transparentDrawable)) {
            transparentDrawable = null;
            return;
        }
        EditText editText1 = new EditText(editText.getContext());
        Drawable drawable = editText1.getBackground();
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(drawable);
        } else {
            editText.setBackgroundDrawable(drawable);
        }
        transparentDrawable = null;
        drawable = null;
        editText1 = null;
    }


}
