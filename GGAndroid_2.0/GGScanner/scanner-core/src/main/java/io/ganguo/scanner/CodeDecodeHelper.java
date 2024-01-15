package io.ganguo.scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import io.ganguo.scanner.core.Config;
import io.ganguo.scanner.core.Image;
import io.ganguo.scanner.core.ImageScanner;
import io.ganguo.scanner.core.Symbol;
import io.ganguo.utils.util.Bitmaps;
import io.ganguo.utils.util.Files;
import io.ganguo.utils.util.log.Logger;

/**
 * <p>
 * 识别二维码/条形码工具类
 * </p>
 * Created by leo on 2018/8/7.
 */

public final class CodeDecodeHelper {
    static final Map<DecodeHintType, Object> ALL_HINT_MAP = new EnumMap<>(DecodeHintType.class);

    static {
        List<BarcodeFormat> allFormatList = new ArrayList<>();
        allFormatList.add(BarcodeFormat.AZTEC);
        allFormatList.add(BarcodeFormat.CODABAR);
        allFormatList.add(BarcodeFormat.CODE_39);
        allFormatList.add(BarcodeFormat.CODE_93);
        allFormatList.add(BarcodeFormat.CODE_128);
        allFormatList.add(BarcodeFormat.DATA_MATRIX);
        allFormatList.add(BarcodeFormat.EAN_8);
        allFormatList.add(BarcodeFormat.EAN_13);
        allFormatList.add(BarcodeFormat.ITF);
        allFormatList.add(BarcodeFormat.MAXICODE);
        allFormatList.add(BarcodeFormat.PDF_417);
        allFormatList.add(BarcodeFormat.QR_CODE);
        allFormatList.add(BarcodeFormat.RSS_14);
        allFormatList.add(BarcodeFormat.RSS_EXPANDED);
        allFormatList.add(BarcodeFormat.UPC_A);
        allFormatList.add(BarcodeFormat.UPC_E);
        allFormatList.add(BarcodeFormat.UPC_EAN_EXTENSION);

        // 可能的编码格式
        ALL_HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, allFormatList);
        // 花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
        ALL_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        // 复杂模式，开启 PURE_BARCODE 模式（带图片 LOGO 的解码方案）
        ALL_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        // 编码字符集
        ALL_HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
    }


    /**
     * function：识别本地照片中的二维码
     *
     * @param filePathUrl 本地文件路径
     * @return
     */
    public static String decodeQRcode(String filePathUrl) {
        if (!Files.checkFileExists(filePathUrl)) {
            throw new RuntimeException("File does not exist");
        }
        Bitmap bitmap = Bitmaps.uniformScale(new File(filePathUrl));
        return decodeQRcode(bitmap);
    }


    /**
     * function：识别ImageView中加载的二维码
     *
     * @param imageView
     * @return
     */
    public static String decodeQRcode(ImageView imageView) {
        if (imageView == null) {
            throw new RuntimeException("decodeBarcode imageView Can't be  null");
        }
        Bitmap bitmap = ((BitmapDrawable) (imageView).getDrawable()).getBitmap();
        return decodeQRcode(bitmap);
    }


    /**
     * function：读取本地图片中的条形码数据
     *
     * @param filePathUrl
     * @return
     */
    public static String decodeBarcode(String filePathUrl) {
        if (!Files.checkFileExists(filePathUrl)) {
            throw new RuntimeException("File does not exist");
        }
        Bitmap bitmap = BitmapFactory.decodeFile(filePathUrl);
        return decodeBarcode(bitmap);
    }

    /**
     * function：识别ImageView中加载的条形码
     *
     * @param imageView
     * @return
     */
    public String decodeBarcode(ImageView imageView) {
        if (imageView == null) {
            throw new RuntimeException("decodeBarcode imageView Can't be  null");
        }
        Bitmap bitmap = ((BitmapDrawable) (imageView).getDrawable()).getBitmap();
        return decodeBarcode(bitmap);
    }


    /**
     * function：读取Bitmap中二维码数据
     *
     * @param bitmap
     * @return
     */
    public static String decodeQRcode(Bitmap bitmap) {
        if (bitmap == null) {
            Logger.e("decodeBarcode Bitmap Can't be  null");
            return "";
        }
        Result result;
        RGBLuminanceSource source = null;
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            source = new RGBLuminanceSource(width, height, pixels);
            result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), ALL_HINT_MAP);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            if (source != null) {
                try {
                    result = new MultiFormatReader().decode(new BinaryBitmap(new GlobalHistogramBinarizer(source)), ALL_HINT_MAP);
                    return result.getText();
                } catch (Throwable e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        }
    }


    /**
     * function：读取Bitmap中条形码数据
     *
     * @param barcodeBmp
     * @return
     */
    public static String decodeBarcode(Bitmap barcodeBmp) {
        if (barcodeBmp == null) {
            Logger.e("decodeBarcode Bitmap Can't be  null");
            return "";
        }
        int width = barcodeBmp.getWidth();
        int height = barcodeBmp.getHeight();
        int[] pixels = new int[width * height];
        barcodeBmp.getPixels(pixels, 0, width, 0, 0, width, height);
        Image barcode = new Image(width, height, "RGB4");
        barcode.setData(pixels);
        ImageScanner reader = new ImageScanner();
        reader.setConfig(Symbol.NONE, Config.ENABLE, 0);
        reader.setConfig(Symbol.CODE128, Config.ENABLE, 1);
        reader.setConfig(Symbol.CODE39, Config.ENABLE, 1);
        reader.setConfig(Symbol.EAN13, Config.ENABLE, 1);
        reader.setConfig(Symbol.EAN8, Config.ENABLE, 1);
        reader.setConfig(Symbol.UPCA, Config.ENABLE, 1);
        reader.setConfig(Symbol.UPCE, Config.ENABLE, 1);
        reader.setConfig(Symbol.UPCE, Config.ENABLE, 1);
        int result = reader.scanImage(barcode.convert("Y800"));
        if (result == 0) {
            return "";
        }
        return CodeHelper.getScannerResults(reader);
    }
}
