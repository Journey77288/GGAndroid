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

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import androidx.fragment.app.Fragment
import io.ganguo.scissor.view.widget.CropView
import io.ganguo.scissor.view.widget.CropView.Extensions.LoaderType

object CropViewExtensions {
    fun pickUsing(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(
                createChooserIntent(),
                requestCode)
    }

    fun pickUsing(fragment: Fragment, requestCode: Int) {
        fragment.startActivityForResult(
                createChooserIntent(),
                requestCode)
    }

    private fun createChooserIntent(): Intent {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        return Intent.createChooser(intent, null)
    }

    private val HAS_COIL = canHasClass("coil.Coil")
    private val HAS_GLIDE = canHasClass("com.bumptech.glide.Glide")
    private val HAS_PICASSO = canHasClass("com.squareup.picasso.Picasso")

    fun resolveBitmapLoader(cropView: CropView, loaderType: LoaderType?): BitmapLoader {
        when(loaderType) {
            LoaderType.COIL -> return CoilBitmapLoader.createUsing(cropView)
            LoaderType.GLIDE -> return GlideBitmapLoader.createUsing(cropView)
            LoaderType.PICASSO -> return PicassoBitmapLoader.createUsing(cropView)
            LoaderType.UIL, LoaderType.CLASS_LOOKUP -> {}
            else -> throw IllegalStateException("Unsupported type of loader = $loaderType")
        }

        if (HAS_COIL) {
            return CoilBitmapLoader.createUsing(cropView)
        }

        if (HAS_GLIDE) {
            return GlideBitmapLoader.createUsing(cropView)
        }

        if (HAS_PICASSO) {
            return PicassoBitmapLoader.createUsing(cropView)
        }

        throw IllegalStateException("You must provide a BitmapLoader")
    }

    fun canHasClass(className: String?): Boolean {
        try {
            Class.forName(className!!)
            return true
        } catch (e: ClassNotFoundException) {
        }
        return false
    }

    fun computeTargetSize(sourceWidth: Int, sourceHeight: Int, viewportWidth: Int, viewportHeight: Int): Rect {
        if (sourceWidth == viewportWidth && sourceHeight == viewportHeight) {
            return Rect(0, 0, viewportWidth, viewportHeight) // Fail fast for when source matches exactly on viewport
        }
        val scale: Float = if (sourceWidth * viewportHeight > viewportWidth * sourceHeight) {
            viewportHeight.toFloat() / sourceHeight.toFloat()
        } else {
            viewportWidth.toFloat() / sourceWidth.toFloat()
        }
        val recommendedWidth = (sourceWidth * scale + 0.5f).toInt()
        val recommendedHeight = (sourceHeight * scale + 0.5f).toInt()
        return Rect(0, 0, recommendedWidth, recommendedHeight)
    }
}