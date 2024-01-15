package io.image.coil

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.executeBlocking
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import coil.size.Size
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import io.image.coil.transformation.FixedCircleCropTransformation
import io.image.drawable.URLDrawable
import io.image.engine.ImageEngine
import io.image.entity.ImageDrawableSize
import io.image.entity.ImageParam
import kotlinx.coroutines.Dispatchers

/**
 * <pre>
 *   @author : leo
 *   @time : 2020/10/11
 *   @desc : Coil 图片加载引擎
 * </pre>
 */
class CoilImageEngine private constructor(val builder: Builder) : ImageEngine {
    private val loader: ImageLoader by lazy {
        createImageLoader(builder)
    }

    /**
     * 图片加载引擎配置类
     * @property isSupportGif Boolean
     * @property isSupportSvg Boolean
     * @property isSupportVideoFrame Boolean
     */
    class Builder(internal var context: Application) {
        internal var isSupportGif: Boolean = false
        internal var isSupportSvg: Boolean = false
        internal var isSupportVideoFrame: Boolean = false


        /**
         * 是否支持gif
         * @param support Boolean
         * @return Builder
         */
        fun supportGif(support: Boolean) = apply {
            this.isSupportGif = support
        }

        /**
         * 是否支持svg图片
         * @param support Boolean
         * @return Builder
         */
        fun supportSvg(support: Boolean) = apply {
            this.isSupportSvg = support
        }

        /**
         * 是否支持视频片段
         * @param support Boolean
         * @return Builder
         */
        fun supportVideoFrame(support: Boolean) = apply {
            this.isSupportVideoFrame = support
        }

        /**
         * create CoilImageEngine
         * @return CoilImageEngine
         */
        fun build(): CoilImageEngine = let {
            CoilImageEngine(this)
        }

    }


    /**
     * 图片加载单例对象
     * @return ImageLoader
     */
    private fun getImageLoader(param: ImageParam): ImageLoader = let {
        //todo 考虑支持自定义 ImageLoader 对象
        loader
    }


    /**
     * 创建图片加载引擎
     * @param builder Builder
     * @return ImageLoader
     */
    private fun createImageLoader(builder: Builder): ImageLoader = let {
        ImageLoader
                .Builder(builder.context)
                .components {
                    if (builder.isSupportSvg) {
                        add(SvgDecoder.Factory())
                    }
                    if (builder.isSupportVideoFrame) {
                        add(VideoFrameDecoder.Factory())
                    }
                    if (builder.isSupportGif) {
                        if (SDK_INT >= Build.VERSION_CODES.P) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                }
                .build()
    }


    /**
     * 下载图片
     * @param context Context?
     * @param param ImageParam
     * @return Bitmap?
     */
    override fun downloadImage(context: Context?, param: ImageParam): Bitmap? {
        if (param.url.isNullOrEmpty()) {
            return null
        }
        return context?.let { it ->
            val request = ImageRequest
                    .Builder(it)
                    .allowHardware(false)
                    .data(param.getData())
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .build()
            getImageLoader(param).executeBlocking(request).drawable?.let {
                val width = it.intrinsicWidth
                val height = it.intrinsicHeight
                it.setBounds(0, 0, width, height)
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                it.draw(canvas)
                bitmap
            }
        }
    }

    /**
     * 加载图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    override fun displaySquareImage(imageView: ImageView?, param: ImageParam) {
        displayImage(imageView, param) {
            dispatcher(Dispatchers.IO)
        }
    }

    /**
     * 加载圆形图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    override fun displayCircleImage(imageView: ImageView?, param: ImageParam) {
        displayImage(imageView, param) {
            transformations(FixedCircleCropTransformation())
            dispatcher(Dispatchers.IO)
        }
    }


    /**
     * 加载圆角图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    override fun displayRoundImage(imageView: ImageView?, param: ImageParam) {
        if (imageView?.visibility != View.VISIBLE) {
            return
        }
        displayImage(imageView, param) {
            transformations(transitionRoundedCorners(param))
            dispatcher(Dispatchers.IO)
        }
    }

    /**
     * 加载视频首帧预览图
     */
    override fun displayVideoFrame(imageView: ImageView?, param: ImageParam) {
        displayImage(imageView, param, true) {
            dispatcher(Dispatchers.IO)
        }
    }

    /**
     * 加载图片链接的原始尺寸
     *
     * @param imageView ImageView
     * @param url String 图片链接
     * @param callback Function1<Pair<Int, Int>, Unit> 宽度和高度的回调
     */
    override fun loadOriginalSize(context: Context, url: String, callback: (Pair<Int, Int>) -> Unit) {
        context.apply {
            ImageRequest
                .Builder(this)
                .data(url)
                .target { callback.invoke(it.intrinsicWidth to it.intrinsicHeight) }
                .let {
                    it.diskCachePolicy(CachePolicy.ENABLED)
                    it.memoryCachePolicy(CachePolicy.DISABLED)
                    it.networkCachePolicy(CachePolicy.ENABLED)
                    it.size(Size.ORIGINAL)
                }
                .build()
                .let {
                    loader.enqueue(it)
                }
        }
    }

    /**
     * 加载图片
     * @param imageView ImageView?
     * @param config ImageRequest.Builder
     */
    private fun displayImage(imageView: ImageView?, param: ImageParam, isVideo: Boolean = false, config: ImageRequest.Builder.() -> ImageRequest.Builder) {
        displayImage(imageView, getImageLoader(param), isVideo) {
            data(param.getData())
                    .error(param.getErrorDrawable())
                    .placeholder(param.getPlaceholderDrawable())
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .crossfade(param.crossFade)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)

            if (param.width > 0 && param.height > 0) {
                size(param.width, param.height)
            }
            config(this)
        }
    }


    /**
     * 加载图片
     * @param imageView ImageView?
     * @param config ImageRequest.Builder
     */
    private fun displayImage(imageView: ImageView?, imageLoader: ImageLoader, isVideo: Boolean = false, config: ImageRequest.Builder.() -> ImageRequest.Builder) {
        imageView?.context?.apply {
            ImageRequest
                    .Builder(this)
                    .target(imageView)
                    .let {
                        config(it)
                        it
                    }
                    .build()
                    .let {
                        imageLoader.enqueue(it)
                    }
        }
    }

    /**
     * 加载图片(自定义加载回调)
     *
     * @param context Context
     * @param param ImageParam
     * @param callback Function1<Drawable>
     */
    override fun displaySquareImage(context: Context,
                                    param: ImageParam,
                                    callback: (Drawable) -> Unit) {
        displayImage(context, callback, param) {
            dispatcher(Dispatchers.IO)
        }
    }

    /**
     * 加载图片(自定义加载回调)
     *
     * @param context Context
     * @param callback Function1<Drawable>
     * @param param ImageParam
     * @param isVideo Boolean
     * @param config ImageRequest.Builder
     */
    private fun displayImage(context: Context,
                             callback: ((Drawable) -> Unit),
                             param: ImageParam,
                             isVideo: Boolean = false,
                             config: ImageRequest.Builder.() -> ImageRequest.Builder) {
        displayImage(context, callback, getImageLoader(param), isVideo) {
            data(param.getData())
                    .error(param.getErrorDrawable())
                    .placeholder(param.getPlaceholderDrawable())
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .crossfade(param.crossFade)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
            if (param.width > 0 && param.height > 0) {
                size(param.width, param.height)
            }
            config(this)
        }
    }

    /**
     * 加载图片(自定义加载回调)
     *
     * @param context Context
     * @param callback Function1<Drawable>
     * @param imageLoader ImageLoader
     * @param isVideo Boolean
     * @param config ImageRequest.Builder
     */
    private fun displayImage(context: Context,
                             callback: ((Drawable) -> Unit),
                             imageLoader: ImageLoader,
                             isVideo: Boolean = false,
                             config: ImageRequest.Builder.() -> ImageRequest.Builder) {
        context.apply {
            ImageRequest
                    .Builder(this)
                    .target { callback.invoke(it) }
                    .let {
                        config(it)
                        it
                    }
                    .build()
                    .let {
                        imageLoader.enqueue(it)
                    }
        }
    }

    /**
     * 创建 RoundedCornersTransformation
     * @param param ImageParam
     * @return RoundedCornersTransformation
     */
    private fun transitionRoundedCorners(param: ImageParam): RoundedCornersTransformation = let {
        var topLeft: Float = 0f
        var topRight: Float = 0f
        var bottomLeft: Float = 0f
        var bottomRight: Float = 0f
        when (param.cornerType) {
            ImageEngine.CornerType.ALL -> {
                topLeft = param.radius
                topRight = param.radius
                bottomLeft = param.radius
                bottomRight = param.radius
            }
            ImageEngine.CornerType.TOP -> {
                topLeft = param.radius
                topRight = param.radius
            }
            ImageEngine.CornerType.TOP_LEFT -> {
                topLeft = param.radius
            }
            ImageEngine.CornerType.TOP_RIGHT -> {
                topRight = param.radius
            }
            ImageEngine.CornerType.BOTTOM -> {
                bottomLeft = param.radius
                bottomRight = param.radius
            }
            ImageEngine.CornerType.BOTTOM_LEFT -> {
                bottomLeft = param.radius
            }
            ImageEngine.CornerType.BOTTOM_RIGHT -> {
                bottomRight = param.radius
            }
            ImageEngine.CornerType.LEFT -> {
                topLeft = param.radius
                bottomLeft = param.radius
            }
            ImageEngine.CornerType.RIGHT -> {
                topRight = param.radius
                bottomRight = param.radius
            }
            else -> {}
        }
        RoundedCornersTransformation(topLeft, topRight, bottomLeft, bottomRight)
    }

    /**
     * 更新Html图片资源
     * @param textView TextView
     * @param data Drawable
     * @param target URLDrawable?
     * @param matchParentWidth Boolean
     */
    private fun updateHtmlDrawable(textView: TextView, data: Drawable, target: URLDrawable?, matchParentWidth: Boolean) {
        val size: ImageDrawableSize = calculateDrawableSize(textView, data, matchParentWidth)
        data.setBounds(0, 0, size.width, size.height)
        target!!.setBounds(0, 0, size.width, size.height)
        target.setDrawable(data)
        textView.text = textView.text
    }

    /**
     * 计算Drawable尺寸大小
     * @param textView TextView
     * @param drawable Drawable
     * @param matchParentWidth Boolean
     * @return ImageDrawableSize
     */
    private fun calculateDrawableSize(textView: TextView, drawable: Drawable, matchParentWidth: Boolean): ImageDrawableSize = let {
        val width: Int
        val height: Int
        if (matchParentWidth) {
            val viewWidth = textView.width
            val scale = viewWidth.toFloat() / drawable.intrinsicWidth
            width = (scale * drawable.intrinsicWidth).toInt()
            height = (scale * drawable.intrinsicHeight).toInt()
        } else {
            width = drawable.intrinsicWidth
            height = drawable.intrinsicHeight
        }
        ImageDrawableSize(width, height)
    }
}


