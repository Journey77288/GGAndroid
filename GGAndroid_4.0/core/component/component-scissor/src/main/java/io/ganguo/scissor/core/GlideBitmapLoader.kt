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

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import io.ganguo.scissor.view.widget.CropView

/**
 * A [BitmapLoader] with transformation for [Glide] image library.
 *
 * @see GlideBitmapLoader.createUsing
 * @see GlideBitmapLoader.createUsing
 */
class GlideBitmapLoader(private val requestManager: RequestManager, private val transformation: BitmapTransformation) : BitmapLoader {

    @SuppressLint("CheckResult")
    override fun load(model: Any?, imageView: ImageView) {
        val requestOptions = RequestOptions()
        requestOptions.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .transform(transformation)

        requestManager.asBitmap()
                .load(model)
                .apply(requestOptions)
                .into(imageView)
    }

    companion object {
        @JvmOverloads
        fun createUsing(cropView: CropView, requestManager: RequestManager = Glide.with(cropView.context)): BitmapLoader {
            return GlideBitmapLoader(requestManager,
                    GlideFillViewportTransformation.createUsing(cropView.viewportWidth, cropView.viewportHeight))
        }
    }
}