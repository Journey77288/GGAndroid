package io.ganguo.core.ui.bindingadapter.imageView

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import io.ganguo.core.ui.bindingadapter.base.BindingViewAdapter
import io.ganguo.image.core.ImageHelper.Companion.get
import io.ganguo.image.core.engine.ImageEngine
import io.ganguo.image.core.entity.ImageParam
import io.ganguo.utils.helper.ResHelper
import io.ganguo.utils.util.Files
import io.ganguo.utils.util.Strings
import java.io.File

/**
 * <pre>
 * author : leo
 * time   : 2018/05/29
 * desc   : 图片加载DataBinding工具类
</pre> *
 */
object BindingImageAdapter : BindingViewAdapter() {
    /**
     * xml绑定本地图片
     *
     * @param imageView
     * @param path      本地图片路径
     */
    @BindingAdapter(value = ["android:bind_image_file_path"])
    fun onBindImageFilePath(imageView: ImageView?, path: String?) {
        if (Strings.isEmpty(path) || !Files.checkFileExists(path)) {
            return
        }
        val param = ImageParam.Builder()
                .file(File(path))
                .build()
        get().getEngine().displayImage(imageView, param)
    }

    /**
     * xml绑定Bitmap资源
     *
     * @param imageView
     * @param bitmap    位图
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["android:bind_image_bitmap"])
    fun onBindBitmap(imageView: ImageView, bitmap: Bitmap?) {
        if (bitmap == null) {
            return
        }
        imageView.setImageBitmap(bitmap)
    }

    /**
     * xml绑定drawable xml资源
     *
     * @param imageView
     * @param src       Drawable目录下的selector、shape图片资源Id
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["android:bind_drawable_xml_res"])
    fun onBindDrawableXmlRes(imageView: ImageView, @DrawableRes src: Int) {
        if (src == 0 || src == -1) {
            return
        }
        imageView.setImageResource(src)
    }

    /**
     * xml绑定加载图片
     *
     * @param imageView
     * @param src       Drawable目录下图片资源Id
     */
    @BindingAdapter(value = ["android:bind_drawable_res"])
    fun onBindImageDrawableRes(imageView: ImageView?, @DrawableRes src: Int) {
        if (src == 0 || src == -1) {
            return
        }
        val param = ImageParam.Builder()
                .url(ResHelper.getResourcesPath(src))
                .build()
        get().getEngine().displayImage(imageView, param)
    }

    /**
     * xml绑定加载图片
     *
     * @param imageView
     * @param url              url链接
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["android:bind_image_url", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder"], requireAll = false)
    fun onBindImageUrl(imageView: ImageView?, url: String?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
        if (Strings.isEmpty(url)) {
            return
        }
        val param = ImageParam.Builder()
                .url(url!!)
                .error(errorPlaceHolder)
                .placeholder(placeHolder)
                .build()
        get().getEngine().displayImage(imageView, param)
    }

    /**
     * xml绑定加载圆形图片
     *
     * @param imageView
     * @param url              url链接
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    @BindingAdapter(value = ["android:bind_image_circleUrl", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder"], requireAll = false)
    fun onBindCircleImage(imageView: ImageView?, url: String?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
        val param = ImageParam.Builder(ImageEngine.ImageType.CIRCLE)
                .url(url!!)
                .placeholder(placeHolder)
                .error(errorPlaceHolder)
                .build()
        get().getEngine().displayImage(imageView, param)
    }

    /**
     * xml绑定加载圆角图片
     *
     * @param imageView
     * @param url              url链接
     * @param radius           圆角大小
     * @param placeHolder      占位图
     * @param errorPlaceHolder 加载错误占位图
     */
    @BindingAdapter(value = ["android:bind_image_roundUrl", "android:bind_image_radius", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder", "android:bind_image_corner_type"], requireAll = false)
    fun onBindRoundImage(imageView: ImageView?,
                         url: String?,
                         radius: Float,
                         placeHolder: Drawable?,
                         errorPlaceHolder: Drawable?,
                         cornerType: ImageEngine.CornerType?) {
        val param = ImageParam.Builder(ImageEngine.ImageType.ROUND)
                .url(url!!)
                .radius(radius.toInt())
                .placeholder(placeHolder)
                .error(errorPlaceHolder)
                .cornerType(cornerType)
                .build()
        get().getEngine().displayImage(imageView, param)
    }

    /**
     * xml绑定加载圆角图片
     *
     * @param imageView
     * @param url              url链接
     * @param radius           圆角大小
     * @param placeHolder      占位图
     * @param errorPlaceHolder 加载错误占位图
     * @param heightRes        图片高度资源Id
     * @param widthRes         图片宽度资源Id
     */
    @BindingAdapter(value = ["android:bind_image_fixed_roundUrl", "android:bind_image_radius", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder", "android:bind_image_corner_type", "android:bind_image_width_res", "android:bind_image_height_res"], requireAll = false)
    @SuppressLint("ResourceType")
    fun onBindFixedRoundImage(imageView: ImageView?,
                              url: String?,
                              radius: Float,
                              placeHolder: Drawable?,
                              errorPlaceHolder: Drawable?,
                              cornerType: ImageEngine.CornerType?, @DimenRes widthRes: Int, @DimenRes heightRes: Int) {
        val width = if (widthRes > 0) ResHelper.getDimensionPixelOffsets(widthRes) else -1
        val height = if (heightRes > 0) ResHelper.getDimensionPixelOffsets(heightRes) else -1
        val param = ImageParam.Builder(ImageEngine.ImageType.ROUND)
                .url(url!!)
                .radius(radius.toInt())
                .placeholder(placeHolder)
                .error(errorPlaceHolder)
                .cornerType(cornerType)
                .width(width)
                .height(height)
                .build()
        get().getEngine().displayImage(imageView, param)
    }
}