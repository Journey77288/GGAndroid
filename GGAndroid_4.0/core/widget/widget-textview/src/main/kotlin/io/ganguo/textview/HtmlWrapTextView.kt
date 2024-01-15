package io.ganguo.textview

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
@Deprecated("It is not recommended to use TextView to load HTML text directly. There are many styling issues")
class HtmlWrapTextView : HtmlTextView {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)
}
