package io.image.glide

import android.content.Context
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.module.AppGlideModule
import io.image.glide.entity.ImageSize
import java.io.File

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/09/26
 *     desc   : Glide自定义Module
 * </pre>
 */
@com.bumptech.glide.annotation.GlideModule
class GlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.prepend(File::class.java, BitmapFactory.Options::class.java, BitmapSizeDecoder())
        registry.register(BitmapFactory.Options::class.java, ImageSize::class.java, OptionsSizeResourceTranscoder())
    }
}