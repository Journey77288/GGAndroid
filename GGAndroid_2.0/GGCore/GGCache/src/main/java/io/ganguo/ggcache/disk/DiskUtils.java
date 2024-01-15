package io.ganguo.ggcache.disk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 帮助类
 * Created by Lynn on 2016/12/14.
 */

public class DiskUtils {
    private DiskUtils() {
    }

    /**
     * 根据 dirname 生成缓存文件
     */
    @SuppressWarnings("ConstantConditions")
    public static File getDiskCacheDir(final Context context, final String dirName) {
        final String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            // SD可用前提下
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + dirName);
    }

    /**
     * 获取当前应用程序的版本号。
     */
    public static int getAppVersion(final Context context, final int defaultValue) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * md5 encode: key -> hex decimal digit
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            //md5加密
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            //直接通过hashCode()
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * 字节流转换16进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        if (bytes == null || bytes.length <= 0) {
            return sb.toString();
        }
        final int len = bytes.length;
        for (int i = 0; i < len; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 转换 bitmap 为 byte[]
     */
    public static byte[] bitmap2ByteArray(final Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //png lossless, 压缩损失小, webp 在某些情况会有 bug , 尤其是深度定制的 rom
        //png 时, quality 无效
        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)) {
            return bos.toByteArray();
        }
        return null;
    }

    /**
     * 逆转换 byte[] 为 bitmap
     */
    public static Bitmap byteArray2Bitmap(byte[] bytes) {
        if (bytes == null || bytes.length < 0) {
            return null;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * drawable 转换为 bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            final BitmapDrawable bd = (BitmapDrawable) drawable;
            if (bd.getBitmap() != null) {
                return bd.getBitmap();
            }
        }

        final Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), config);
        final Canvas canvas = new Canvas(bitmap);
        //设置画布大小
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        //绘制到画布上
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * bitmap 转换为 drawable
     */
    @SuppressWarnings("deprecation")
    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        final BitmapDrawable bd = new BitmapDrawable(bitmap);
        bd.setTargetDensity(bitmap.getDensity());
        return bd;
    }
}
