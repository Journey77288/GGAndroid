package io.ganguo.scanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;

import io.ganguo.scanner.core.ImageScanner;
import io.ganguo.scanner.core.Symbol;
import io.ganguo.scanner.core.SymbolSet;


/**
 * QRHelper  - 二维码工具类
 *
 * @link<a href="https://github.com/bertsir/zBarLibary">该工具类，部分源码来着该开源库<a/>
 */
public class CodeHelper {


    /**
     * function：get SymbolSet Result data
     *
     * @param reader
     * @return
     */
    public static String getScannerResults(ImageScanner reader) {
        if (reader == null || reader.getResults() == null) {
            return "";
        }
        SymbolSet symbols = reader.getResults();
        String data = "";
        for (Symbol sym : symbols) {
            data = sym.getData();
        }
        return data;
    }


    /**
     * 将两个Bitmap合并成一个
     *
     * @param first
     * @param second
     * @param fromPoint 第二个Bitmap开始绘制的起始位置（相对于第一个Bitmap）
     * @return
     */
    public static Bitmap mixtureBitmap(Bitmap first, Bitmap second, PointF fromPoint) {
        if (first == null || second == null || fromPoint == null) {
            return null;
        }

        int width = Math.max(first.getWidth(), second.getWidth());
        Bitmap newBitmap = Bitmap.createBitmap(
                width,
                first.getHeight() + second.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, 0, 0, null);
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save();
        cv.restore();

        return newBitmap;
    }

    /**
     * 设置水印图片到中间
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark,
                (src.getWidth() - watermark.getWidth()) / 2,
                (src.getHeight() - watermark.getHeight()) / 2);
    }


    /**
     * 设置水印图片到中间
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas canvas = new Canvas(newb);
        canvas.drawBitmap(src, 0, 0, null);
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        canvas.save();
        canvas.restore();
        return newb;
    }

    /**
     * function: 缩放Bitmap
     *
     * @param bm
     * @param f
     * @return
     */
    public static Bitmap zoomImg(Bitmap bm, float f) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = f;
        float scaleHeight = f;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newed = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newed;
    }

}
