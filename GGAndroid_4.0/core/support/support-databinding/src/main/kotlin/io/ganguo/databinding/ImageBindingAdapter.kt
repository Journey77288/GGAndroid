@file:JvmName("ImageBindingAdapter")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/01
 *     desc   : Image DataBinding Adapter
 * </pre>
 */

package io.ganguo.databinding

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter


/**
 * Bind the Bitmap to the ImageView
 * @receiver ImageView
 * @param bitmap Bitmap?
 */
@BindingAdapter(
    "android:bind_bitmap_to_image_view"
)
fun ImageView?.bindBitmap(bitmap: Bitmap) {
    this?.apply {
        setImageBitmap(bitmap)
    }
}

