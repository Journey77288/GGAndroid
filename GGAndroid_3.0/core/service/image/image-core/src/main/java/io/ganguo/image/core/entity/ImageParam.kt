package io.ganguo.image.core.entity

import android.graphics.drawable.Drawable
import android.net.Uri
import io.ganguo.image.core.R
import io.ganguo.image.core.engine.ImageEngine
import io.ganguo.utils.helper.ResHelper
import io.ganguo.utils.util.Strings
import java.io.File

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/07
 *     desc   : 图片加载参数
 * </pre>
 */

data class ImageParam(private var url: String,
                      var width: Int = -1,
                      var height: Int = -1,
                      private var error: Drawable? = null,
                      private var placeholder: Drawable? = null,
                      var radius: Int = 0,
                      private var cornerType: ImageEngine.CornerType? = null,
                      var type: ImageEngine.ImageType = ImageEngine.ImageType.NORMAL) {
    /**
     * error 为 null 则返回透明样式
     * @return Drawable
     */
    fun getErrorDrawable(): Drawable {
        return error ?: ResHelper.getDrawable(R.color.transparent)
    }

    /**
     * placeholder 为 null 则返回透明样式
     * @return Drawable
     */
    fun getPlaceholderDrawable(): Drawable {
        return placeholder ?: ResHelper.getDrawable(R.color.transparent)
    }

    /**
     * 圆角类型为 null 则默认 all 类型
     * @return ImageEngine.CornerType
     */
    fun getCornerType(): ImageEngine.CornerType {
        return cornerType ?: ImageEngine.CornerType.ALL
    }

    /**
     * url 为空 则返回一个默认的链接，避免 Picasso 空校验报错
     * @return String
     */
    fun getUrl(): String {
        if (Strings.isEmpty(url)) {
            return "http://xxx"
        }
        return url
    }

    class Builder(var type: ImageEngine.ImageType = ImageEngine.ImageType.NORMAL) {

        var url = ""
        var error: Drawable? = null
        var placeholder: Drawable? = null
        var width: Int = -1
        var height: Int = -1
        var radius: Int = 0
        var cornerType: ImageEngine.CornerType? = null

        fun url(url: String) = apply { this.url = url }
        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun error(error: Drawable?) = apply { this.error = error }
        fun placeholder(placeholder: Drawable?) = apply { this.placeholder = placeholder }
        fun radius(radius: Int) = apply { this.radius = radius }
        fun cornerType(cornerType: ImageEngine.CornerType?) = apply { this.cornerType = cornerType }

        /**
         * 若加载来源为 file 则将其转成 url
         * @param file File
         * @return Builder
         */
        fun file(file: File) = apply { url = Uri.fromFile(file).toString() }

        /**
         *  若加载来源为 Uri 则将其转成 url
         * @param uri Uri
         * @return Builder
         */
        fun uri(uri: Uri) = apply { url = uri.toString() }

        fun build(): ImageParam {
            return ImageParam(url, width, height, error, placeholder, radius, cornerType, type)
        }
    }
}
