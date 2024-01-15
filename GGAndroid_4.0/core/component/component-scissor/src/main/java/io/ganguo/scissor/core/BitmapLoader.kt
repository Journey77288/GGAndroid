package io.ganguo.scissor.core

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable

/**
 * Load extension delegates actual Bitmap loading to a BitmapLoader allowing it to use different implementations.
 */
interface BitmapLoader {
    fun load(@Nullable model: Any?, @NonNull view: ImageView)
}