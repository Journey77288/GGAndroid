package io.ganguo.picker.engine

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/06
 *     desc   : 图片框架加载图片抽象接口
 * </pre>
 *
 * 图片框架加载图片抽象接口
 * 定义了几个关键方法 在使用处实现
 * @param
 * @see
 * @author Raynor
 * @property
 */
interface ImageEngine {

    /**
     * 加载图片缩略图
     * @param [context] 上下文
     * @param [resize] 目标尺寸 缩略图一般会缩小到指定尺寸再加载 不然会OOM
     * @param [placeholder] 占位图
     * @param [imageView] 目标Image View
     * @param [uri] 资源文件的Uri
     */
    fun loadThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView?, uri: Uri?)

    /**
     * 加载原始图片
     * @param [context] 上下文
     * @param [resizeX] 目标宽度 一般是会等比缩放
     * @param [resizeY] 目标高度 一般是会等比缩放
     * @param [imageView] 目标Image View
     * @param [uri] 资源文件的Uri
     */
    fun loadImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?)

    /**
     * 加载原始图片
     *
     * @param [context] 上下文
     * @param [resizeX] 目标宽度 一般是会等比缩放
     * @param [resizeY] 目标高度 一般是会等比缩放
     * @param [uri] 资源文件的Uri
     * @param [callback] 加载完成回调
     */
    fun loadImage(context: Context?, resizeX: Int, resizeY: Int, uri: Uri?, callback: ((Drawable) -> Unit))

    /**
     * 加载视频缩略图（第一帧）
     */
    fun loadVideoThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView?, uri: Uri?)

    /**
     * 加载视频第一帧大图（在预览模式时使用）
     */
    fun loadVideoFirstFrame(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?)

    /**
     * 加载GIF图的缩略图
     * @param [context] 上下文
     * @param [resize] 目标尺寸 缩略图一般会缩小到指定尺寸再加载 不然会OOM
     * @param [placeholder] 占位图
     * @param [imageView] 目标Image View
     * @param [uri] 资源文件的Uri
     */
    fun loadGifThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView?, uri: Uri?)

    /**
     * 加载GIF原始图片
     * @param [context] 上下文
     * @param [resizeX] 目标宽度 一般是会等比缩放
     * @param [resizeY] 目标高度 一般是会等比缩放
     * @param [imageView] 目标Image
     */
    fun loadGifImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?)
}