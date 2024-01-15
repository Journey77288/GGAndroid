package io.ganguo.library.core.image;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import io.ganguo.library.R;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.util.Strings;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * <p>
 * Picasso 图片加载工具类
 * </p>
 * Created by leo on 2018/10/26.
 */
public class PhotoPicassoLoader {
    public static final String PICASSO_LOAD_TAG = "picasso_load_tag";


    /**
     * Picasso init Config
     */
    public static void init(boolean isDebug) {
        Picasso.get().setIndicatorsEnabled(isDebug);
        Picasso.get().setLoggingEnabled(isDebug);
    }


    /**
     * function：加载DrawableRes图片资源
     *
     * @param imageView
     * @param imageResource    图片资源
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    @SuppressLint("ResourceType")
    public static void displayImage(final ImageView imageView, @DrawableRes int imageResource, Drawable placeHolder, Drawable errorPlaceHolder) {
        if (imageResource <= 0) {
            imageResource = R.color.transparent;
        }
        Picasso.get()
                .load(imageResource)
                .placeholder(placeHolder == null ? ResHelper.getDrawable(R.color.transparent) : placeHolder)
                .error(errorPlaceHolder == null ? ResHelper.getDrawable(R.color.transparent) : errorPlaceHolder)
                .tag(PICASSO_LOAD_TAG)
                .into(imageView);
    }


    /**
     * function：加载图片资源
     *
     * @param imageView
     * @param imageFile        图片文件
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    public static void displayImage(final ImageView imageView, File imageFile, Drawable placeHolder, Drawable errorPlaceHolder) {
        Picasso.get()
                .load(imageFile)
                .placeholder(placeHolder == null ? ResHelper.getDrawable(R.color.transparent) : placeHolder)
                .error(errorPlaceHolder == null ? ResHelper.getDrawable(R.color.transparent) : errorPlaceHolder)
                .tag(PICASSO_LOAD_TAG)
                .into(imageView);
    }


    /**
     * function：加载图片资源
     *
     * @param imageView
     * @param imageResource    图片资源链接
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     * @param callback         加载结果回调
     */
    public static void displayImage(final ImageView imageView, String imageResource, Drawable placeHolder, Drawable errorPlaceHolder, Callback callback) {
        if (Strings.isEmpty(imageResource)) {
            imageResource = "http:xxx";
        }
        Picasso.get()
                .load(imageResource)
                .placeholder(placeHolder == null ? ResHelper.getDrawable(R.color.transparent) : placeHolder)
                .error(errorPlaceHolder == null ? ResHelper.getDrawable(R.color.transparent) : errorPlaceHolder)
                .tag(PICASSO_LOAD_TAG)
                .into(imageView, callback);
    }


    /**
     * function：加载圆形图片，一般用于圆形头像加载
     *
     * @param imageView
     * @param url              图片资源(ur/res/本地path都可以)
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     */
    public static void displayCircleImage(final ImageView imageView, String url, Drawable placeHolder, Drawable errorPlaceHolder) {
        if (Strings.isEmpty(url)) {
            url = "http:xxx";
        }
        Picasso.get()
                .load(url)
                .placeholder(placeHolder == null ? ResHelper.getDrawable(R.color.transparent) : placeHolder)
                .error(errorPlaceHolder == null ? ResHelper.getDrawable(R.color.transparent) : errorPlaceHolder)
                .transform(new CropCircleTransformation())
                .noFade()
                .tag(PICASSO_LOAD_TAG)
                .into(imageView);
    }


    /**
     * function：加载圆角图片
     *
     * @param imageView
     * @param url              图片资源(ur/res/本地path都可以)
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     * @param cornerType       圆角范围
     * @param radius           圆角大小
     */
    public static void displayRoundImage(final ImageView imageView, String url, int radius, jp.wasabeef.picasso.transformations.RoundedCornersTransformation.CornerType cornerType, Drawable placeHolder, Drawable errorPlaceHolder) {
        if (Strings.isEmpty(url)) {
            url = "http://xxx";
        }
        if (cornerType == null) {
            cornerType = RoundedCornersTransformation.CornerType.ALL;
        }
        Picasso.get()
                .load(url)
                .placeholder(placeHolder == null ? ResHelper.getDrawable(R.color.transparent) : placeHolder)
                .error(errorPlaceHolder == null ? ResHelper.getDrawable(R.color.transparent) : errorPlaceHolder)
                .transform(new jp.wasabeef.picasso.transformations.RoundedCornersTransformation(radius, 0, cornerType))
                .noFade()
                .tag(PICASSO_LOAD_TAG)
                .into(imageView);
    }


    /**
     * function：兼容加载ScaleType为CenterCrop的圆角图片
     *
     * @param imageView
     * @param url              图片资源(ur/res/本地path都可以)
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     * @param cornerType       圆角范围
     * @param radius           圆角大小
     */
    public static void displayFixedRoundImage(final ImageView imageView, String url, int radius, RoundedCornersTransformation.CornerType cornerType, Drawable placeHolder, Drawable errorPlaceHolder, int width, int height) {
        if (Strings.isEmpty(url)) {
            url = "http://xxx";
        }
        if (cornerType == null) {
            cornerType = RoundedCornersTransformation.CornerType.ALL;
        }
        RequestCreator creator = Picasso.get()
                .load(url)
                .placeholder(placeHolder == null ? ResHelper.getDrawable(R.color.transparent) : placeHolder)
                .error(errorPlaceHolder == null ? ResHelper.getDrawable(R.color.transparent) : errorPlaceHolder)
                .transform(new jp.wasabeef.picasso.transformations.RoundedCornersTransformation(radius, 0, cornerType))
                .noFade()
                .tag(PICASSO_LOAD_TAG);
        if (width > 0 && height > 0) {
            creator.centerCrop();
            creator.centerCrop(Gravity.BOTTOM);
            creator.resize(width, height);
        }
        creator.into(imageView);

    }


    /**
     * function：支持设置图片大小的加载图片函数
     *
     * @param imageView
     * @param url              图片资源(ur/res/本地path都可以)
     * @param placeHolder      加载占位图
     * @param errorPlaceHolder 加载失败占位图
     * @param height           目标高度
     * @param width            目标宽度
     */
    public static void displaySizeImage(final ImageView imageView, String url, Drawable placeHolder, Drawable errorPlaceHolder, int width, int height) {
        if (Strings.isEmpty(url)) {
            url = "http://xxx";
        }
        RequestCreator creator = Picasso.get()
                .load(url)
                .placeholder(placeHolder == null ? ResHelper.getDrawable(R.color.transparent) : placeHolder)
                .error(errorPlaceHolder == null ? ResHelper.getDrawable(R.color.transparent) : errorPlaceHolder)
                .tag(PICASSO_LOAD_TAG)
                .noFade();
        if (height > 0 && width > 0) {
            creator.resize(width, height);
            creator.centerCrop();
        }
        creator.into(imageView);
    }

}
