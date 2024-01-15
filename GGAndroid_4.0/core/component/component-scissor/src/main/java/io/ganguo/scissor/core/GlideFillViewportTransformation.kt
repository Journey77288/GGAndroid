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
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import io.ganguo.scissor.core.CropViewExtensions.computeTargetSize
import java.nio.charset.Charset
import java.security.MessageDigest

internal class GlideFillViewportTransformation(private val viewportWidth: Int, private val viewportHeight: Int) : BitmapTransformation() {
    override fun transform(bitmapPool: BitmapPool, source: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val sourceWidth = source.width
        val sourceHeight = source.height

        val target = computeTargetSize(sourceWidth, sourceHeight, viewportWidth, viewportHeight)

        val targetWidth = target.width()
        val targetHeight = target.height()
        return Bitmap.createScaledBitmap(
                source,
                targetWidth,
                targetHeight,
                true)
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is GlideFillViewportTransformation) {
            return obj.viewportWidth == viewportWidth && obj.viewportHeight == viewportHeight
        }
        return false
    }

    override fun hashCode(): Int {
        val hash = viewportWidth * 31 + viewportHeight
        return hash * 17 + ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private const val ID = "io.ganguo.scissor.core"
        private val ID_BYTES = ID.toByteArray(Charset.defaultCharset())
        fun createUsing(viewportWidth: Int, viewportHeight: Int): BitmapTransformation {
            return GlideFillViewportTransformation(viewportWidth, viewportHeight)
        }
    }
}