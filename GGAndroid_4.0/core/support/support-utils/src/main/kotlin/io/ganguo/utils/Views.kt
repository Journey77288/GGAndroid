package io.ganguo.utils

import android.graphics.Paint
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlin.math.ceil

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/01/05
 *     desc   : View处理工具
 * </pre>
 */


/**
 * Show view
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Make view gone
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Hide view
 */
fun View.hide() {
    visibility = View.INVISIBLE
}

/**
 * Set TextView max length
 *
 * @param length Int
 */
fun TextView.setMaxLength(length: Int) {
    val filters = arrayOf<InputFilter>()
    filters[0] = InputFilter.LengthFilter(length)
    setFilters(filters)
}

/**
 * Get the real font height of TextView
 *
 * @return Int
 */
fun TextView.getFontHeight(): Int {
    val paint = Paint()
    paint.textSize = this.textSize
    val fm = paint.fontMetrics
    return ceil(fm.descent - fm.ascent).toInt()
}

/**
 * Selection to end of EditText
 */
fun EditText.setSelectionEnd() {
    val text = this.text.toString()
    val length = if (text.isBlank()) 0 else text.length
    this.setSelection(length)
}

/**
 * Measure view size manually
 * 对View进行主动Measure，Measure后获取到measuredWidth、measuredHeight不为0
 *
 * @return Pair<Int, Int> 宽高尺寸数据
 */
fun View.getMeasureSize(): Pair<Int, Int> {
    val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    this.measure(w, h)
    return this.measuredWidth to this.measuredHeight
}