package io.image.entity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import io.image.ImageLoader
import io.image.engine.ImageEngine
import io.image.enums.ImageResourceType
import io.image.enums.ImageShapeType
import java.io.File

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/07
 *     desc   : 图片加载参数
 * </pre>
 */

class ImageParam(var context: Context,
                 var shapeType: ImageShapeType) {
    var url = ""
    var file: File? = null
    var filePath = ""
    var drawable: Drawable? = null
    var bitmap: Bitmap? = null
    var uri: Uri? = null

    var drawableRes = io.ganguo.resources.R.drawable.ic_launcher
    var errorPlaceholder: Drawable? = ImageLoader.get().errorPlaceholder
    var placeholder: Drawable? = ImageLoader.get().placeholder
    var width: Int = -1
    var height: Int = -1
    var radius: Float = 0f
    var crossFade: Boolean = true
    var cornerType: ImageEngine.CornerType? = null
    var resourceType: ImageResourceType = ImageResourceType.NO_SET_DATA


    /**
     * 是否开启加载动画，默认开启
     * @param crossFade Boolean
     * @return ImageParam
     */
    fun crossFade(crossFade: Boolean) = apply {
        this.crossFade = crossFade
    }


    /**
     * 加载图片链接
     * @param url String
     * @return Builder
     */
    fun url(url: String) = apply {
        this.url = url
        resourceType = ImageResourceType.URL
    }

    /**
     *  若加载来源为 Uri 则将其转成 url
     * @param uri Uri
     * @return Builder
     */
    fun uri(uri: Uri?) = apply {
        this.uri = uri
        resourceType = ImageResourceType.URI
    }

    /**
     * 加载本地图片文件
     * @param file File
     * @return Builder
     */
    fun file(file: File?) = apply {
        this.file = file
        this.resourceType = ImageResourceType.FILE
    }

    /**
     * 加载本地图片文件
     * @param filePath String
     * @return Builder
     */
    fun filePath(filePath: String) = apply {
        this.filePath = filePath
        this.resourceType = ImageResourceType.FILE_PATH
    }


    /**
     * 加载 drawableRes 资源
     * @param drawableRes Int
     * @return Builder
     */
    fun drawableRes(@DrawableRes drawableRes: Int?) = apply {
        this.drawableRes = drawableRes ?: 0
        this.resourceType = ImageResourceType.DRAWABLE_RES
    }


    /**
     * 加载drawable对象
     * @param drawable Drawable
     * @return Builder
     */
    fun drawable(drawable: Drawable?) = apply {
        this.drawable = drawable
        this.resourceType = ImageResourceType.DRAWABLE
    }

    /**
     * 加载Bitmap对象
     * @param bitmap Bitmap
     * @return Builder
     */
    fun bitmap(bitmap: Bitmap?) = apply {
        this.bitmap = bitmap
        this.resourceType = ImageResourceType.BITMAP
    }


    /**
     *
     * @param width Int
     * @return Builder
     */
    fun width(width: Int) = apply { this.width = width }


    /**
     * 配置加载图片高度
     * @param height Int
     * @return Builder
     */
    fun height(height: Int) = apply { this.height = height }

    /**
     * 图片加载错误占位图
     * @param error Drawable?
     * @return Builder
     */
    fun errorPlaceholder(error: Drawable?) = apply {
        error?.let {
            this.errorPlaceholder = it
        }
    }


    /**
     * 图片加载占位图
     * @param placeholder Drawable?
     * @return Builder
     */
    fun placeholder(placeholder: Drawable?) = apply {
        placeholder?.let {
            this.placeholder = it
        }
    }


    /**
     * @param radius Int
     * @return Builder
     */
    fun radius(cornerType: ImageEngine.CornerType?, radius: Float?) = apply {
        this.cornerType = cornerType ?: ImageEngine.CornerType.ALL
        this.radius = radius ?: 0f
    }


    /**
     * error 为 null 则返回透明样式
     * @return Drawable
     */
    fun getErrorDrawable(): Drawable {
        return errorPlaceholder ?: getDefaultDrawable()
    }

    /**
     * placeholder 为 null 则返回透明样式
     * @return Drawable
     */
    fun getPlaceholderDrawable(): Drawable {
        return placeholder ?: getDefaultDrawable()
    }


    /**
     * 默认透明Drawable
     * @return Drawable?
     */
    private fun getDefaultDrawable(): Drawable {
        return ResourcesCompat.getDrawable(
                context.resources,
                io.ganguo.resources.R.color.transparent, null
        )!!
    }


    /**
     * 获取加载的图片资源
     * @return Any?
     */
    fun getData(): Any? {
        return when (resourceType) {
            ImageResourceType.URL -> url
            ImageResourceType.BITMAP -> bitmap
            ImageResourceType.DRAWABLE -> drawable
            ImageResourceType.DRAWABLE_RES -> drawableRes
            ImageResourceType.FILE -> file
            ImageResourceType.FILE_PATH -> filePath
            ImageResourceType.URI -> uri
            ImageResourceType.NO_SET_DATA -> null
        }
    }

    /**
     * 是否为网络资源
     *
     * @return Boolean
     */
    fun isNetworkResource(): Boolean {
        return resourceType == ImageResourceType.URL
    }

    /**
     * 是否为本地资源
     *
     * @return Boolean
     */
    fun isLocalResource(): Boolean {
        return resourceType == ImageResourceType.URI || resourceType == ImageResourceType.FILE || resourceType == ImageResourceType.FILE_PATH
    }

    /**
     * 是否为Drawable资源
     *
     * @return Boolean
     */
    fun isDrawableResource(): Boolean {
        return resourceType == ImageResourceType.DRAWABLE || resourceType == ImageResourceType.DRAWABLE_RES
    }
}

