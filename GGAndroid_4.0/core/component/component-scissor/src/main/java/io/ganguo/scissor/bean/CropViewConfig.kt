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
package io.ganguo.scissor.bean

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import com.example.commponent_scissor.R
import io.ganguo.scissor.view.widget.CropView

class CropViewConfig {
    var viewportRatio = DEFAULT_VIEWPORT_RATIO
        set(viewportRatio) {
            field = if (viewportRatio <= 0) DEFAULT_VIEWPORT_RATIO else viewportRatio
        }
    var maxScale = DEFAULT_MAXIMUM_SCALE
        set(maxScale) {
            field = if (maxScale <= 0) DEFAULT_MAXIMUM_SCALE else maxScale
        }
    var minScale = DEFAULT_MINIMUM_SCALE
        set(minScale) {
            field = if (minScale <= 0) DEFAULT_MINIMUM_SCALE else minScale
        }
    var viewportOverlayPadding = DEFAULT_VIEWPORT_OVERLAY_PADDING
    var viewportOverlayColor = DEFAULT_VIEWPORT_OVERLAY_COLOR
    var viewportOverlayCornerRadius = DEFAULT_VIEWPORT_OVERLAY_CORNER_RADIUS
    var strokeColor = DEFAULT_STROKE_COLOR
    var strokeWidth = DEFAULT_STROKE_WIDTH
    var strokeInterval = DEFAULT_STROKE_INTERVAL

    @CropView.Shape
    private var shape = DEFAULT_SHAPE
    @CropView.Shape
    fun shape(): Int {
        return shape
    }

    fun setShape(@CropView.Shape shape: Int) {
        this.shape = shape
    }

    companion object {
        const val DEFAULT_VIEWPORT_RATIO = 0f
        const val DEFAULT_MAXIMUM_SCALE = 10f
        const val DEFAULT_MINIMUM_SCALE = 0f
        const val DEFAULT_IMAGE_QUALITY = 100
        const val DEFAULT_VIEWPORT_OVERLAY_PADDING = 0
        const val DEFAULT_VIEWPORT_OVERLAY_CORNER_RADIUS = 0
        const val DEFAULT_STROKE_COLOR = Color.TRANSPARENT
        const val DEFAULT_STROKE_WIDTH = 2f
        const val DEFAULT_STROKE_INTERVAL = 20f
        const val DEFAULT_VIEWPORT_OVERLAY_COLOR = -0x38000000 // Black with 200 alpha
        val DEFAULT_SHAPE: Int = CropView.Shape.Companion.RECTANGLE
        fun from(context: Context, attrs: AttributeSet?): CropViewConfig {
            val cropViewConfig = CropViewConfig()
            if (attrs == null) {
                return cropViewConfig
            }
            val attributes: TypedArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CropView)
            cropViewConfig.viewportRatio = attributes.getFloat(R.styleable.CropView_cropviewViewportRatio,
                    DEFAULT_VIEWPORT_RATIO)
            cropViewConfig.maxScale = attributes.getFloat(R.styleable.CropView_cropviewMaxScale,
                    DEFAULT_MAXIMUM_SCALE)
            cropViewConfig.minScale = attributes.getFloat(R.styleable.CropView_cropviewMinScale,
                    DEFAULT_MINIMUM_SCALE)
            cropViewConfig.viewportOverlayColor = attributes.getColor(R.styleable.CropView_cropviewViewportOverlayColor,
                    DEFAULT_VIEWPORT_OVERLAY_COLOR)
            cropViewConfig.viewportOverlayPadding = attributes.getDimensionPixelSize(R.styleable.CropView_cropviewViewportOverlayPadding,
                    DEFAULT_VIEWPORT_OVERLAY_PADDING)
            cropViewConfig.viewportOverlayCornerRadius = attributes.getDimensionPixelOffset(R.styleable.CropView_cropviewViewportCornerRadius,
                    DEFAULT_VIEWPORT_OVERLAY_CORNER_RADIUS)
            cropViewConfig.strokeColor = attributes.getColor(R.styleable.CropView_cropviewStrokeColor, DEFAULT_STROKE_COLOR)
            cropViewConfig.strokeWidth = attributes.getDimension(R.styleable.CropView_cropviewStrokeWidth, DEFAULT_STROKE_WIDTH)
            cropViewConfig.strokeInterval = attributes.getDimension(R.styleable.CropView_cropviewStrokeInterval, DEFAULT_STROKE_INTERVAL)
            @CropView.Shape val shape: Int = attributes.getInt(
                    R.styleable.CropView_cropviewShape, DEFAULT_SHAPE)
            cropViewConfig.setShape(shape)
            attributes.recycle()
            return cropViewConfig
        }
    }
}