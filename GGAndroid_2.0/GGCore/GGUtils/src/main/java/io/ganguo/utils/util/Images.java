package io.ganguo.utils.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

import io.ganguo.utils.bean.Globals;
import io.ganguo.utils.util.Intents;
import io.ganguo.utils.util.log.Logger;

/**
 * <p>
 * 图片/照片相关工具类
 * </p>
 * Created by leo on 2018/7/6 上午11:36
 */
public class Images {
    private Images() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    private static final int OUTPUT_SIZE_SMALL = 50;
    private static final int OUTPUT_SIZE_MIDDLE = 150;
    private static final int OUTPUT_SIZE_LARGE = 500;


    /**
     * 将图片文件进行缩放、旋转、压缩
     *
     * @param from
     * @param to
     * @param outSize
     * @return
     */
    public static File easyCompress(File from, File to, int outSize) {
        Bitmap scaledBitmap = Bitmaps.uniformScale(from);
        Bitmap rotatedBitmap = Bitmaps.rotateToNormal(scaledBitmap, from.getAbsolutePath());
        Bitmaps.compress(rotatedBitmap != null ? rotatedBitmap : scaledBitmap, to, outSize);

        Logger.tag("compress")
                .i("scaledBitmap Width :" + scaledBitmap.getWidth() +
                        " scaledBitmap Height : " + scaledBitmap.getHeight());
        Logger.tag("compress")
                .i("result path:" + to.getPath() + " result size: " + to.length() / 1024);
        return to;
    }

    /**
     * 将图片缩放压缩成小于50kb
     * 用于质量要求不高的情况（头像）
     *
     * @param from
     * @param to
     * @return
     */
    public static File easyCompressInSmallSize(File from, File to) {
        return easyCompress(from, to, OUTPUT_SIZE_SMALL);
    }

    /**
     * 将图片缩放压缩成小于150kb
     * 用于大多数情况
     *
     * @param from
     * @param to
     * @return
     */
    public static File easyCompressInMidSize(File from, File to) {
        return easyCompress(from, to, OUTPUT_SIZE_MIDDLE);
    }

    /**
     * 将图片缩放压缩成小于500kb
     * 用于对图片质量要求高的情况
     *
     * @param from
     * @param to
     * @return
     */
    public static File easyCompressInLargeSize(File from, File to) {
        return easyCompress(from, to, OUTPUT_SIZE_LARGE);
    }

    /**
     * 裁剪图片
     *
     * @param activity
     * @param from
     * @param to
     * @param outputXY
     * @return
     */
    public static File cropImage(Activity activity, File from, File to, int outputXY, int requestCode) {
        Intent intent = Intents.createCropImgIntent(from, to, outputXY);
        activity.startActivityForResult(intent, requestCode);
        return to;
    }

    /**
     * 裁剪图片
     *
     * @param fragment
     * @param from
     * @param to
     * @param outputXY
     * @return
     */
    public static File cropImage(Fragment fragment, File from, File to, int outputXY, int requestCode) {
        return cropImage(fragment.getActivity(), from, to, outputXY, requestCode);
    }


    /**
     * 拍照取缩略图
     * <p>
     * 回调处 getExtras().getParcelable("data")
     * 可以取得缩略图的Bitmap
     *
     * @param activity 请求拍照的代码,不指定Uri，避免返回data值为null
     */
    public static void takePhotoThumb(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.setResult(Activity.RESULT_OK);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 拍照取缩略图
     * <p>
     * 回调处 getExtras().getParcelable("data")
     * 可以取得缩略图的Bitmap
     *
     * @param fragment 请求拍照的代码,不指定Uri，避免返回data值为null
     */
    public static void takePhotoThumb(Fragment fragment, int requestCode) {
        takePhotoThumb(fragment.getActivity(), requestCode);
    }

    /**
     * 拍照
     * <p>
     * 在回调处 ContentResolver.openOutputStream(uri)
     * 用这个方法把拍好的照片写入到你想要的文件内再使用
     *
     * @param activity
     * @param requestCode
     * @return Uri
     */
    public static Uri takePhoto(Activity activity, int requestCode) {
        Uri uri = Intents.createImageUri(activity);
        takePhoto(activity, uri, requestCode);
        return uri;
    }

    /**
     * 拍照
     * <p>
     * 在回调处 ContentResolver.openOutputStream(uri)
     * 用这个方法把拍好的照片写入到你想要的文件内再使用
     *
     * @param activity
     * @param requestCode
     * @return Uri
     */
    public static Uri takePhoto(Activity activity, Uri uri, int requestCode) {
        Intent intent = Intents.createTakePhotoIntent(uri);
        activity.startActivityForResult(intent, requestCode);
        return uri;
    }

    /**
     * 打开图库
     * <p>
     *
     * @param activity
     * @param requestCode
     */
    public static void takePick(Activity activity, int requestCode) {
        Intent intent = Intents.createPickIntent();
        activity.startActivityForResult(intent, requestCode);
    }


}
