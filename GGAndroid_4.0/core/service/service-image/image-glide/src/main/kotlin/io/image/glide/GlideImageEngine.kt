package io.image.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.image.engine.ImageEngine
import io.image.entity.ImageParam
import io.image.glide.entity.ImageSize
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.util.concurrent.ExecutionException

/**
 * <pre>
 * author : leo
 * time   : 2019/03/22
 * desc   : Glide 加载图片引擎
 * </pre>
 */
class GlideImageEngine : ImageEngine {

    /**
     * 恢复图片加载
     * @param context Context?
     */
    override fun resumeLoadImage(context: Context?) {
        if (context != null) {
            Glide.with(context).resumeRequests()
        }
    }


    /**
     * 暂停图片加载
     * @param context Context?
     */
    override fun pauseLoadImage(context: Context?) {
        if (context != null) {
            Glide.with(context).pauseRequests()
        }
    }


    /**
     * 加载图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    override fun displaySquareImage(imageView: ImageView?, param: ImageParam) {
        displayImage(imageView, param) {
            override(param.width, param.height)
        }
    }

    /**
     * 加载图片(自定义加载回调)
     */
    override fun displaySquareImage(context: Context, param: ImageParam, callback: (Drawable) -> Unit) {
        displayImage(context, callback, param) {
            override(param.width, param.height)
        }
    }

    /**
     * 加载圆形图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    override fun displayCircleImage(imageView: ImageView?, param: ImageParam) {
        displayImage(imageView, param) {
            circleCrop()
        }
    }

    /**
     * 加载圆角图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    override fun displayRoundImage(imageView: ImageView?, param: ImageParam) {
        displayImage(imageView, param) {
            transform(CenterCrop(), RoundedCornersTransformation(param.radius.toInt(), 0, EngineHelper.transitionGlideCornerType(param.cornerType)))
        }
    }

    /**
     * 显示视频首帧
     */
    override fun displayVideoFrame(imageView: ImageView?, param: ImageParam) {
        imageView?.apply {
            var builder = Glide.with(imageView)
                    .load(param.uri)
                    .apply {
                        override(param.width, param.height)
                    }
            if (width > 0 && height > 0) {
                builder.override(width, height)
            }
            builder.into(imageView)
        }
    }

    /**
     * 加载图片原始尺寸
     *
     * @param context Context
     * @param url String
     * @param callback Function1<Pair<Int, Int>, Unit>
     */
    override fun loadOriginalSize(context: Context, url: String, callback: (Pair<Int, Int>) -> Unit) {
        Glide.with(context)
            .`as`(ImageSize::class.java)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .skipMemoryCache(true)
            .into(object: SimpleTarget<ImageSize>() {
                override fun onResourceReady(resource: ImageSize, transition: Transition<in ImageSize>?) {
                    callback.invoke(resource.width to resource.height)
                }
            })
    }


    /**
     * 下载图片
     * @param context Context?
     * @param param ImageParam
     * @return Bitmap?
     */
    override fun downloadImage(context: Context?, param: ImageParam): Bitmap? {
        try {
            val builder = Glide
                    .with(context!!)
                    .asBitmap()
                    .load(param.url)
            val futureTarget: FutureTarget<Bitmap>
            futureTarget = if (param.width > 0 && param.height > 0) {
                builder.submit(param.width, param.height)
            } else {
                builder.submit()
            }
            return futureTarget.get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 加载图片
     * @param imageView ImageView?
     * @param param ImageParam
     * @param options [@kotlin.ExtensionFunctionType] Function1<RequestOptions, RequestOptions>
     */
    private fun displayImage(imageView: ImageView?, param: ImageParam, options: RequestOptions.() -> RequestOptions) {
        imageView?.apply {
            displayImage(imageView, param.getData()) {
                if (!param.crossFade) {
                    dontAnimate()
                }
                error(param.getErrorDrawable())
                placeholder(param.getPlaceholderDrawable())
                when {
                    param.isLocalResource() -> {// 本地资源缓存Resource
                        diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    }
                    param.isDrawableResource() -> {// Drawable图片资源不进行缓存，由Glide计算图片尺寸，避免图片加载空白情况
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                    }
                }
                options(this)
            }
        }
    }


    /**
     * 加载图片
     * @param imageView ImageView?
     * @param resource Any?
     * @param options [@kotlin.ExtensionFunctionType] Function1<RequestOptions, RequestOptions>
     */
    private fun displayImage(imageView: ImageView?, resource: Any?, options: RequestOptions.() -> RequestOptions) {
        imageView?.apply {
            if (resource is String && resource.endsWith("gif")) {
                Glide.with(imageView)
                        .load(resource)
                        .centerInside()
            } else {
                Glide.with(imageView)
                        .load(resource)
            }.apply {
                apply(options(getBaseOptions()))
                into(imageView)
            }
        }
    }

    /**
     * 加载图片(自定义加载回调)
     *
     * @param context Context
     * @param callback Function1<Drawable>
     * @param param ImageParam
     * @param options [@kotlin.ExtensionFunctionType] Function1<RequestOptions, RequestOptions>
     */
    private fun displayImage(context: Context, callback: (Drawable) -> Unit, param: ImageParam, options: RequestOptions.() -> RequestOptions) {
        context.apply {
            displayImage(context, callback, param.getData()) {
                if (!param.crossFade) {
                    dontAnimate()
                }
                error(param.getErrorDrawable())
                placeholder(param.getPlaceholderDrawable())
                when {
                    param.isLocalResource() -> {// 本地资源缓存Resource
                        diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    }
                    param.isDrawableResource() -> {// Drawable图片资源不进行缓存，由Glide计算图片尺寸，避免图片加载空白情况
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                    }
                }
                options(this)
            }
        }
    }


    /**
     * 加载图片(自定义加载回调)
     *
     * @param context Context
     * @param callback Function1<Drawable>
     * @param resource Any?
     * @param options [@kotlin.ExtensionFunctionType] Function1<RequestOptions, RequestOptions>
     */
    private fun displayImage(context: Context, callback: (Drawable) -> Unit, resource: Any?, options: RequestOptions.() -> RequestOptions) {
        context.apply {
            if (resource is String && resource.endsWith("gif")) {
                Glide.with(context)
                        .load(resource)
                        .centerInside()
            } else {
                Glide.with(context)
                        .load(resource)
            }.apply {
                apply(options(getBaseOptions()))
                into(object: SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        callback.invoke(resource)
                    }
                })
            }
        }
    }


    /**
     * Base RequestOptions
     * @return RequestOptions
     */
    private fun getBaseOptions(): RequestOptions {
        val options = RequestOptions()
        options
                .sizeMultiplier(1f)
                .skipMemoryCache(false)
        return options
    }
}
