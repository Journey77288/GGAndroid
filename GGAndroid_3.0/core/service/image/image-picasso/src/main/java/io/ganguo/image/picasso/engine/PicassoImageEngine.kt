package io.ganguo.image.picasso.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.ganguo.image.core.drawable.URLDrawable
import io.ganguo.image.core.engine.ImageEngine
import io.ganguo.image.core.entity.ImageParam
import io.ganguo.image.picasso.EngineHelper.transitionPicassoCornerType
import io.ganguo.image.picasso.target.BitmapTarget
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.IOException


/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/21
 *     desc   :  Picasso图片加载引擎
 * </pre>
 */
class PicassoImageEngine : ImageEngine {

    override fun resumeLoadImage(context: Context?) {
        if (context != null) {
            Picasso.get().resumeTag(PICASSO_LOAD_TAG)
        }
    }

    override fun pauseLoadImage(context: Context?) {
        if (context != null) {
            Picasso.get().pauseTag(PICASSO_LOAD_TAG)
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
        var bitmapTarget = object : BitmapTarget() {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                super.onBitmapLoaded(bitmap, from)
                protectedFromGarbageCollectorTargets.remove(this)
                setBitmapToTextView(textView!!, bitmap, drawable!!, matchParentWidth)
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
                super.onBitmapFailed(e, errorDrawable)
                protectedFromGarbageCollectorTargets.remove(this)
            }

        }
        protectedFromGarbageCollectorTargets.add(bitmapTarget)

        Picasso.get()
                .load(param.getUrl())
                .placeholder(param.getPlaceholderDrawable())
                .error(param.getErrorDrawable())
                .noFade()
                .into(bitmapTarget)
    }

    /**
     * 设置Bitmap位图到TextView
     *
     * @param drawable         目标drawable
     * @param matchParentWidth 是否撑满控件宽度
     * @param textView         目标TextView
     */
    private fun setBitmapToTextView(textView: TextView, bitmap: Bitmap, drawable: URLDrawable, matchParentWidth: Boolean) {
        val bitmapDrawable: Drawable = BitmapDrawable(textView.resources, bitmap)
        val width: Int
        val height: Int
        if (matchParentWidth) {
            val sale = textView.width.toFloat() / bitmap.width
            width = (sale * bitmap.width).toInt()
            height = (sale * bitmap.height).toInt()
        } else {
            width = bitmap.width
            height = bitmap.height
        }
        bitmapDrawable.setBounds(0, 0, width, height)
        drawable.setBounds(0, 0, width, height)
        drawable.setDrawable(bitmapDrawable)
        textView.text = textView.text
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
        val creator = Picasso.get()
                .load(param.getUrl())
                .placeholder(param.getPlaceholderDrawable())
                .error(param.getErrorDrawable())
                .tag(PICASSO_LOAD_TAG)
        if (param.height > 0 && param.width > 0) {
            creator.resize(param.width, param.height)
            creator.centerCrop()
        }
        creator.into(imageView)
    }

    /**
     * 加载圆形图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    private fun displayImageUrlCircle(imageView: ImageView?, param: ImageParam) {
        val creator = Picasso.get()
                .load(param.getUrl())
                .placeholder(param.getPlaceholderDrawable())
                .error(param.getErrorDrawable())
                .transform(CropCircleTransformation())
                .noFade()
                .tag(PICASSO_LOAD_TAG)
        creator.into(imageView)
    }

    /**
     * 加载圆角图片
     * @param imageView ImageView?
     * @param param ImageParam
     */
    private fun displayImageUrlRound(imageView: ImageView?, param: ImageParam) {
        if (imageView?.visibility != View.VISIBLE) {
            return
        }
        imageView.post {
            var roundType = transitionPicassoCornerType(param.getCornerType())
            val creator = Picasso.get()
                    .load(param.getUrl())
                    .placeholder(param.getPlaceholderDrawable())
                    .error(param.getErrorDrawable())
                    .centerCrop(Gravity.CENTER)
                    .resize(imageView.measuredWidth, imageView.measuredHeight)
                    .transform(RoundedCornersTransformation(param.radius, 0, roundType))
                    .tag(PICASSO_LOAD_TAG)
            if (param.width > 0 && param.height > 0) {
                creator.resize(param.width, param.height)
            } else {
                creator.resize(imageView.measuredWidth, imageView.measuredHeight)
            }
            creator.into(imageView)
        }
    }

    /**
     * 下载图片
     * @param context Context?
     * @param param ImageParam
     * @return Bitmap?
     */
    override fun downloadImage(context: Context?, param: ImageParam): Bitmap? {
        try {
            val creator = Picasso.get().load(param.getUrl())
            if (param.height > 0 && param.width > 0) {
                creator.resize(param.width, param.height)
            }
            return creator.get()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        private const val PICASSO_LOAD_TAG = "picasso_load_tag"
        private var protectedFromGarbageCollectorTargets: HashSet<Target> = HashSet()

    }
}