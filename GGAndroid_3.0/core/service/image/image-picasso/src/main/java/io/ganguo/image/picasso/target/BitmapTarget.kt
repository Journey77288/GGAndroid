package io.ganguo.image.picasso.target

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * <pre>
 * author : leo
 * time   : 2019/08/27
 * desc   : Picasso bitmap Target
 * </pre>
 */
open class BitmapTarget : Target {
    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {}
    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
    override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
}