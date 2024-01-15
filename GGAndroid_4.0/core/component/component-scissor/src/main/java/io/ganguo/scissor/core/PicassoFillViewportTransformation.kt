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
import com.squareup.picasso.Transformation

internal class PicassoFillViewportTransformation(private val viewportWidth: Int, private val viewportHeight: Int) : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val sourceWidth: Int = source.width
        val sourceHeight: Int = source.height
        val target = CropViewExtensions.computeTargetSize(sourceWidth, sourceHeight, viewportWidth, viewportHeight)
        val result: Bitmap = Bitmap.createScaledBitmap(
                source,
                target.width(),
                target.height(),
                true)
        if (result != source) {
            source.recycle()
        }
        return result
    }

    override fun key(): String {
        return viewportWidth.toString() + "x" + viewportHeight
    }

    companion object {
        fun createUsing(viewportWidth: Int, viewportHeight: Int): Transformation {
            return PicassoFillViewportTransformation(viewportWidth, viewportHeight)
        }
    }
}