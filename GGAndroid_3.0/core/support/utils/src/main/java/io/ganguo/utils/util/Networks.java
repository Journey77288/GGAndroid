package io.ganguo.utils.util;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;

import androidx.annotation.RequiresPermission;
import androidx.core.content.PermissionChecker;
import io.ganguo.utils.bean.Globals;

/**
 * 网络处理工具
 * <p/>
 * Created by Tony on 9/30/15.
 */
public class Networks {

    private Networks() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 网络类型
     */
    public enum Type {
        _2G, _3G, _4G, WIFI, UNKNOWN
    }

    /**
     * 检测网络是连接
     *
     * @return isConnected
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * Get network type
     *
     * @param context
     * @return ConnectivityManager.TYPE_WIFI | ConnectivityManager.TYPE_MOBILE
     */
    public static int getType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null || cm.getActiveNetworkInfo() == null) {
            return -1;
        }
        return cm.getActiveNetworkInfo().getType();
    }

    /**
     * is wifi network
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        return getType(context) == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * is mobile network
     *
     * @param context
     * @return
     */
    public static boolean isMobile(Context context) {
        return getType(context) == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * get mobile type
     *
     * @param context
     * @return
     */
    public static Type getMobileType(Context context) {
        // is wifi
        if (isWifi(context)) {
            return Type.WIFI;
        }
        // is mobile
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = tm.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return Type._2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return Type._3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return Type._4G;
            default:
                return Type.UNKNOWN;
        }
    }

    /**
     * get wifi mac
     *
     * @param context
     * @return
     */
    @RequiresPermission(allOf = {android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.CHANGE_WIFI_STATE})
    public static String getWifiMac(Context context) {
        if (PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_WIFI_STATE) == PermissionChecker.PERMISSION_GRANTED) {
            WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info == null) {
                wifi.setWifiEnabled(true);
                // waiting enable
                SystemClock.sleep(2500);
                info = wifi.getConnectionInfo();
            }
            return info == null ? "Unknown" : info.getMacAddress();
        }
        return null;
    }

    /**
     * get wifi ip
     * # IPv4 192.168.1.1
     * # IPV6 not supported (formatIpAddress)
     *
     * @param context
     * @return
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_WIFI_STATE)
    public static String getWifiIp(Context context) {
        if (PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_WIFI_STATE) == PermissionChecker.PERMISSION_GRANTED) {
            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            DhcpInfo dhcpInfo = wm.getDhcpInfo();
            if (dhcpInfo == null) {
                return null;
            }
            return Formatter.formatIpAddress(dhcpInfo.ipAddress);
        }
        return null;
    }


    /**
     * get wifi or local ip Address
     */
    public static String getIpAddress(Context context) {
        if (PermissionChecker.checkSelfPermission(context, Manifest.permission.INTERNET) != PermissionChecker.PERMISSION_GRANTED) {
            return null;
        }

        try {
            List<NetworkInterface> networkInterfaces = java.util.Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                List<InetAddress> inetAddresses = java.util.Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddress : inetAddresses) {
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        if (!hostAddress.contains(":")) {
                            return hostAddress;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}