package io.ganguo.library.ui.bindingadapter.imageView;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import java.io.File;

import io.ganguo.library.core.image.PhotoPicassoLoader;
import io.ganguo.library.ui.bindingadapter.base.BindingViewAdapter;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.util.Files;
import io.ganguo.utils.util.Strings;

/**
 * function：图片加载DataBinding工具类
 * update by leo on 2018/05/29.
 */
public class BindingImageAdapter extends BindingViewAdapter {


    /**
     * function：xml绑定本地图片
     *
     * @param imageView
     * @param path      本地图片路径
     */
    @BindingAdapter(value = {"android:bind_image_file_path"})
    public static void onBindImageFilePath(ImageView imageView, String path) {
        if (Strings.isEmpty(path) || !Files.checkFileExists(path)) {
            return;
        }
        PhotoPicassoLoader.displayImage(imageView, new File(path), null, null);
    }


    /**
     * function：xml绑定Bitmap资源
     *
     * @param imageView
     * @param bitmap    位图
     */
    @BindingAdapter(value = {"android:bind_image_bitmap"})
    public static void onBindBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

    /**
     * function：xml绑定drawable xml资源
     *
     * @param imageView
     * @param src       Drawable目录下的selector、shape图片资源Id
     */
    @BindingAdapter(value = {"android:bind_drawable_xml_res"})
    public static void onBindDrawableXmlRes(ImageView imageView, @DrawableRes int src) {
        if (src == 0 || src == -1) {
            return;
        }
        imageView.setImageResource(src);
    }

    /**
     * function：xml绑定加载图片
     *
     * @param imageView
     * @param src       Drawable目录下图片资源Id
     */
    @BindingAdapter(value = {"android:bind_drawable_res"})
    public static void onBindImageDrawableRes(ImageView imageView, @DrawableRes int src) {
        if (src == 0 || src == -1) {
            return;
        }
        PhotoPicassoLoader.displayImage(imageView, src, null, null);
    }


    /**
     * function：xml绑定加载图片
     *
     * @param imageView
     * @param url              url链接
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    @BindingAdapter(value = {"android:bind_image_url", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder"}, requireAll = false)
    public static void onBindImageUrl(ImageView imageView, String url, Drawable placeHolder, Drawable errorPlaceHolder) {
        PhotoPicassoLoader.displayImage(imageView, url, placeHolder, errorPlaceHolder, null);
    }


    /**
     * function：xml绑定加载圆形图片
     *
     * @param imageView
     * @param url              url链接
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    @BindingAdapter(value = {"android:bind_image_circleUrl", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder"}, requireAll = false)
    public static void onBindCircleImage(ImageView imageView, String url, Drawable placeHolder, Drawable errorPlaceHolder) {
        PhotoPicassoLoader.displayCircleImage(imageView, url, placeHolder, errorPlaceHolder);
    }


    /**
     * function: xml绑定加载圆角图片
     *
     * @param imageView
     * @param url              url链接
     * @param radius           圆角大小
     * @param placeHolder      占位图
     * @param errorPlaceHolder 加载错误占位图
     */
    @BindingAdapter(value = {"android:bind_image_roundUrl", "android:bind_image_radius", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder", "android:bind_image_corner_type"}, requireAll = false)
    public static void onBindRoundImage(ImageView imageView,
                                        String url,
                                        float radius,
                                        Drawable placeHolder,
                                        Drawable errorPlaceHolder,
                                        jp.wasabeef.picasso.transformations.RoundedCornersTransformation.CornerType cornerType) {
        PhotoPicassoLoader.displayRoundImage(imageView, url, (int) radius, cornerType, placeHolder, errorPlaceHolder);
    }

    /**
     * function: xml绑定加载圆角图片
     *
     * @param imageView
     * @param url              url链接
     * @param radius           圆角大小
     * @param placeHolder      占位图
     * @param errorPlaceHolder 加载错误占位图
     * @param heightRes        图片高度资源Id
     * @param widthRes         图片宽度资源Id
     */
    @BindingAdapter(value = {"android:bind_image_fixed_roundUrl", "android:bind_image_radius", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder", "android:bind_image_corner_type", "android:bind_image_width_res", "android:bind_image_height_res"}, requireAll = false)
    @SuppressLint("ResourceType")
    public static void onBindFixedRoundImage(ImageView imageView,
                                             String url,
                                             float radius,
                                             Drawable placeHolder,
                                             Drawable errorPlaceHolder,
                                             jp.wasabeef.picasso.transformations.RoundedCornersTransformation.CornerType cornerType, @DimenRes int widthRes, @DimenRes int heightRes) {
        int width = widthRes > 0 ? ResHelper.getDimensionPixelOffsets(widthRes) : -1;
        int height = heightRes > 0 ? ResHelper.getDimensionPixelOffsets(heightRes) : -1;
        PhotoPicassoLoader.displayFixedRoundImage(imageView, url, (int) radius, cornerType, placeHolder, errorPlaceHolder, width, height);
    }


    /**
     * function：xml绑定加载圆形图片 - 自定图片大小
     *
     * @param imageView
     * @param url              url链接
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     * @param heightRes        图片高度资源Id
     * @param widthRes         图片宽度资源Id
     */
    @BindingAdapter(value = {"android:bind_image_sizeUrl", "android:bind_image_placeHolder", "android:bind_image_error_placeHolder", "android:bind_image_width_res", "android:bind_image_height_res"}, requireAll = false)
    @SuppressLint("ResourceType")
    public static void onBindCircleImage(ImageView imageView, String url, Drawable placeHolder, Drawable errorPlaceHolder, @DimenRes int widthRes, @DimenRes int heightRes) {
        int width = widthRes > 0 ? ResHelper.getDimensionPixelOffsets(widthRes) : -1;
        int height = heightRes > 0 ? ResHelper.getDimensionPixelOffsets(heightRes) : -1;
        PhotoPicassoLoader.displaySizeImage(imageView, url, placeHolder, errorPlaceHolder, width, height);
    }

}
