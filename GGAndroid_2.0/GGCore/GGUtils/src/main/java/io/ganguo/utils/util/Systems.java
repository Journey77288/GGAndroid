package io.ganguo.utils.util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;

import com.facebook.device.yearclass.YearClass;

import io.ganguo.utils.bean.Globals;

/**
 * 系统/app 工具
 * <p/>
 * Created by Tony on 9/30/15.
 */
public class Systems {
    private Systems() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 设备级别
     */
    public enum Performance {
        LOW, MEDIUM, HIGH
    }

    /**
     * 设备级别 https://github.com/facebook/device-year-class
     *
     * @return
     */
    public static Performance getPerformance(Context context) {
        int year = YearClass.get(context);
        if (year >= 2014) {
            return Performance.HIGH;
        } else if (year >= 2012) {
            return Performance.MEDIUM;
        } else {
            return Performance.LOW;
        }
    }

    /**
     * 获取设备唯一码
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    @Deprecated
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (Strings.isEmpty(deviceId)) {
            deviceId = Strings.randomUUID();
        }
        return deviceId;
    }


    /**
     * 是否平板设备
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(Context context, String content) {
        // 得到剪贴板管理器
        ClipboardManager cm = getClipboardManager(context);
        // 将文本内容放到系统剪贴板里。
        ClipData clipData = ClipData.newPlainText(String.valueOf(System.currentTimeMillis()), content);
        cm.setPrimaryClip(clipData);
    }


    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cm = getClipboardManager(context);
        // 检查剪贴板是否有内容
        if (cm.hasPrimaryClip()) {
            CharSequence content = cm.getPrimaryClip().getItemAt(0).coerceToText(context);
            return content != null ? content.toString() : "";
        }
        return "";
    }

    /**
     * 得到剪贴板管理器
     *
     * @param context
     * @return
     */

    public static ClipboardManager getClipboardManager(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cm;
    }


}
