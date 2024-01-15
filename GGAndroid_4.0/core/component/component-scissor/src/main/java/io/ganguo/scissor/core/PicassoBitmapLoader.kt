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

import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation
import io.ganguo.scissor.view.widget.CropView
import java.io.File

/**
 * A [BitmapLoader] with transformation for [Picasso] image library.
 *
 * @see PicassoBitmapLoader.createUsing
 * @see PicassoBitmapLoader.createUsing
 */
class PicassoBitmapLoader(picasso: Picasso, transformation: Transformation) : BitmapLoader {
    private val picasso: Picasso = picasso
    private val transformation: Transformation = transformation
    
    override fun load(model: Any?, imageView: ImageView) {
        val requestCreator: RequestCreator = if (model is Uri || model == null) {
            picasso.load(model as Uri?)
        } else if (model is String) {
            picasso.load(model)
        } else if (model is File) {
            picasso.load(model)
        } else if (model is Int) {
            picasso.load(model)
        } else {
            throw IllegalArgumentException("Unsupported model $model")
        }
        requestCreator
                .transform(transformation)
                .into(imageView)
    }

    companion object {
        @JvmOverloads
        fun createUsing(cropView: CropView, picasso: Picasso = Picasso.get()): BitmapLoader {
            return PicassoBitmapLoader(picasso,
                    PicassoFillViewportTransformation.createUsing(cropView.viewportWidth, cropView.viewportHeight))
        }
    }

}