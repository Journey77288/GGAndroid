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
package io.ganguo.scissor.core

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.TargetApi
import android.graphics.Matrix
import android.graphics.Rect
import android.os.Build
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ImageView
import androidx.annotation.IntDef
import io.ganguo.scissor.bean.CropViewConfig
import io.ganguo.scissor.core.TouchManager.GestureAnimator.OnAnimationUpdateListener

class TouchManager(private val imageView: ImageView, cropViewConfig: CropViewConfig) {
    private val cropViewConfig: CropViewConfig
    private val scaleGestureDetector: ScaleGestureDetector
    private val gestureDetector: GestureDetector
    private var minimumScale = -1.0f
    private var maximumScale = -1.0f
    private var imageBounds: Rect? = null
    private var aspectRatio = 0f
    var viewportWidth = 0
        private set
    var viewportHeight = 0
        private set
    var viewportCornerRadius = 0
    private var bitmapWidth = 0
    private var bitmapHeight = 0
    private var verticalLimit = 0
    private var horizontalLimit = 0
    private var scale = -1.0f
    private val position = TouchPoint()
    private val gestureAnimator = GestureAnimator(object : OnAnimationUpdateListener {
        override fun onAnimationUpdate(@GestureAnimator.AnimationType animationType: Int, animationValue: Float) {
            if (animationType == GestureAnimator.ANIMATION_X) {
                position[animationValue] = position.y
                ensureInsideViewport()
            } else if (animationType == GestureAnimator.ANIMATION_Y) {
                position[position.x] = animationValue
                ensureInsideViewport()
            } else if (animationType == GestureAnimator.ANIMATION_SCALE) {
                scale = animationValue
                setLimits()
            }
            imageView.invalidate()
        }

        override fun onAnimationFinished() {
            ensureInsideViewport()
        }
    })
    private val scaleGestureListener: OnScaleGestureListener = object : OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scale = calculateScale(detector.scaleFactor)
            setLimits()
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {}
    }
    private val gestureListener: GestureDetector.OnGestureListener = object : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (e2.pointerCount != 1) {
                return true
            }
            val delta = TouchPoint(-distanceX, -distanceY)
            position.add(delta)
            ensureInsideViewport()
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
//            var velocityX = velocityX
//            var velocityY = velocityY
//            velocityX /= 2f
//            velocityY /= 2f
//            if (Math.abs(velocityX) < MINIMUM_FLING_VELOCITY) {
//                velocityX = 0f
//            }
//            if (Math.abs(velocityY) < MINIMUM_FLING_VELOCITY) {
//                velocityY = 0f
//            }
//            if (velocityX == 0f && velocityY == 0f) {
//                return true
//            }
//            val width = (imageBounds!!.right * scale).toInt()
//            val height = (imageBounds!!.bottom * scale).toInt()
//            val scroller = OverScroller(imageView.context)
//            scroller.fling(e1.x.toInt(), e1.y.toInt(), velocityX.toInt(), velocityY.toInt(), -width, width, -height, height)
//            val target = TouchPoint(scroller.finalX.toFloat(), scroller.finalY.toFloat())
//            val x = if (velocityX == 0f) position.x else target.x * scale
//            val y = if (velocityY == 0f) position.y else target.y * scale
//            gestureAnimator.animateTranslation(position.x, x, position.y, y)
//            return true
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            val fromX: Float
            val toX: Float
            val fromY: Float
            val toY: Float
            val targetScale: Float
            val eventPoint = TouchPoint(e.x, e.y)
            if (scale == minimumScale) {
                targetScale = maximumScale / 2
                val translatedTargetPosition = mapTouchCoordinateToMatrix(eventPoint, targetScale)
                val centeredTargetPosition = centerCoordinates(translatedTargetPosition)
                fromX = position.x
                toX = centeredTargetPosition.x
                fromY = position.y
                toY = centeredTargetPosition.y
            } else {
                targetScale = minimumScale
                val translatedPosition = mapTouchCoordinateToMatrix(eventPoint, scale)
                val centeredTargetPosition = centerCoordinates(translatedPosition)
                fromX = centeredTargetPosition.x
                toX = 0f
                fromY = centeredTargetPosition.y
                toY = 0f
            }
            gestureAnimator.animateDoubleTap(fromX, toX, fromY, toY, scale, targetScale)
            return true
        }

        private fun centerCoordinates(coordinates: TouchPoint): TouchPoint {
            val x = coordinates.x + imageBounds!!.right / 2
            val y = coordinates.y + imageBounds!!.bottom / 2
            return TouchPoint(x, y)
        }
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    fun onEvent(event: MotionEvent) {
        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        if (isUpAction(event.actionMasked)) {
            ensureInsideViewport()
        }
    }

    fun applyPositioningAndScale(matrix: Matrix) {
        matrix.postTranslate(-bitmapWidth / 2.0f, -bitmapHeight / 2.0f)
        matrix.postScale(scale, scale)
        matrix.postTranslate(position.x, position.y)
    }

    fun resetFor(bitmapWidth: Int, bitmapHeight: Int, availableWidth: Int, availableHeight: Int) {
        aspectRatio = cropViewConfig.viewportRatio
        imageBounds = Rect(0, 0, availableWidth / 2, availableHeight / 2)
        setViewport(bitmapWidth, bitmapHeight, availableWidth, availableHeight)
        scale = -1f
        this.bitmapWidth = bitmapWidth
        this.bitmapHeight = bitmapHeight
        if (bitmapWidth > 0 && bitmapHeight > 0) {
            setMinimumScale()
            setLimits()
            resetPosition()
            ensureInsideViewport()
        }
    }

    fun getAspectRatio(): Float {
        return aspectRatio
    }

    fun setAspectRatio(ratio: Float) {
        aspectRatio = ratio
        cropViewConfig.viewportRatio = ratio
    }

    private fun mapTouchCoordinateToMatrix(coordinate: TouchPoint, targetScale: Float): TouchPoint {
        val width = bitmapWidth * targetScale
        val height = bitmapHeight * targetScale
        val x0 = width / 2
        val y0 = height / 2
        var newX = coordinate.x * targetScale
        newX = -(newX - x0)
        var newY = coordinate.y * targetScale
        newY = if (newY > y0) {
            -(newY - y0)
        } else {
            y0 - newY
        }
        return TouchPoint(newX, newY)
    }

    private fun ensureInsideViewport() {
        if (imageBounds == null) {
            return
        }
        var newY = position.y
        val bottom = imageBounds!!.bottom
        if (bottom - newY >= verticalLimit) {
            newY = (bottom - verticalLimit).toFloat()
        } else if (newY - bottom >= verticalLimit) {
            newY = (bottom + verticalLimit).toFloat()
        }
        var newX = position.x
        val right = imageBounds!!.right
        if (newX <= right - horizontalLimit) {
            newX = (right - horizontalLimit).toFloat()
        } else if (newX > right + horizontalLimit) {
            newX = (right + horizontalLimit).toFloat()
        }
        position[newX] = newY
    }

    private fun setViewport(bitmapWidth: Int, bitmapHeight: Int, availableWidth: Int, availableHeight: Int) {
        val imageAspect = bitmapWidth.toFloat() / bitmapHeight
        val viewAspect = availableWidth.toFloat() / availableHeight
        var ratio = cropViewConfig.viewportRatio
        if (java.lang.Float.compare(0f, ratio) == 0) {
            // viewport ratio of 0 means match native ratio of bitmap
            ratio = imageAspect
        }
        if (ratio > viewAspect) {
            // viewport is wider than view
            viewportWidth = availableWidth - cropViewConfig.viewportOverlayPadding * 2
            viewportHeight = (viewportWidth * (1 / ratio)).toInt()
        } else {
            // viewport is taller than view
            viewportHeight = availableHeight - cropViewConfig.viewportOverlayPadding * 2
            viewportWidth = (viewportHeight * ratio).toInt()
        }
        viewportCornerRadius = cropViewConfig.viewportOverlayCornerRadius
    }

    private fun setLimits() {
        horizontalLimit = computeLimit((bitmapWidth * scale).toInt(), viewportWidth)
        verticalLimit = computeLimit((bitmapHeight * scale).toInt(), viewportHeight)
    }

    private fun resetPosition() {
        position[imageBounds!!.right.toFloat()] = imageBounds!!.bottom.toFloat()
    }

    private fun setMinimumScale() {
        val fw = viewportWidth.toFloat() / bitmapWidth
        val fh = viewportHeight.toFloat() / bitmapHeight
        minimumScale = Math.max(fw, fh)
        scale = Math.max(scale, minimumScale)
    }

    private fun calculateScale(newScaleDelta: Float): Float {
        return Math.max(minimumScale, Math.min(scale * newScaleDelta, maximumScale))
    }

    private class GestureAnimator(private val listener: OnAnimationUpdateListener) {
        @IntDef(ANIMATION_X, ANIMATION_Y, ANIMATION_SCALE)
        @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
        annotation class AnimationType
        interface OnAnimationUpdateListener {
            fun onAnimationUpdate(@AnimationType animationType: Int, animationValue: Float)

            fun onAnimationFinished()
        }

        private var xAnimator: ValueAnimator? = null
        private var yAnimator: ValueAnimator? = null
        private var scaleAnimator: ValueAnimator? = null

        private var animator: AnimatorSet? = null

        val updateListener = AnimatorUpdateListener { animation ->
            val value = animation.animatedValue as Float

            when {
                animation === xAnimator -> {
                    listener.onAnimationUpdate(ANIMATION_X, value)
                }
                animation === yAnimator -> {
                    listener.onAnimationUpdate(ANIMATION_Y, value)
                }
                animation === scaleAnimator -> {
                    listener.onAnimationUpdate(ANIMATION_SCALE, value)
                }
            }
        }

        private val animatorListener: Animator.AnimatorListener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                xAnimator?.removeUpdateListener(updateListener)
                yAnimator?.removeUpdateListener(updateListener)
                scaleAnimator?.removeUpdateListener(updateListener)
                animator?.removeAllListeners()
                listener.onAnimationFinished()
            }
        }

        fun animateTranslation(fromX: Float, toX: Float, fromY: Float, toY: Float) {
            animator?.cancel()

            xAnimator = ValueAnimator.ofFloat(fromX, toX)
            yAnimator = ValueAnimator.ofFloat(fromY, toY)
            scaleAnimator = null

            xAnimator?.addUpdateListener(updateListener)
            yAnimator?.addUpdateListener(updateListener)

            animate(DecelerateInterpolator(), 250, xAnimator, yAnimator)
        }

        fun animateDoubleTap(fromX: Float, toX: Float, fromY: Float, toY: Float, fromScale: Float, toScale: Float) {
            animator?.cancel()

            xAnimator = ValueAnimator.ofFloat(fromX, toX)
            yAnimator = ValueAnimator.ofFloat(fromY, toY)
            scaleAnimator = ValueAnimator.ofFloat(fromScale, toScale)

            xAnimator?.addUpdateListener(updateListener)
            yAnimator?.addUpdateListener(updateListener)
            scaleAnimator?.addUpdateListener(updateListener)

            animate(AccelerateDecelerateInterpolator(), 500, scaleAnimator, xAnimator, yAnimator)
        }

        private fun animate(interpolator: Interpolator, duration: Long, first: ValueAnimator?, vararg animators: ValueAnimator?) {
            animator = AnimatorSet()
            animator!!.duration = duration
            animator!!.interpolator = interpolator
            animator!!.addListener(animatorListener)
            val builder = animator!!.play(first)
            for (valueAnimator in animators) {
                builder.with(valueAnimator)
            }
            animator!!.start()
        }

        companion object {
            const val ANIMATION_X = 0
            const val ANIMATION_Y = 1
            const val ANIMATION_SCALE = 2
        }
    }

    companion object {
        private const val MINIMUM_FLING_VELOCITY = 2500
        private fun computeLimit(bitmapSize: Int, viewportSize: Int): Int {
            return (bitmapSize - viewportSize) / 2
        }

        private fun isUpAction(actionMasked: Int): Boolean {
            return actionMasked == MotionEvent.ACTION_POINTER_UP || actionMasked == MotionEvent.ACTION_UP
        }
    }

    init {
        scaleGestureDetector = ScaleGestureDetector(imageView.context, scaleGestureListener)
        gestureDetector = GestureDetector(imageView.context, gestureListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            scaleGestureDetector.isQuickScaleEnabled = true
        }
        this.cropViewConfig = cropViewConfig
        minimumScale = cropViewConfig.minScale
        maximumScale = cropViewConfig.maxScale
    }
}