/*
 * Copyright (C) 2015 Lyft, Inc.
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
package io.ganguo.scissor.view.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import io.ganguo.scissor.bean.CropViewConfig
import io.ganguo.scissor.core.*
import io.ganguo.scissor.utils.Utils

/**
 * An [ImageView] with a fixed viewport and cropping capabilities.
 */
class CropView : androidx.appcompat.widget.AppCompatImageView {
    private var touchManager: TouchManager? = null
    private var config: CropViewConfig? = null
    private val viewportPaint = Paint()
    private val strokePaint = Paint()
    private val bitmapPaint = Paint()
    private var bitmap: Bitmap? = null

    /**
     * Get the transform matrix
     */
    val transformMatrix = Matrix()
    private var extensions: Extensions? = null

    /**
     * Corresponds to the values in [R.attr.cropviewShape]
     */
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(Shape.RECTANGLE, Shape.OVAL)
    annotation class Shape {
        companion object {
            const val RECTANGLE = 0
            const val OVAL = 1
        }
    }

    @Shape
    private var shape = Shape.RECTANGLE
    private var maskPath: Path? = null
    private var rectangleClipPath: Path? = null
    private var rectangleClipRect: RectF? = null
    private var ovalClipPath: Path? = null
    private var ovalClipRect: RectF? = null

    constructor(context: Context) : super(context) {
        initCropView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initCropView(context, attrs)
    }

    fun initCropView(context: Context, attrs: AttributeSet?) {
        config = CropViewConfig.from(context, attrs)

        touchManager = TouchManager(this, config!!)

        bitmapPaint.isFilterBitmap = true
        setViewportOverlayColor(config!!.viewportOverlayColor)
        shape = config!!.shape() ?: CropView.Shape.RECTANGLE

        // We need anti-aliased Paint to smooth the curved edges
        viewportPaint.flags = viewportPaint.flags or Paint.ANTI_ALIAS_FLAG

        // stroke need anti-aliased Paint to draw line
        strokePaint.apply {
            flags = strokePaint.flags or Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.STROKE
            color = config!!.strokeColor
            strokeWidth = config!!.strokeWidth
            pathEffect = DashPathEffect(floatArrayOf(config!!.strokeInterval, config!!.strokeInterval), 0f)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (bitmap == null) {
            return
        }
        drawBitmap(canvas)
        if (shape == Shape.RECTANGLE) {
            drawSquareOverlay(canvas)
        } else {
            drawOvalOverlay(canvas)
        }
    }

    private fun drawBitmap(canvas: Canvas) {
        transformMatrix.reset()
        touchManager?.applyPositioningAndScale(transformMatrix)
        canvas.drawBitmap(bitmap!!, transformMatrix, bitmapPaint)
    }

    private fun drawSquareOverlay(canvas: Canvas) {
        val viewportWidth: Int = touchManager?.viewportWidth ?: 0
        val viewportHeight: Int = touchManager?.viewportHeight ?: 0
        val viewportCornerRadius: Int = touchManager?.viewportCornerRadius?: 0
        val left = (width - viewportWidth) / 2
        val top = (height - viewportHeight) / 2
        if (maskPath == null) {
            maskPath = Path()
        }
        maskPath!!.reset()
        maskPath!!.addRect(0f, 0f, width.toFloat(), height.toFloat(), Path.Direction.CW)
        if (rectangleClipPath == null) {
            rectangleClipPath = Path()
        }
        if (rectangleClipRect == null) {
            rectangleClipRect = RectF()
        }
        rectangleClipRect!!.let {
            it.left = left.toFloat()
            it.top = top.toFloat()
            it.right = (width - left).toFloat()
            it.bottom = (height - top).toFloat()
        }
        rectangleClipPath!!.reset()
        rectangleClipPath!!.addRoundRect(rectangleClipRect!!, viewportCornerRadius.toFloat(), viewportCornerRadius.toFloat(), Path.Direction.CW)
        rectangleClipPath!!.close()
        maskPath!!.op(rectangleClipPath!!, Path.Op.DIFFERENCE)
        canvas.drawPath(maskPath!!, viewportPaint)
        canvas.drawPath(rectangleClipPath!!, strokePaint)
    }

    private fun drawOvalOverlay(canvas: Canvas) {
        if (maskPath == null) {
            maskPath = Path()
        }
        maskPath!!.reset()
        maskPath!!.addRect(0f, 0f, width.toFloat(), height.toFloat(), Path.Direction.CW)
        if (ovalClipRect == null) {
            ovalClipRect = RectF()
        }
        if (ovalClipPath == null) {
            ovalClipPath = Path()
        }
        val viewportWidth: Int = touchManager?.viewportWidth ?: 0
        val viewportHeight: Int = touchManager?.viewportHeight ?: 0
        val left = (width - viewportWidth) / 2
        val top = (height - viewportHeight) / 2
        val right = width - left
        val bottom = height - top
        ovalClipRect!!.left = left.toFloat()
        ovalClipRect!!.top = top.toFloat()
        ovalClipRect!!.right = right.toFloat()
        ovalClipRect!!.bottom = bottom.toFloat()

        ovalClipPath!!.reset()
        ovalClipPath!!.addOval(ovalClipRect!!, Path.Direction.CW)
        ovalClipPath!!.close()
        maskPath!!.op(ovalClipPath!!, Path.Op.DIFFERENCE)
        canvas.drawPath(maskPath!!, viewportPaint)
        canvas.drawPath(ovalClipPath!!, strokePaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        resetTouchManager()
    }

    /**
     * Sets the color of the viewport overlay
     *
     * @param viewportOverlayColor The color to use for the viewport overlay
     */
    fun setViewportOverlayColor(@ColorInt viewportOverlayColor: Int) {
        viewportPaint.color = viewportOverlayColor
        config?.viewportOverlayColor = viewportOverlayColor
    }

    /**
     * Sets the padding for the viewport overlay
     *
     * @param viewportOverlayPadding The new padding of the viewport overlay
     */
    fun setViewportOverlayPadding(viewportOverlayPadding: Int) {
        config?.viewportOverlayPadding = viewportOverlayPadding
        resetTouchManager()
        invalidate()
    }

    /**
     * Returns the native aspect ratio of the image.
     *
     * @return The native aspect ratio of the image.
     */
    val imageRatio: Float
        get() {
            val bitmap: Bitmap? = getImageBitmap()
            return if (bitmap != null) bitmap.width.toFloat() / bitmap.height.toFloat() else 0f
        }
    /**
     * Returns the aspect ratio of the viewport and crop rect.
     *
     * @return The current viewport aspect ratio.
     */
    /**
     * Sets the aspect ratio of the viewport and crop rect.  Defaults to
     * the native aspect ratio if `ratio == 0`.
     *
     * @param ratio The new aspect ratio of the viewport.
     */
    var viewportRatio: Float
        get() = touchManager?.getAspectRatio() ?: 0f
        set(ratio) {
            var ratio = ratio
            if (ratio.compareTo(0f) == 0) {
                ratio = imageRatio
            }
            touchManager?.setAspectRatio(ratio)
            resetTouchManager()
            invalidate()
        }

    @SuppressLint("ResourceType")
    override fun setImageResource(@DrawableRes resId: Int) {
        val bitmap: Bitmap? = if (resId > 0) BitmapFactory.decodeResource(resources, resId) else null
        setImageBitmap(bitmap)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        val bitmap: Bitmap?
        bitmap = when {
            drawable is BitmapDrawable -> {
                val bitmapDrawable: BitmapDrawable? = drawable as BitmapDrawable?
                bitmapDrawable?.bitmap
            }
            drawable != null -> {
                Utils.asBitmap(drawable, width, height)
            }
            else -> {
                null
            }
        }
        setImageBitmap(bitmap)
    }

    override fun setImageURI(uri: Uri?) {
        extensions()
            .using(Extensions.LoaderType.COIL)
            .load(uri)
    }

    override fun setImageBitmap(bitmap: Bitmap?) {
        this.bitmap = bitmap
        resetTouchManager()
        invalidate()
    }

    /**
     * @return Current working Bitmap or `null` if none has been set yet.
     */
    fun getImageBitmap(): Bitmap? {
        return bitmap
    }

    private fun resetTouchManager() {
        val invalidBitmap = bitmap == null
        val bitmapWidth = if (invalidBitmap) 0 else bitmap?.width ?: 0
        val bitmapHeight = if (invalidBitmap) 0 else bitmap?.height ?: 0
        touchManager?.resetFor(bitmapWidth, bitmapHeight, width, height)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val result = super.dispatchTouchEvent(event)
        if (!isEnabled) {
            return result
        }
        touchManager?.onEvent(event)
        invalidate()
        return true
    }

    /**
     * Performs synchronous image cropping based on configuration.
     *
     * @return A [Bitmap] cropped based on viewport and user panning and zooming or `null` if no [Bitmap] has been
     * provided.
     */
    fun crop(): Bitmap? {
        if (bitmap == null) {
            return null
        }
        val src: Bitmap = bitmap!!
        val srcConfig: Bitmap.Config = src.config
        val config: Bitmap.Config = if (srcConfig == null) Bitmap.Config.ARGB_8888 else srcConfig
        val viewportHeight: Int = touchManager?.viewportHeight ?: 0
        val viewportWidth: Int = touchManager?.viewportWidth ?: 0
        val viewportCornerRadius: Int = touchManager?.viewportCornerRadius?: 0
        val dst: Bitmap = Bitmap.createBitmap(viewportWidth, viewportHeight, config)
        val canvas = Canvas(dst)
        val left = (right - viewportWidth) / 2
        val top = (bottom - top - viewportHeight) / 2
        val rectF = RectF(0f, 0f, dst.width.toFloat(), dst.height.toFloat())
        bitmapPaint.xfermode = null
        if (shape == Shape.RECTANGLE) {
            canvas.drawRoundRect(rectF, viewportCornerRadius.toFloat(), viewportCornerRadius.toFloat(), bitmapPaint)
        } else {
            canvas.drawOval(rectF, bitmapPaint)
        }
        bitmapPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.translate(-left.toFloat(), -top.toFloat())
        drawBitmap(canvas)
        return dst
    }

    /**
     * Obtain current viewport width.
     *
     * @return Current viewport width.
     *
     * Note: It might be 0 if layout pass has not been completed.
     */
    val viewportWidth: Int
        get() = touchManager?.viewportWidth ?: 0

    /**
     * Obtain current viewport height.
     *
     * @return Current viewport height.
     *
     * Note: It might be 0 if layout pass has not been completed.
     */
    val viewportHeight: Int
        get() = touchManager?.viewportHeight ?: 0

    /**
     * Offers common utility extensions.
     *
     * @return Extensions object used to perform chained calls.
     */
    fun extensions(): Extensions {
        if (extensions == null) {
            extensions = Extensions(this)
        }
        return extensions!!
    }

    /**
     * Set the shape of crop
     *
     * @param shape
     */
    fun setShape(@Shape shape: Int) {
        this.shape = shape
        config?.setShape(shape)
        invalidate()
    }

    /**
     * Get the shape of crop
     *
     * @return Int
     */
    fun getShape(): Int {
        return shape
    }

    /**
     * Optional extensions to perform common actions involving a [CropView]
     */
    class Extensions internal constructor(private val cropView: CropView) {
        /**
         * Load a [Bitmap] using an automatically resolved [BitmapLoader] which will attempt to scale image to fill view.
         *
         * @param model Model used by [BitmapLoader] to load desired [Bitmap]
         */
        fun load(model: Any?) {
            LoadRequest(cropView)
                    .load(model)
        }

        /**
         * Load a [Bitmap] using given [BitmapLoader], you must call [LoadRequest.load] afterwards.
         *
         * @param bitmapLoader [BitmapLoader] used to load desired [Bitmap]
         */
        fun using(bitmapLoader: BitmapLoader?): LoadRequest {
            return LoadRequest(cropView).using(bitmapLoader)
        }

        enum class LoaderType {
            PICASSO, GLIDE, COIL, UIL, CLASS_LOOKUP
        }

        /**
         * Load a [Bitmap] using a reference to a [BitmapLoader], you must call [LoadRequest.load] afterwards.
         *
         *
         * Please ensure that the library for the [BitmapLoader] you reference is available on the classpath.
         *
         * @param loaderType the [BitmapLoader] to use to load desired (@link Bitmap}
         */
        fun using(loaderType: LoaderType): LoadRequest {
            return LoadRequest(cropView).using(loaderType)
        }

        /**
         * Perform an asynchronous crop request.
         *
         * @return [CropRequest] used to chain a configure cropping request, you must call either one of:
         *
         *  * [CropRequest.into]
         *  * [CropRequest.into]
         *
         */
        fun crop(): CropRequest {
            return CropRequest(cropView)
        }

        /**
         * Perform a pick image request using [Activity.startActivityForResult].
         */
        fun pickUsing(activity: Activity, requestCode: Int) {
            CropViewExtensions.pickUsing(activity, requestCode)
        }

        /**
         * Perform a pick image request using [Fragment.startActivityForResult].
         */
        fun pickUsing(fragment: Fragment, requestCode: Int) {
            CropViewExtensions.pickUsing(fragment, requestCode)
        }
    }
}