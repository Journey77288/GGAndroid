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
package io.ganguo.scissor.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.concurrent.Executors
import java.util.concurrent.Future

object Utils {
    fun checkArg(expression: Boolean, msg: String) {
        require(expression) { msg }
    }

    fun checkNotNull(`object`: Any?, msg: String?) {
        if (`object` == null) {
            throw NullPointerException(msg)
        }
    }

    fun asBitmap(drawable: Drawable, minWidth: Int, minHeight: Int): Bitmap {
        val tmpRect = Rect()
        drawable.copyBounds(tmpRect)
        if (tmpRect.isEmpty) {
            tmpRect[0, 0, Math.max(minWidth, drawable.intrinsicWidth)] = Math.max(minHeight, drawable.intrinsicHeight)
            drawable.bounds = tmpRect
        }
        val bitmap: Bitmap = Bitmap.createBitmap(tmpRect.width(), tmpRect.height(), Bitmap.Config.ARGB_8888)
        drawable.draw(Canvas(bitmap))
        return bitmap
    }

    private val EXECUTOR_SERVICE = Executors.newCachedThreadPool()
    private const val TAG = "scissors.Utils"
    fun flushToFile(bitmap: Bitmap?,
                    format: Bitmap.CompressFormat?,
                    quality: Int,
                    file: File): Future<Void?> {
        return EXECUTOR_SERVICE.submit({
            var outputStream: OutputStream? = null
            try {
                file.parentFile.mkdirs()
                outputStream = FileOutputStream(file)
                format?.let { bitmap?.compress(it, quality, outputStream) }
                outputStream.flush()
            } catch (throwable: Throwable) {
                Log.d(TAG, "Error attempting to save bitmap.", throwable)
            } finally {
                closeQuietly(outputStream)
            }
        }, null)
    }

    fun flushToStream(bitmap: Bitmap?,
                      format: Bitmap.CompressFormat?,
                      quality: Int,
                      outputStream: OutputStream,
                      closeWhenDone: Boolean): Future<Void?> {
        return EXECUTOR_SERVICE.submit({
            try {
                format?.let { bitmap?.compress(it, quality, outputStream) }
                outputStream.flush()
            } catch (throwable: Throwable) {
                Log.d(TAG, "Error attempting to save bitmap.", throwable)
            } finally {
                if (closeWhenDone) {
                    closeQuietly(outputStream)
                }
            }
        }, null)
    }

    private fun closeQuietly(outputStream: OutputStream?) {
        try {
            outputStream?.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error attempting to close stream.", e)
        }
    }
}