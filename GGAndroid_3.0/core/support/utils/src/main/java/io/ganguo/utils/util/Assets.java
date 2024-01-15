package io.ganguo.utils.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.ganguo.utils.bean.Globals;
import io.ganguo.utils.util.json.Gsons;

/**
 * Assets 工具类
 * <p>
 * Created by hulkyao on 23/6/2017.
 */

public class Assets {

    private Assets() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 从asset中读取bitmap  Warn:耗时操作
     *
     * @param context
     * @param fileName    相对文件名, 支持多层结构, 如"cover.png", "images/pro/11/detail.png",
     * @return bitmap
     * @throws IOException
     */
    public static Bitmap getBitmap(Context context, String fileName) throws IOException {
        InputStream in = null;
        try {
            in = context.getAssets().open(fileName);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            if (in != null) {
                in.close();
                in = null;
            }
        }

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(in);
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
        }
        return bitmap;
    }

    /**
     * 从asset中读取json object  Warn:耗时操作
     *
     * @param context
     * @param fileName 相对文件名, 支持多层结构, 如"pro_gg.txt", "images/pro/11/details.json",
     * @param tClass  转换数据类型
     * @return T
     */
    public static <T> T getJsonObject(Context context, String fileName, Class<T> tClass) {
        String jsonString = getString(context, fileName);
        T object = Gsons.fromJson(jsonString, tClass);
        return object;
    }

    /**
     * 从assets获取string
     *
     * @param context
     * @param fileName 相对文件名, 支持多层结构, 如"pro_gg.txt", "images/pro/11/details.json",
     * @return
     */
    public static String getString(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从assets获取多行string
     *
     * @param context
     * @param fileName 相对文件名, 支持多层结构, 如"pro_gg.txt", "images/pro/11/details.json",
     * @return
     */
    public static List<String> getStringList(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 复制asset文件
     * # File to = new File(Config.getDataPath(), "test");
     * # copyAssetFile(ctx, "test", to);
     *
     * @param context
     * @param fileName
     * @param to
     * @throws IOException
     */
    public static void copyFile(Context context, String fileName, File to) throws IOException {
        InputStream in = context.getAssets().open(fileName);
        OutputStream out = new FileOutputStream(to, false);
        try {
            byte data[] = new byte[1024];
            int length;
            while ((length = in.read(data)) != -1) {
                out.write(data, 0, length);
            }
            out.flush();
        } finally {
            out.close();
            in.close();
        }
    }

}
