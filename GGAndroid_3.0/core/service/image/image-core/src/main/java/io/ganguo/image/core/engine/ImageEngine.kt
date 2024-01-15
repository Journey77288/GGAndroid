package io.ganguo.image.core.engine

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import io.ganguo.image.core.drawable.URLDrawable
import io.ganguo.image.core.entity.ImageParam

/**
 * <pre>
 * author : leo
 * time   : 2019/03/21
 * desc   : 图片加载引擎接口
 * </pre>
 */
interface ImageEngine {
    /**
     * 恢复加载图片，一般配合滑动列表来用，滑动不加载，停止滑动才加载图片
     */
    fun resumeLoadImage(context: Context?)

    /**
     * 停止加载图片，一般配合滑动列表来用，滑动不加载，停止滑动才加载图片
     */
    fun pauseLoadImage(context: Context?)

    /**
     * 加载图片到Drawable，一般用于加载TextView加载Html
     *
     * @param textView
     * @param drawable         目标URLDrawable
     * @param url              图片资源链接
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    fun displayImageDrawable(textView: TextView?, param: ImageParam, drawable: URLDrawable?, matchParentWidth: Boolean)

    /**
     * 加载图片
     *
     * @param imageView
     * @param param         图片资源
     */
    fun displayImage(imageView: ImageView?, param: ImageParam)

    /**
     * 下载图片
     *
     * @param context  上下文
     * @param param 图片资源
     */
    fun downloadImage(context: Context?, param: ImageParam): Bitmap?

    /**
     * 图片圆角类型
     */
    enum class CornerType {
        ALL, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, TOP, BOTTOM, LEFT, RIGHT, OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT, DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
    }

    /**
     * 加载类型
     * - NORMAL 默认加载
     * - CIRCLE 圆形（如头像）
     * - ROUND 圆角类型
     */
    enum class ImageType {
        NORMAL, CIRCLE, ROUND
    }
}