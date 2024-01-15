package io.ganguo.utils.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.ganguo.utils.bean.Globals;

/**
 * Intent操作工具类
 * <p>
 * update by leo on 2018/07/06.
 */

public class Intents {

    private Intents() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }


    /**
     * <p>
     * 1、创建一个拍照intent
     * 2、在回调处 ContentResolver.openOutputStream(uri)
     * </p>
     *
     * @param uri
     * @return Intent
     */
    public static Intent createTakePhotoIntent(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return intent;
    }


    /**
     * 在系统相册中创建一个Uri
     * 可以使拍完照片在系统相册中找到
     *
     * @param context
     * @return
     */
    public static Uri createImageUri(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues cv = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        cv.put(MediaStore.Images.Media.TITLE, timeStamp);
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
    }


    /**
     * <p>
     * 创建一个裁剪图片intent
     * </p>
     *
     * @param from
     * @param to
     * @param outputXY
     * @return
     */
    public static Intent createCropImgIntent(File from, File to, int outputXY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(from), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputXY);
        intent.putExtra("outputY", outputXY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(to));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        return intent;
    }


    /**
     * <p>
     * 1、创建一个选取照片intent
     * 2、挑选图片在4.4以下处理会有所不同，在onActivityResult那里要区分处理
     * </p>
     *
     * @return Intent
     */
    public static Intent createPickIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        return intent;
    }


    /**
     * 获取系统相册最新的一张图片的Uri
     *
     * @param context
     * @return
     */
    public static Uri getLastPathInSystemImages(Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.ImageColumns.ORIENTATION},
                MediaStore.Images.Media.DATE_ADDED, null, "date_added ASC");

        Uri uri = null;
        if (cursor != null && cursor.moveToLast()) {
            uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            cursor.close();
        }
        return uri;
    }

}
