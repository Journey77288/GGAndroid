package io.ganguo.scanner.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.annotation.ColorInt
import androidx.lifecycle.*
import io.ganguo.scanner.R


/**
 * <pre>
 * author : leo
 * time   : 2019/12/03
 * desc   : ScanningLineView
 *          Used to handle up and down scan animation
 * <pre>
 *
 * @property scanningLinePaint  画笔
 * @property gradientShader  线性渐变着色器
 * @property scanningSpeed 扫描线，上下扫动速度
 * @property scanningAnimation 扫描动画类型
 * @property scanningRange 扫描角度
 * @property scanningLineColor 扫描线颜色
 */
open class ScanLineView : View, DefaultLifecycleObserver {
    var scanningRange: Int = 0
        set(value) {
            field = value
            invalidate()
        }
    var scanningSpeed: Long = 3000L
        set(value) {
            scanningAnimation.duration = scanningSpeed
            field = value
        }

    @ColorInt
    var scanningLineColor = Color.RED
        set(value) {
            field = value
            gradientShader = null
            invalidate()
        }
    private lateinit var scanningLinePaint: Paint
    private var gradientShader: Shader? = null
    private val scanningAnimation: Animation by lazy {
        createScanningAnimation()
    }
    private var lifecycle: Lifecycle? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        obtainStyled(context, attrs)
        initPaint()
    }


    /**
     * 初始化画笔属性
     */
    private fun initPaint() {
        scanningLinePaint = Paint()
        scanningLinePaint.style = Paint.Style.FILL
        scanningLinePaint.strokeWidth = 10f
        scanningLinePaint.isAntiAlias = true
    }


    /**
     * 创建位移动画
     * @return Animation
     */
    private fun createScanningAnimation(): Animation {
        return TranslateAnimation(
                Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, scanningRange.toFloat())
                .apply {
                    duration = scanningSpeed
                    repeatMode = Animation.RESTART
                    repeatCount = Animation.INFINITE
                }
    }

    /**
     * 读取xml自定义属性
     * @param context Context
     * @param attrs AttributeSet?
     */
    private fun obtainStyled(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.scan_line_view)
        scanningLineColor = a.getColor(R.styleable.scan_line_view_scan_line_color, Color.GREEN)
        scanningRange = a.getDimensionPixelOffset(R.styleable.scan_line_view_scan_line_range, 200)
        a.recycle()
    }


    /**
     * 绘制扫描线
     * @param canvas Canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (gradientShader == null) {
            gradientShader = createPaintShader(scanningLineColor, width.toFloat())
            scanningLinePaint.strokeWidth = height.toFloat()
            scanningLinePaint.shader = gradientShader
        }
        canvas.drawLine(0f, 0f, measuredWidth.toFloat(), 0f, scanningLinePaint)
    }


    /**
     * 设置画笔着色器
     * @param color Int
     * @param width Int
     */
    private fun createPaintShader(@ColorInt color: Int, width: Float): LinearGradient {
        return LinearGradient(
                0f, 0f,
                width, 0f,
                interceptionColor(color), null,
                Shader.TileMode.CLAMP)
    }


    /**
     * 截取渐变色
     * @return IntArray
     */
    private fun interceptionColor(@ColorInt color: Int): IntArray {
        return Integer.toHexString(color)
                .run {
                    substring(length - 6, length - 0)
                }
                .let {
                    intArrayOf(Color.parseColor("#00$it"),
                            color, Color.parseColor("#00$it"))
                }
    }


    /**
     * Sets the lifecycle owner for this view. This means you don't need
     * to call [.open], [.close] or [.destroy] at all.
     *
     * @param owner the owner activity or fragment
     */
    open fun setLifecycleOwner(owner: LifecycleOwner) {
        if (lifecycle != null) {
            lifecycle?.removeObserver(this)
        }
        lifecycle = owner.lifecycle
        lifecycle?.addObserver(this)
    }

    /**
     * 暂停扫描动画
     */
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        clearAnimation()
    }

    /**
     * 启动扫描动画
     */
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        startAnimation(scanningAnimation)
    }

}