package io.ganguo.utils.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;
import java.util.List;


/**
 * <p>
 * App安装判断工具类
 * </p>
 * Created by leo on 2018/9/11.
 */
public class AppInstalls {
    private static final String KEY_PACKAGE = "package";
    private static final String TYPE_PACKAGE_ARCHIVE = "application/vnd.android.package-archive";
    //WhatApp
    private static final String PACKAGE_WHATS_APP = "com.whatsapp";
    //Telegram
    private static final String PACKAGE_TELEGRAM = "org.telegram.messenger";
    //微信
    private static final String PACKAGE_WECHAT = "com.tencent.mm";
    //Gmail
    private static final String PACKAGE_GMAIL = "com.google.android.gm";
    //Line
    private static final String PACKAGE_LINE = "jp.naver.line.android";
    //Twitter
    private static final String PACKAGE_TWITTER = "com.twitter.android";
    //QQ
    private static final String PACKAGE_QQ = "com.tencent.mobileqq";
    //支付宝
    private static final String PACKAGE_ALIPAY = "com.eg.android.AlipayGphone";
    //FaceBook
    private static final String PACKAGE_FACE_BOOK = " com.facebook.katana";
    //新浪微博
    private static final String PACKAGE_SINA_WB = "com.sina.weibo";


    /**
     * 判断App是否安装
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> pinfo = pm.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (Strings.isEquals(pinfo.get(i).packageName, packageName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件
     */
    public static void installApk(Context context, File file) {
        installApk(context, Uri.fromFile(file));
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件uri
     */
    public static void installApk(Context context, Uri file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(file, TYPE_PACKAGE_ARCHIVE);
        context.startActivity(intent);
    }

    /**
     * 卸载apk
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse(KEY_PACKAGE + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }



    /**
     * function：判断新浪微博是否安装
     *
     * @param context
     * @return
     */
    public static boolean isSinaClientInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_SINA_WB);
    }


    /**
     * function：判断FaceBook是否安装
     *
     * @param context
     * @return
     */
    public static boolean isFaceBookInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_FACE_BOOK);
    }

    /**
     * function：判断微信是否安装
     *
     * @param context
     * @return
     */
    public static boolean isWeChatInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_WECHAT);
    }


    /**
     * function：判断支付宝是否安装
     *
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_ALIPAY);
    }

    /**
     * function：判断Gmail是否安装
     *
     * @param context
     * @return
     */
    public static boolean isGmailInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_GMAIL);
    }


    /**
     * function：判断手机是否安装WhatApp
     *
     * @param context
     * @return
     */
    public static boolean isWhatAppInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_WHATS_APP);
    }

    /**
     * function：判断手机是否安装QQ
     *
     * @param context
     * @return
     */
    public static boolean isQQAppInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_QQ);
    }


    /**
     * function：判断telegram是否安装
     *
     * @param context
     * @return
     */
    public static boolean isTelegramInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_TELEGRAM);
    }

    /**
     * function：判断Twitter是否安装
     *
     * @param context
     * @return
     */
    public static boolean isTwitterInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_TWITTER);
    }


    /**
     * function：判断Line是否安装
     *
     * @param context
     * @return
     */
    public static boolean isLineInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_LINE);
    }


}
