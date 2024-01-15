package io.ganguo.appcompat.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout
import io.ganguo.appcompat.R

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/12
 *     desc   : 支持圆角的FrameLayout
 * </pre>
 */
class CornerFrameLayout : FrameLayout {
    private var cornersRadius: Float = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributeSet(attrs)
    }


    /**
     * 自定义属性初始化
     * @param attrs AttributeSet
     */
    private fun initAttributeSet(attrs: AttributeSet?) {
        var array = context.obtainStyledAttributes(attrs, R.styleable.CornerFrameLayout)
        cornersRadius = array.getDimension(R.styleable.CornerFrameLayout_cornersRadius, 0f)
        array.recycle()
    }


    /**
     * 重写dispatchDraw函数，裁切圆角
     * @param canvas Canvas
     */
    override fun dispatchDraw(canvas: Canvas?) {
        var path = Path()
        var rectF = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
        path.addRoundRect(rectF, cornersRadius, cornersRadius, Path.Direction.CW)
        canvas?.clipPath(path)
        super.dispatchDraw(canvas)
    }
}