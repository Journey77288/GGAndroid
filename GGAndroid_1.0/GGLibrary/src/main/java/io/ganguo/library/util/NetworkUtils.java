package io.ganguo.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.text.TextUtils;

import io.ganguo.library.Config;

/**
 * 网络工具类
 * <p/>
 * Created by zhihui_chen on 14-8-19.
 */
public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getName();

    public static final String SETTING_WIFI_MAC = "config_wifi_mac";

    /**
     * WIFI网络
     */
    public static final int NETTYPE_WIFI = 0x01;
    /**
     * CMWAP网络
     */
    public static final int NETTYPE_CMWAP = 0x02;
    /**
     * CMNET网络
     */
    public static final int NETTYPE_CMNET = 0x03;

    /**
     * 检测网络是否可用
     *
     * @return isConnected
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    public static boolean isWifiAvailable(Context context) {
        if (getNetworkType(context) == NETTYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 获取wifi mac地址, 若wifi未开启,将自动开启wifi
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        // 先从配置中拿到mac
        String macAddress = Config.getString(SETTING_WIFI_MAC);
        if (!StringUtils.isEmpty(macAddress)) return macAddress;

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        macAddress = info.getMacAddress();
        if (StringUtils.isEmpty(macAddress) || !wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(true);
            // 延迟一下
            SystemClock.sleep(3500);
            info = wifi.getConnectionInfo();
            macAddress = info.getMacAddress();
        }

        // 写入到配置信息中
        if (!StringUtils.isEmpty(macAddress))
            Config.putString(SETTING_WIFI_MAC, macAddress);

        return macAddress;
    }
}
