package io.ganguo.scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import io.ganguo.scanner.builder.TextViewConfig;


/**
 * <p>
 * 条码/二维码生成工具类
 * </p>
 * Created by leo on 2018/8/7.
 */
public class CodeEncryptHelper {

    /**
     * function: 生成二维码
     *
     * @param content
     * @return
     */
    public static Bitmap createQRCode(String content) {
        return createQRCode(content, 300, 300);
    }

    /**
     * function: 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createQRCode(String content, int width, int height) {
        Bitmap bitmap;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);//这里调整二维码的容错率
        hints.put(EncodeHintType.MARGIN, 1);   //设置白边取值1-4，值越大白边越大
        try {
            result = multiFormatWriter.encode(new String(content.getBytes("UTF-8"), "ISO-8859-1"),
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }


    /**
     * function: 生成带logo的二维码
     *
     * @param content
     * @param logo
     * @return
     */
    public static Bitmap createLogoQRCode(String content, Bitmap logo) {
        return createLogoQRCode(content, 300, 300, logo, 0.3f);
    }

    /**
     * function: 生成带logo的二维码
     *
     * @param content
     * @param logo
     * @return
     */
    public static Bitmap createLogoQRCode(String content, int width, int height, Bitmap logo) {
        //logo大小默认为二维码大小 * 0.3f ，Logo太大可能导致二维码无法识别，也可以通过指定scaleValue来指定大小
        return createLogoQRCode(content, width, height, logo, 0.3f);
    }


    /**
     * function: 生成带logo的二维码
     *
     * @param content
     * @param logo
     * @return
     */
    public static Bitmap createLogoQRCode(String content, int width, int height, Bitmap logo, float scaleValue) {
        Bitmap qrCode = createQRCode(content, width, height);
        return createLogoQRCode(qrCode, logo, scaleValue);
    }

    /**
     * function: 生成带logo的二维码
     *
     * @param qrCode
     * @param scaleValue
     * @param logo
     * @return
     */
    public static Bitmap createLogoQRCode(Bitmap qrCode, Bitmap logo, float scaleValue) {
        int qrwidth = qrCode.getWidth();
        int waterWidth = (int) (qrwidth * scaleValue);
        float scale = waterWidth / (float) logo.getWidth();
        Bitmap waterQrcode = CodeHelper.createWaterMaskCenter(qrCode, CodeHelper.zoomImg(logo, scale));
        return waterQrcode;
    }

    /**
     * function:生成条形码
     *
     * @param context
     * @param contents
     * @return
     */
    public static Bitmap createBarCodeWithText(Context context, String contents) {
        return createBarCodeWithText(context, contents, 300, 150, null);
    }

    /**
     * function:生成条形码
     *
     * @param context
     * @param contents
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    public static Bitmap createBarCodeWithText(Context context, String contents, int desiredWidth,
                                               int desiredHeight) {
        return createBarCodeWithText(context, contents, desiredWidth, desiredHeight, null);
    }


    /**
     * function:生成条形码
     *
     * @param context
     * @param contents
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    public static Bitmap createBarCodeWithText(Context context, String contents, int desiredWidth,
                                               int desiredHeight, TextViewConfig config) {
        if (TextUtils.isEmpty(contents)) {
            throw new NullPointerException("contents not be null");
        }
        if (desiredWidth == 0 || desiredHeight == 0) {
            throw new NullPointerException("desiredWidth or desiredHeight not be null");
        }
        Bitmap resultBitmap;

        /**
         * 条形码的编码类型
         */
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

        Bitmap barcodeBitmap = encodeBarCodeAsBitmap(contents, barcodeFormat,
                desiredWidth, desiredHeight);

        Bitmap codeBitmap = createBarCodeBitmap(contents, barcodeBitmap.getWidth(),
                barcodeBitmap.getHeight(), context, config);

        resultBitmap = CodeHelper.mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(
                0, desiredHeight));
        return resultBitmap;
    }

    /**
     * function: 将条形码信息写入到Bitmap
     *
     * @param contents
     * @param desiredHeight
     * @param desiredWidth
     * @return
     */
    private static Bitmap encodeBarCodeAsBitmap(String contents, BarcodeFormat format, int desiredWidth, int desiredHeight) {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, format, desiredWidth,
                    desiredHeight, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;

    }

    /**
     * function: 生成条形码文字信息相关Bitmap
     *
     * @param contents
     * @param width
     * @param height
     * @param config
     * @return
     */
    private static Bitmap createBarCodeBitmap(String contents, int width, int height, Context context,
                                              TextViewConfig config) {
        if (config == null) {
            config = new TextViewConfig.Builder().build();
        }
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setText(contents);
        tv.setTextSize(config.getSize() == 0 ? tv.getTextSize() : config.getSize());
        tv.setHeight(height);
        tv.setGravity(config.getGravity());
        tv.setMaxLines(config.getMaxLines());
        tv.setWidth(width);
        tv.setDrawingCacheEnabled(true);
        tv.setTextColor(config.getColor());
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

        tv.buildDrawingCache();
        return tv.getDrawingCache();
    }

}
