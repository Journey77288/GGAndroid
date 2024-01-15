package io.ganguo.utils.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.ganguo.utils.bean.Globals;
import io.ganguo.utils.util.log.Logger;

/**
 * bitmap处理工具类
 * <p>
 * Created by HulkYao on 4/11/15.
 */
public class Bitmaps {

    private Bitmaps() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 保存为文件
     * (完成后回收bitmap)
     *
     * @param bitmap
     * @param to
     * @param format
     * @param quality
     */
    public static void toFile(Bitmap bitmap, File to, Bitmap.CompressFormat format, int quality) {
        Validates.notNull(bitmap);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(to);
            bitmap.compress(format, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Logger.e("handleImageToFile failed:", e);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e("handleImageToFile failed:", e);
        }
    }

    /**
     * 保存为PNG
     * (完成后回收bitmap)
     *
     * @param bitmap
     * @param to
     * @param quality
     */
    public static void toPNGFile(Bitmap bitmap, File to, int quality) {
        toFile(bitmap, to, Bitmap.CompressFormat.PNG, quality);
    }

    /**
     * 保存为JPEG
     * (完成后回收bitmap)
     *
     * @param bitmap
     * @param to
     * @param quality
     */
    public static void toJPEGFile(Bitmap bitmap, File to, int quality) {
        toFile(bitmap, to, Bitmap.CompressFormat.JPEG, quality);
    }

    /**
     * 获取bitmap占用的内存体积(byte)
     *
     * @param bitmap byte
     * @return
     */
    public static long size(Bitmap bitmap) {
        Validates.notNull(bitmap);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return (long) bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    /**
     * 旋转bitmap
     *
     * @param bitmap
     * @param degrees
     * @return bitmap
     */
    public static Bitmap rotate(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        // bitmap new
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 旋转图片至正确位置
     *
     * @param bitmap
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap rotateToNormal(Bitmap bitmap, String path) {
        int angle = readDegree(path);
        if (angle == 0) {
            return bitmap;
        }
        return rotate(bitmap, angle);

    }

    /**
     * 用于压缩file类型文件大小
     *
     * @param inputBitmap
     * @param output
     * @param outPutSize
     * @return
     */
    public static File compress(Bitmap inputBitmap, File output, int outPutSize) {
        int quality = 100;
        // 最小quality是10
        while (quality > 10) {
            quality -= 5;
            Bitmaps.toJPEGFile(inputBitmap, output, quality);

            long fileSize = output.length() / 1024;
            if (fileSize <= outPutSize) {
                break;
            }
            Logger.i("scaleBitmap" + "quality," + quality +
                    " output.length() / 1024 :" + (output.length() / 1024) +
                    " outPutSize:" + outPutSize);
        }
        return output;
    }

    /**
     * 等比缩放
     *
     * @param from
     * @return
     */
    public static Bitmap uniformScale(File from) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(from.getPath(), newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        int be = computeSize(w, h);
        while (true) {
            try {
                // 设置缩放比例
                newOpts.inSampleSize = be;
                return BitmapFactory.decodeFile(from.getPath(), newOpts);
            } catch (OutOfMemoryError outOfMemoryError) {
                Logger.e("java.lang.OutOfMemoryError,current be:" + be);
                be++;
            }
        }
    }


    /**
     * 分辨率的压缩算法
     * <p>
     * 接近微信图片保存时图片分辨率算法
     * https://github.com/Curzibn/Luban/blob/master/library/src/main/java/top/zibin/luban/Engine.java
     *
     * @param srcWidth
     * @param srcHeight
     * @return
     */
    private static int computeSize(int srcWidth, int srcHeight) {
        int mSampleSize;

        srcWidth = srcWidth % 2 == 1 ? srcWidth + 1 : srcWidth;
        srcHeight = srcHeight % 2 == 1 ? srcHeight + 1 : srcHeight;

        srcWidth = srcWidth > srcHeight ? srcHeight : srcWidth;
        srcHeight = srcWidth > srcHeight ? srcWidth : srcHeight;

        double scale = ((double) srcWidth / srcHeight);

        if (scale <= 1 && scale > 0.5625) {
            if (srcHeight < 1664) {
                mSampleSize = 1;
            } else if (srcHeight >= 1664 && srcHeight < 4990) {
                mSampleSize = 2;
            } else if (srcHeight >= 4990 && srcHeight < 10240) {
                mSampleSize = 4;
            } else {
                mSampleSize = srcHeight / 1280 == 0 ? 1 : srcHeight / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            mSampleSize = srcHeight / 1280 == 0 ? 1 : srcHeight / 1280;
        } else {
            mSampleSize = (int) Math.ceil(srcHeight / (1280.0 / scale));
        }

        return mSampleSize;
    }

    /**
     * 拿图片路径缩略图
     *
     * @param path 图片的路径
     * @return Bitmap
     */
    public static Bitmap getThumbPic(String path) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e("failed to get ExifInterface", e);
        }
        if (exif == null || exif.getThumbnail() == null) {
            return null;
        }
        byte[] imageData = exif.getThumbnail();
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    degree = 0;
                    break;
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
            Logger.e("failed to readPictureDegree", e);
        }
        return degree;
    }

    /**
     * 毛玻璃效果
     *
     * @param from
     * @param radius
     * @param canReuseInBitmap
     * @return bitmap
     */
    public static Bitmap doBlur(Bitmap from, int radius, boolean canReuseInBitmap) {
        Validates.notNull(from);

        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = from;
        } else {
            bitmap = from.copy(from.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    /**
     * recycle bitmap
     * <p/>
     *
     * @param bitmap
     */
    public static void recycle(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * recycle drawable
     * -> recycle(Bitmap bitmap)
     *
     * @param drawable
     */
    public static void recycle(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        if (drawable instanceof BitmapDrawable) {
            recycle(((BitmapDrawable) drawable).getBitmap());
        }
    }

    /**
     * recycle imageView
     * -> recycle(Bitmap bitmap)
     *
     * @param imageView
     */
    public static void recycle(ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Drawable drawable = imageView.getDrawable();
        imageView.setImageBitmap(null);
        imageView.setImageDrawable(null);
        recycle(drawable);
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String toBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
