package io.image.glide

import android.graphics.BitmapFactory
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import io.image.glide.entity.ImageSize

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/09/26
 *     desc   : BitmapFactory.Options中获取图片尺寸Transcoder
 * </pre>
 */
class OptionsSizeResourceTranscoder : ResourceTranscoder<BitmapFactory.Options, ImageSize> {
    override fun transcode(toTranscode: Resource<BitmapFactory.Options>, options: Options): Resource<ImageSize>? {
        val bmOptions = toTranscode.get()
        val imageSize = ImageSize(bmOptions.outWidth, bmOptions.outHeight)
        return SimpleResource(imageSize)
    }
}