package io.ganguo.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.ganguo.GGimage.BuildConfig;
import io.ganguo.library.Config;
import io.ganguo.library.util.FileUtils;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * 处理照片拍摄，裁剪工具类
 * Created by HulkYao on 4/11/15.
 */
public class PhotoUtil {
    public final static Logger logger = LoggerFactory.getLogger(PhotoUtil.class);

    public final static int REQUEST_CODE_PICK = 333;
    public final static int REQUEST_CODE_CAMERA = 666;
    public final static int REQUEST_CODE_CROP = 999;

    /**
     * 获得一个暂存位置文件
     *
     * @return
     */
    public static File getTempFile() {
        String tempPath = Config.getTempPath() + System.currentTimeMillis() + "_temp.jpg";
        File tempFile = new File(tempPath);
        return tempFile;
    }

    /**
     * 获得一个图片位置文件
     *
     * @return
     */
    public static File getImageFile() {
        String tempPath = Config.getImagePath() + System.currentTimeMillis() + "_temp.jpg";
        File tempFile = new File(tempPath);
        return tempFile;
    }


    /**
     * 裁剪图片
     *
     * @param context
     * @param uri
     * @param outputXY
     * @return
     */
    public static File cropPhoto(Activity context, Uri uri, int outputXY) {
        File tempFile = getTempFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputXY);
        intent.putExtra("outputY", outputXY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, REQUEST_CODE_CROP);
        return tempFile;
    }

    /**
     * 裁剪图片
     *
     * @param context
     * @param path
     * @param outputXY
     * @return
     */
    public static File cropPhoto(Activity context, File path, int outputXY) {
        File tempFile = getTempFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(path), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputXY);
        intent.putExtra("outputY", outputXY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, REQUEST_CODE_CROP);
        return tempFile;
    }

    /**
     * 裁剪图片
     *
     * @param context
     * @param path
     * @param outputXY
     * @return
     */
    public static File cropPhoto(Fragment context, File path, int outputXY) {
        File tempFile = getTempFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(path), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputXY);
        intent.putExtra("outputY", outputXY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.getActivity().startActivityForResult(intent, REQUEST_CODE_CROP);
        return tempFile;
    }

    /**
     * 拍照
     *
     * @param context
     * @return
     */
    public static File takePhoto(Activity context) {
        File tempFile = getTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("noFaceDetection", true);
        context.setResult(Activity.RESULT_OK);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        return tempFile;
    }

    /**
     * 拍照
     *
     * @param context
     * @param ImagePath
     * @return
     */
    public static File takePhoto(Activity context, String ImagePath) {
        File tempFile = getTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(ImagePath)));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("noFaceDetection", true);
        context.setResult(Activity.RESULT_OK);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        return tempFile;
    }


    /**
     * 拍照
     *
     * @param context
     * @param requestCode 请求拍照的代码（区分不同界面跳转到拍照界面，以便于拍照成功后对参数处理）
     * @return
     */
    public static File takePhoto(Activity context, int requestCode) {
        File tempFile = getTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("noFaceDetection", true);
        context.setResult(Activity.RESULT_OK);
        context.startActivityForResult(intent, requestCode);
        return tempFile;
    }


    /**
     * 拍照
     *
     * @param context
     * @param requestCode 请求拍照的代码,不指定Uri，避免返回data值为null。
     * @return
     */
    public static void takePhotoNoUri(Activity context, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        context.setResult(Activity.RESULT_OK);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 拍照
     *
     * @param context 请求拍照的代码,不指定Uri，避免返回data值为null。
     * @return
     */
    public static void takePhotoNoUri(Activity context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        context.setResult(Activity.RESULT_OK);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 拍照
     *
     * @param context
     * @return
     */
    public static File takePhoto(Fragment context) {
        File tempFile = getTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("noFaceDetection", true);
        context.getActivity().setResult(Activity.RESULT_OK);
        context.getActivity().startActivityForResult(intent, REQUEST_CODE_CAMERA);
        return tempFile;
    }


    /**
     * 拍照
     *
     * @param context
     * @param requestCode 请求拍照的代码（区分不同界面跳转到拍照界面，以便拍照成功后对参数处理）
     * @return
     */
    public static File takePhoto(Fragment context, int requestCode) {
        File tempFile = getTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("noFaceDetection", true);
        context.getActivity().startActivityForResult(intent, requestCode);
        context.getActivity().setResult(Activity.RESULT_OK);
        return tempFile;
    }

    /**
     * 挑选照片，返回uri
     *
     * @param aboveKitkatCode 4.4以上请求拍照的代码（区分不同界面跳转到拍照界面，以便拍照成功后对参数处理）
     * @param aboveKitkatCode 4.4以下请求拍照的代码（区分不同界面跳转到拍照界面，以便拍照成功后对参数处理）
     * @param context
     */
    public static void pickPhoto(Activity context, int aboveKitkatCode, int followingKitkatCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("return-data", true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            context.startActivityForResult(intent, aboveKitkatCode);//4.4版本
        } else {
            context.startActivityForResult(intent, followingKitkatCode);//4.4以下版本，先不处理
        }
        context.setResult(Activity.RESULT_OK);
    }


    /**
     * 挑选照片，返回uri
     *
     * @param context
     */
    public static void pickPhoto(Activity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("return-data", true);
        context.startActivityForResult(intent, REQUEST_CODE_PICK);
        context.setResult(Activity.RESULT_OK);
    }


    /**
     * 挑选照片，返回uri
     *
     * @param requestCode 请求拍照的代码（区分不同界面跳转到拍照界面，以便拍照成功后对参数处理）
     * @param context
     */
    public static void pickPhoto(Fragment context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("return-data", true);
        context.getActivity().startActivityForResult(intent, REQUEST_CODE_PICK);
        context.getActivity().setResult(Activity.RESULT_OK);
    }

    /**
     * 挑选照片，返回uri
     *
     * @param context
     */
    public static void pickPhoto(Fragment context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("return-data", true);
        context.getActivity().startActivityForResult(intent, REQUEST_CODE_PICK);
        context.getActivity().setResult(Activity.RESULT_OK);
    }

    /**
     * 压缩图片
     *
     * @param context
     * @param tempUri
     * @param outPutSize 这只是一个估值，当裁剪出来照片设置像素太高时最后的值也会偏高
     * @return
     */
    public static Bitmap recycleCompressBitmap(Context context, Uri tempUri, int outPutSize) {
        Bitmap photo = uriToBitmap(context, tempUri);
        Bitmap resultBitmap = null;
        if (photo != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int options = 100;
            photo.compress(Bitmap.CompressFormat.JPEG, options, outputStream);
            while (outputStream.toByteArray().length / 1024 > outPutSize) {
                outputStream.reset();
                options -= 5;
                photo.compress(Bitmap.CompressFormat.JPEG, options, outputStream);
            }
            photo.recycle();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            resultBitmap = BitmapFactory.decodeStream(inputStream, null, null);
        }
        return resultBitmap;
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @param outPutSize 这只是一个估值，当裁剪出来照片设置像素太高时最后的值也会偏高
     * @return
     */
    public static Bitmap recycleCompressBitmap(Bitmap bitmap, int outPutSize) {
        Bitmap photo = bitmap;
        if (photo != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int options = 100;
            photo.compress(Bitmap.CompressFormat.JPEG, options, outputStream);
            while (outputStream.toByteArray().length / 1024 > outPutSize) {
                outputStream.reset();
                options -= 5;
                photo.compress(Bitmap.CompressFormat.JPEG, options, outputStream);
            }
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            photo = BitmapFactory.decodeStream(inputStream, null, null);
        }
        return photo;
    }

    /**
     * 压缩图片
     *
     * @param context
     * @param tempUri
     * @return
     */
    public static Bitmap compressBitmap(Context context, Uri tempUri) {
        Bitmap photo = uriToBitmap(context, tempUri);
        if (photo != null) {
            try {
                FileOutputStream out = new FileOutputStream(tempUri.getPath());
                photo.compress(Bitmap.CompressFormat.JPEG, 95, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return photo;
    }


    /**
     * 压缩图片
     *
     * @param context
     * @param tempUri
     * @return
     */
    public static File compressBitmapReturnFile(Context context, Uri tempUri) {
        Bitmap photo = uriToBitmap(context, tempUri);
        File file = new File(tempUri.getPath());
        if (photo != null) {
            try {
                FileOutputStream out = new FileOutputStream(tempUri.getPath());
                photo.compress(Bitmap.CompressFormat.JPEG, 95, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 压缩图片
     *
     * @param context
     * @param file
     * @return
     */
    public static File compressBitmapReturnFile(Context context, File file) {
        Bitmap photo = BitmapFactory.decodeFile(file.getAbsolutePath());
        File files = new File(file.getAbsolutePath());
        if (photo != null) {
            try {
                FileOutputStream out = new FileOutputStream(files.getAbsolutePath());
                if (FileUtils.getFileSize(file.getAbsolutePath()) <= 250) {
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } else if (FileUtils.getFileSize(file.getAbsolutePath()) >= 400) {
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, out);
                }
                photo.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return files;
    }


    /**
     * 将uri转为bitmap
     *
     * @param context
     * @param tempUri
     * @return
     */
    public static Bitmap uriToBitmap(Context context, Uri tempUri) {
        Bitmap bitmap = null;
        try {
            if (tempUri != null) {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), tempUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 压缩图片
     *
     * @return
     */
    public static File seveBitmapFile(Bitmap bitmap, File file) {
        if (bitmap != null) {
            try {
                FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 获取Uri里面的图片路径
     *
     * @param contentUri
     * @return
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    /**
     * 返回的路径可能会因不同的手机发生偏移旋转
     * 可以通过获取filePAth的路径的属性判断旋转的度数纠正过来。
     *
     * @param filePath
     * @return
     */
    public static Bitmap getRegularBitmap(String filePath) {
        int degree = readPictureDegree(filePath);
        Bitmap bitmap = PhotoUtil.getSmallBitmap(filePath);
        bitmap = rotatingImageView(degree, bitmap);
        return bitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotatingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}
