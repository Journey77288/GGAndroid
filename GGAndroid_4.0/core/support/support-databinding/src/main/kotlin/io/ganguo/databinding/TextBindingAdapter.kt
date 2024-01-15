@file:JvmName("TextBindingAdapter")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/28
 *     desc   : TextView DataBinding Adapter
 * </pre>
 */

package io.ganguo.databinding

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.TypedValue
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

/**
 * Bind the drawable object to the textView
 * @receiver TextView
 * @param drawableLeft Drawable?
 * @param drawableTop Drawable?
 * @param drawableRight Drawable?
 * @param drawableBottom Drawable?
 */
@BindingAdapter(
	  value = [
		  "android:bind_drawable_left_to_text_view",
		  "android:bind_drawable_top_to_text_view",
		  "android:bind_drawable_right_to_text_view",
		  "android:bind_drawable_bottom_to_text_view"],
	  requireAll = false
)
fun TextView.bindTextDrawable(
	  drawableLeft: Drawable?,
	  drawableTop: Drawable?,
	  drawableRight: Drawable?,
	  drawableBottom: Drawable?
) {
	setCompoundDrawablesWithIntrinsicBounds(
		  drawableLeft,
		  drawableTop,
		  drawableRight,
		  drawableBottom
	)
}


/**
 * Bind the drawableResId to the top of the text
 * @receiver TextView
 * @param drawableLeftResId Int
 * @param drawableTopResId Int
 * @param drawableRightResId Int
 * @param drawableBottomResId Int
 */
@BindingAdapter(
	  value = ["android:bind_drawable_left_res_to_text_view",
		  "android:bind_drawable_top_res_to_text_view",
		  "android:bind_drawable_right_res_to_text_view",
		  "android:bind_drawable_bottom_res_to_text_view"],
	  requireAll = false
)
fun TextView.bindTextDrawableResource(
	  @DrawableRes drawableLeftResId: Int = 0,
	  @DrawableRes drawableTopResId: Int = 0,
	  @DrawableRes drawableRightResId: Int = 0,
	  @DrawableRes drawableBottomResId: Int = 0
) {
	setCompoundDrawablesWithIntrinsicBounds(
		  drawableLeftResId,
		  drawableTopResId,
		  drawableRightResId,
		  drawableBottomResId
	)
}


/**
 * Bind the HTML string to TextView
 * @receiver TextView
 * @param htmlStr String
 */
@BindingAdapter("android:bind_html_string_to_text_view")
fun TextView.bindTextHtmlString(htmlStr: String?) {
	HtmlCompat
		  .fromHtml(htmlStr.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
		  .let {
			  text = it
		  }
}


/**
 * Bind the color resource id to TextView
 * @receiver TextView
 * @param colorResId Int
 */
@BindingAdapter("android:bind_color_res_to_text_view")
fun TextView.bindTextColorResource(@ColorRes colorResId: Int = 0) {
	ResourcesCompat
		  .getColorStateList(resources, colorResId, null)
		  .let {
			  setTextColor(it)
		  }
}


/**
 * Bind the color resource id to TextView
 * @receiver TextView
 * @param colorStateList ColorStateList
 */
@BindingAdapter("android:bind_color_state_list_to_text_view")
fun TextView.bindTextColorStateList(colorStateList: ColorStateList?) {
	colorStateList?.let {
		setTextColor(it)
	}
}

/**
 * Bind the color resource to TextView
 * @receiver TextView
 * @param color Int
 */
@BindingAdapter("android:bind_color_int_to_text_view")
fun TextView.bindTextColorInt(color: Int = 0) {
	setTextColor(color)
}

/**
 * Bind font size ResourceId up to TextView
 * @receiver TextView
 * @param textSizeUnit Int?
 * @param textSizeRes Int
 */
@BindingAdapter(value = ["android:bind_text_size_unit_to_text_view", "android:bind_text_size_res_to_text_view"])
fun TextView.bindTextSizeResource(textSizeUnit: Int?, @DimenRes textSizeRes: Int = 0) {
	if (textSizeRes == 0) {
		return
	}
	var unit = textSizeUnit
	if (unit == null || unit < 0) {
		unit = TypedValue.COMPLEX_UNIT_SP
	}
	setTextSize(unit, resources.getDimension(textSizeRes))
}


/**
 * Bind the string resource to TextView
 * @receiver TextView
 * @param strRes Int
 */
@BindingAdapter("android:bind_string_res_to_text_view")
fun TextView.bindTextStringResource(@StringRes strRes: Int) {
	resources.getString(strRes).let {
		text = it
	}
}


/**
 * Bind the string to TextView
 * @receiver TextView
 * @param str String
 */
@BindingAdapter("android:bind_string_to_text_view")
fun TextView.bindTextString(str: String?) {
	str?.let {
		text = it
	}
}


/**
 * Bind font size up to TextView
 * @receiver TextView
 * @param textSizeUnit Int?
 * @param textSize Float
 */
@BindingAdapter(value = ["android:bind_text_size_unit_to_text_view", "android:bind_text_size_to_text_view"])
fun TextView.bindTextSize(textSizeUnit: Int?, textSize: Float = 0.0f) {
	var unit = textSizeUnit
	if (unit == null || unit < 0) {
		unit = TypedValue.COMPLEX_UNIT_SP
	}
	setTextSize(unit, textSize)
}


/**
 * Set the [TextView] font style
 * @receiver TextViewextView?
 * @param toTypeface Int?
 */
@BindingAdapter(value = ["android:bind_typeface_to_text_view"])
fun TextView.bindTextTypeface(toTypeface: Int?) {
	if (toTypeface == null || toTypeface < 0) {
		return
	}
    if (toTypeface == Typeface.NORMAL) {
        setTypeface(null, toTypeface)
    } else {
        setTypeface(typeface, toTypeface)
    }
}


/**
 * set the password to display the status through dataBinding
 * @receiver TextViewextView
 * @param isVisible Boolean
 */
@BindingAdapter(value = ["android:bind_password_is_visible_to_text_view"])
fun TextView.bindTextPasswordVisible(isVisible: Boolean) {
	inputType = if (isVisible) {
		InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
	} else {
		InputType.TYPE_TEXT_VARIATION_PASSWORD
	}.let {
		it or InputType.TYPE_CLASS_TEXT
	}
	if (this is EditText) {
		setSelection(selectionEnd)
	}
}








