package io.ganguo.image.glide.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.ganguo.image.core.drawable.URLDrawable
import io.ganguo.image.core.engine.ImageEngine
import io.ganguo.image.core.entity.ImageParam
import io.ganguo.image.glide.EngineHelper
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
    override fun resumeLoadImage(context: Context?) {
        if (context != null) {
            Glide.with(context).resumeRequests()
        }
    }

    override fun pauseLoadImage(context: Context?) {
        if (context != null) {
            Glide.with(context).pauseRequests()
        }
    }

    /**
     * 配合TextView、URLImageGetter 加载Html中的图片
     *
     * @param url              资源链接
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 错误占位图
     * @param drawable         目标drawable
     * @param matchParentWidth 是否撑满控件宽度
     * @param textView         目标TextView
     */
    override fun displayImageDrawable(textView: TextView?, param: ImageParam, drawable: URLDrawable?, matchParentWidth: Boolean) {
        val options = getRequestOptions(param.getPlaceholderDrawable(), param.getErrorDrawable())
        Glide.with(textView!!)
                .load(param.getUrl())
                .apply(options)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        val width: Int
                        val height: Int
                        if (matchParentWidth) {
                            val viewWidth = textView.width
                            val scale = viewWidth.toFloat() / resource.intrinsicWidth
                            width = (scale * resource.intrinsicWidth).toInt()
                            height = (scale * resource.intrinsicHeight).toInt()
                        } else {
                            width = resource.intrinsicWidth
                            height = resource.intrinsicHeight
                        }
                        resource.setBounds(0, 0, width, height)
                        drawable!!.setBounds(0, 0, width, height)
                        drawable.setDrawable(resource)
                        textView.text = textView.text
                    }
                })
    }

    /**
     * 默认加载图片 - 根据参数类型选择对应的加载方式
     * @param imageView ImageView?
     * @param param ImageParam
     */
    override fun displayImage(imageView: ImageView?, param: ImageParam) {
        when (param.type) {
            ImageEngine.ImageType.CIRCLE -> {
                displayImageUrlCircle(imageView, param)
            }
            ImageEngine.ImageType.ROUND -> {
                displayImageUrlRound(imageView, param)
            }
            else -> {
                displayImageUrl(imageView, param)
            }
        }
    }

    /**
     * 加载图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    private fun displayImageUrl(imageView: ImageView?, param: ImageParam) {
        displayImageResource(imageView, param.getUrl(), getRequestOptions(param.getPlaceholderDrawable(), param.getErrorDrawable()), param.width, param.height)
    }

    /**
     * 加载圆形图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    private fun displayImageUrlCircle(imageView: ImageView?, param: ImageParam) {
        val options = getRequestOptions(param.getPlaceholderDrawable(), param.getErrorDrawable())
        options.circleCrop()
        displayImageResource(imageView, param.getUrl(), options)
    }

    /**
     * 加载圆角图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    private fun displayImageUrlRound(imageView: ImageView?, param: ImageParam) {
        val options = getRequestOptions(param.getPlaceholderDrawable(), param.getErrorDrawable())
        options.transform(CenterCrop(), RoundedCornersTransformation(param.radius, 0, EngineHelper.transitionGlideCornerType(param.getCornerType())))
        displayImageResource(imageView, param.getUrl(), options, param.width, param.height)
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
                    .load(param.getUrl())
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
     * 加载图片资源
     *
     * @param imageView
     * @param resource       图片资源
     * @param requestOptions 加载图片配置
     */
    private fun displayImageResource(imageView: ImageView?, resource: Any?, requestOptions: RequestOptions, width: Int = 0, height: Int = 0) {
        var builder = Glide.with(imageView!!)
                .load(resource)
                .apply(requestOptions)
        if (width > 0 && height > 0) {
            builder.override(width, height)
        }
        builder.into(imageView)
    }

    /**
     * create RequestOptions
     *
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    private fun getRequestOptions(placeHolder: Drawable?, errorPlaceHolder: Drawable?): RequestOptions {
        val options = RequestOptions()
        options.placeholder(placeHolder)
                .error(errorPlaceHolder)
                .sizeMultiplier(1f)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        return options
    }
}