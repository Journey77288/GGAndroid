/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ganguo.picker.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import io.ganguo.picker.R

class CheckView : View {
    private var mCountable = false
    private var mChecked = false
    private var mCheckedNum = 0
    private var mStrokePaint: Paint? = null
    private var mBackgroundPaint: Paint? = null
    private var mTextPaint: TextPaint? = null
    private var mShadowPaint: Paint? = null
    private var mCheckDrawable: Drawable? = null
    private var mDensity = 0f
    private var mCheckRect: Rect? = null
    private var mEnabled = true
	private var mSize = 48f

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) { // fixed size 48dp x 48dp
        val sizeSpec = MeasureSpec.makeMeasureSpec((mSize * mDensity).toInt(), MeasureSpec.EXACTLY)
        super.onMeasure(sizeSpec, sizeSpec)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mDensity = context.resources.displayMetrics.density
        mStrokePaint = Paint()
        mStrokePaint!!.isAntiAlias = true
        mStrokePaint!!.style = Paint.Style.STROKE
        mStrokePaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        mStrokePaint!!.strokeWidth = STROKE_WIDTH * mDensity
        val ta = getContext().theme.obtainStyledAttributes(intArrayOf(R.attr.item_checkCircle_borderColor))
        val defaultColor = ResourcesCompat.getColor(
                resources, R.color.item_checkCircle_borderColor,
                getContext().theme)
        val color = ta.getColor(0, defaultColor)
	    attrs?.let {
		    val a = context.obtainStyledAttributes(attrs, R.styleable.CheckView)
		    mSize = a.getFloat(R.styleable.CheckView_circle_size, 48f)
		    a.recycle()
	    }
        ta.recycle()
        mStrokePaint!!.color = color
        mCheckDrawable = ResourcesCompat.getDrawable(context.resources,
                R.drawable.ic_check_white_18dp, context.theme)
    }

    fun setChecked(checked: Boolean) {
        check(!mCountable) { "CheckView is countable, call setCheckedNum() instead." }
        mChecked = checked
        invalidate()
    }

    fun setCountable(countable: Boolean) {
        mCountable = countable
    }

    fun setCheckedNum(checkedNum: Int) {
        check(mCountable) { "CheckView is not countable, call setChecked() instead." }
        require(!(checkedNum != UNCHECKED && checkedNum <= 0)) { "checked num can't be negative." }
        mCheckedNum = checkedNum
        invalidate()
    }

    override fun setEnabled(enabled: Boolean) {
        if (mEnabled != enabled) {
            mEnabled = enabled
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // draw outer and inner shadow
        initShadowPaint()
        canvas.drawCircle(mSize * mDensity / 2, mSize * mDensity / 2,
                (STROKE_RADIUS + STROKE_WIDTH / 2 + SHADOW_WIDTH) * mDensity, mShadowPaint!!)
        // draw white stroke
        canvas.drawCircle(mSize * mDensity / 2, mSize * mDensity / 2,
                STROKE_RADIUS * mDensity, mStrokePaint!!)
        // draw content
        if (mCountable) {
            if (mCheckedNum != UNCHECKED) {
                initBackgroundPaint()
                canvas.drawCircle(mSize * mDensity / 2, mSize * mDensity / 2,
                        BG_RADIUS * mDensity, mBackgroundPaint!!)
                initTextPaint()
                val text = mCheckedNum.toString()
                val baseX = (canvas.width - mTextPaint!!.measureText(text)).toInt() / 2
                val baseY = (canvas.height - mTextPaint!!.descent() - mTextPaint!!.ascent()).toInt() / 2
                canvas.drawText(text, baseX.toFloat(), baseY.toFloat(), mTextPaint!!)
            }
        } else {
            if (mChecked) {
                initBackgroundPaint()
                canvas.drawCircle(mSize * mDensity / 2, mSize * mDensity / 2,
                        BG_RADIUS * mDensity, mBackgroundPaint!!)
                mCheckDrawable!!.bounds = checkRect
                mCheckDrawable!!.draw(canvas)
            }
        }
        // enable hint
        alpha = if (mEnabled) 1.0f else 0.5f
    }

    private fun initShadowPaint() {
        if (mShadowPaint == null) {
            mShadowPaint = Paint()
            mShadowPaint!!.isAntiAlias = true
            // all in dp
            val outerRadius = STROKE_RADIUS + STROKE_WIDTH / 2
            val innerRadius = outerRadius - STROKE_WIDTH
            val gradientRadius = outerRadius + SHADOW_WIDTH
            val stop0 = (innerRadius - SHADOW_WIDTH) / gradientRadius
            val stop1 = innerRadius / gradientRadius
            val stop2 = outerRadius / gradientRadius
            val stop3 = 1.0f
            mShadowPaint!!.shader = RadialGradient(mSize * mDensity / 2,
                    mSize * mDensity / 2,
                    gradientRadius * mDensity, intArrayOf(Color.parseColor("#00000000"), Color.parseColor("#0D000000"),
                    Color.parseColor("#0D000000"), Color.parseColor("#00000000")), floatArrayOf(stop0, stop1, stop2, stop3),
                    Shader.TileMode.CLAMP)
        }
    }

    private fun initBackgroundPaint() {
        if (mBackgroundPaint == null) {
            mBackgroundPaint = Paint()
            mBackgroundPaint!!.isAntiAlias = true
            mBackgroundPaint!!.style = Paint.Style.FILL
            val ta = context.theme
                    .obtainStyledAttributes(intArrayOf(R.attr.item_checkCircle_backgroundColor))
            val defaultColor = ResourcesCompat.getColor(
                    resources, R.color.item_checkCircle_backgroundColor,
                    context.theme)
            val color = ta.getColor(0, defaultColor)
            ta.recycle()
            mBackgroundPaint!!.color = color
        }
    }

    private fun initTextPaint() {
        if (mTextPaint == null) {
            mTextPaint = TextPaint()
            mTextPaint!!.isAntiAlias = true
            mTextPaint!!.color = Color.WHITE
            mTextPaint!!.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            mTextPaint!!.textSize = 12.0f * mDensity
        }
    }

    // rect for drawing checked number or mark
    private val checkRect: Rect
        private get() {
            if (mCheckRect == null) {
                val rectPadding = (mSize * mDensity / 2 - CONTENT_SIZE * mDensity / 2).toInt()
                mCheckRect = Rect(rectPadding, rectPadding,
                        (mSize * mDensity - rectPadding).toInt(), (mSize * mDensity - rectPadding).toInt())
            }
            return mCheckRect!!
        }

    companion object {
        const val UNCHECKED = Int.MIN_VALUE
        private const val STROKE_WIDTH = 3.0f // dp
        private const val SHADOW_WIDTH = 6.0f // dp
        private const val STROKE_RADIUS = 11.5f // dp
        private const val BG_RADIUS = 11.0f // dp
        private const val CONTENT_SIZE = 18 // dp
    }
}