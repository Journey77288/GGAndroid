package io.ganguo.switchs

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt


/**
 * <pre>
 * author : leo
 * time   : 2019/12/03
 * desc   : iOS风格Switch Button
 * <pre>
 *
 * @property iosRightCircleShow Boolean 是否显示右侧小圆
 * @property iosLeftLineShow Boolean 是否显示右侧竖线
 * @property iosLeftLineColor Int 左边竖线颜色
 * @property iosLeftLineHeight Int 左边竖线高度
 * @property iosLeftLineWidth Int 左边竖线宽度
 * @property iosLeftLineMarginLeft Int 左边竖线左边距
 * @property iosRightCircleColor Int 右侧小圆颜色
 * @property iosRightCircleRadius Int 右侧小圆半径
 * @property iosRightCircleWidth Int 右侧小圆线条宽度
 * @property iosRightCircleMarginRight Int 右侧小圆右边距
 * @property linePath Path 竖线绘制路径
 * @property circlePath Path 小圆绘制路径
 * @property linePaint Paint 竖线画笔
 * @property circlePaint Paint 小圆画笔
 */

open class SwitchIOSButton : SwitchButton {
    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        initIOSAttributes(attrs)
        initIOSPaint()
    }

    private var iosRightCircleShow: Boolean = false
    private var iosLeftLineShow: Boolean = false


    @ColorInt
    private var iosLeftLineColor: Int = Color.WHITE
    private var iosLeftLineHeight: Int = 0
    private var iosLeftLineWidth: Int = 2
    private var iosLeftLineMarginLeft: Int = 0

    @ColorInt
    private var iosRightCircleColor: Int = Color.LTGRAY
    private var iosRightCircleRadius: Int = 0
    private var iosRightCircleWidth: Int = 0
    private var iosRightCircleMarginRight: Int = iosLeftLineMarginLeft - iosRightCircleRadius

    private var linePath: Path = Path()
    private var circlePath: Path = Path()
    private var linePaint: Paint = Paint()
    private var circlePaint: Paint = Paint()


    /**
     * 初始化IOS样式自定义属性
     * @param attrs AttributeSet
     */
    private fun initIOSAttributes(attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton)
        iosLeftLineColor =
                array.getColor(R.styleable.SwitchButton_iosLeftLineColor, Color.WHITE)
        iosLeftLineHeight = array.getDimensionPixelOffset(
                R.styleable.SwitchButton_iosLeftLineHeight,
                (trackHeight * 0.4f).toInt()
        )
        iosLeftLineWidth = array.getDimensionPixelOffset(
                R.styleable.SwitchButton_iosLeftLineWidth,
                2
        )
        iosLeftLineMarginLeft = array.getDimensionPixelOffset(
                R.styleable.SwitchButton_iosLeftLineMarginLeft,
                (trackWidth * 0.2f).toInt()
        )
        iosLeftLineShow = array.getBoolean(
                R.styleable.SwitchButton_iosLeftLineShow,
                false)



        iosRightCircleColor =
                array.getColor(R.styleable.SwitchButton_iosRightCircleColor, Color.DKGRAY)
        iosRightCircleRadius = array.getDimensionPixelOffset(
                R.styleable.SwitchButton_iosRightCircleRadius,
                (thumbRadius * 0.2f).toInt()
        )
        iosRightCircleWidth = array.getDimensionPixelOffset(
                R.styleable.SwitchButton_iosRightCircleWidth,
                2
        )
        iosRightCircleMarginRight = array.getDimensionPixelOffset(
                R.styleable.SwitchButton_iosRightCircleMarginRight,
                iosLeftLineMarginLeft
        )
        iosRightCircleShow = array.getBoolean(
                R.styleable.SwitchButton_iosLeftLineShow,
                false)
        array.recycle()
    }


    /**
     * 初始化画笔
     */
    private fun initIOSPaint() {
        linePaint.style = Paint.Style.FILL
        linePaint.strokeJoin = Paint.Join.ROUND
        linePaint.strokeCap = Paint.Cap.ROUND
        linePaint.color = iosLeftLineColor
        linePaint.isAntiAlias = true
        linePaint.isDither = true

        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeJoin = Paint.Join.ROUND
        circlePaint.strokeCap = Paint.Cap.ROUND
        circlePaint.color = iosRightCircleColor
        circlePaint.strokeWidth = iosRightCircleWidth.toFloat()
        circlePaint.isAntiAlias = true
        circlePaint.isDither = true
    }


    override fun onDrawToggleTrack(canvas: Canvas) {
        super.onDrawToggleTrack(canvas)
        if (iosLeftLineShow) {
            onDrawLeftLine(canvas)
        }
        if (iosRightCircleShow) {
            onDrawRightCircle(canvas)
        }

    }


    /**
     * 绘制左边竖线
     */
    private fun onDrawLeftLine(canvas: Canvas) {
        linePath.reset()
        var lineRectF = RectF()
        lineRectF.left = iosLeftLineMarginLeft.toFloat()
        lineRectF.right = lineRectF.left + iosLeftLineWidth
        lineRectF.top = (height - iosLeftLineHeight) * 0.5f
        lineRectF.bottom = lineRectF.top + iosLeftLineHeight
        linePath.addRect(lineRectF, Path.Direction.CW)
        canvas.save()
        canvas.drawPath(linePath, linePaint)
        canvas.restore()
    }

    /**
     * 绘制右侧小圆圈
     */
    private fun onDrawRightCircle(canvas: Canvas) {
        circlePath.reset()
        var centerX = width - iosRightCircleMarginRight - iosRightCircleRadius
        var centerY = height * 0.5f
        circlePath.addCircle(
                centerX.toFloat(),
                centerY,
                iosRightCircleRadius.toFloat(),
                Path.Direction.CW
        )
        canvas.save()
        canvas.drawPath(circlePath, circlePaint)
        canvas.restore()
    }


}