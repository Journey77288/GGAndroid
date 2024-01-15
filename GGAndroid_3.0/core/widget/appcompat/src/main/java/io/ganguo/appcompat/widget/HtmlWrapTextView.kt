package io.ganguo.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import org.sufficientlysecure.htmltextview.HtmlTextView

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/18
 *     desc   : 支持Html富文本的TextView
 * </pre>
 */
class HtmlWrapTextView : HtmlTextView {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)
}