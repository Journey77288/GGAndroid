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

import android.widget.ImageView
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.Transformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import io.ganguo.scissor.view.widget.CropView

/**
 * A [BitmapLoader] with transformation for [Coil] image library.
 *
 * @see CoilBitmapLoader.createUsing
 * @see CoilBitmapLoader.createUsing
 */
class CoilBitmapLoader(private val loader: ImageLoader, private val transformation: Transformation) : BitmapLoader {

    override fun load(model: Any?, view: ImageView) {
        val request = ImageRequest
                .Builder(view.context)
                .target(view)
                .data(model)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .networkCachePolicy(CachePolicy.ENABLED)
                .transformations(transformation)
                .build()
        loader.enqueue(request)
    }

    companion object {
        @JvmOverloads
        fun createUsing(cropView: CropView, imageLoader: ImageLoader = ImageLoader.Builder(cropView.context).build()) : BitmapLoader {
            return CoilBitmapLoader(imageLoader, CoilFillViewportTransformation.createUsing(cropView.viewportWidth, cropView.viewportHeight))
        }
    }
}