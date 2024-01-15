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

import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation

internal class CoilFillViewportTransformation(private val viewportWidth: Int, private val viewportHeight: Int) : Transformation {

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val sourceWidth = input.width
        val sourceHeight = input.height
        val target = CropViewExtensions.computeTargetSize(sourceWidth, sourceHeight, viewportWidth, viewportHeight)
        val targetWidth = target.width()
        val targetHeight = target.height()
        val result = Bitmap.createScaledBitmap(
                input,
                targetWidth,
                targetHeight,
                true)
        if (result != input) {
            input.recycle()
        }
        return result
    }

    override val cacheKey: String by lazy { viewportWidth.toString() + "x" + viewportHeight }

    companion object {
        fun createUsing(viewportWidth: Int, viewportHeight: Int): Transformation {
            return CoilFillViewportTransformation(viewportWidth, viewportHeight)
        }
    }
}