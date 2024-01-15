package io.ganguo.library.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import io.ganguo.library.Config;
import io.ganguo.library.common.UIHelper;


/**
 * Android 工具类
 * <p/>
 * Created by zhihui_chen on 14-8-4.
 */
public class AndroidUtils {
    private static final String TAG = AndroidUtils.class.getName();
    private static final String CONFIG_UNIQUE_ID = "config_unique_id";

    /**
     * 单位转换
     *
     * @param context
     * @param unit    TypedValue.COMPLEX_UNIT_DIP
     * @param value   px
     * @return
     */
    public static float applyDimension(Context context, int unit, float value) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(unit, value, displayMetrics);
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 打开软键盘
     *
     * @param window
     */
    public static void showSoftKeyBoard(final Window window) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (window.getCurrentFocus() != null) {
                    InputMethodManager inputManager = (InputMethodManager) window.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInputFromInputMethod(window.getCurrentFocus().getWindowToken(), 0);
                }
            }
        }, 200);
    }

    /**
     * 关闭软键盘
     *
     * @param window
     */
    public static void hideSoftKeyBoard(final Window window) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (window.getCurrentFocus() != null) {
                    InputMethodManager inputManager = (InputMethodManager) window.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), 0);
                }
            }
        }, 200);
    }

    /**
     * ∆
     * 强制关闭软键盘（如果上方的不管用，可以试试这个）
     *
     * @param window
     */
    public static void forceHideKeyboard(final Window window) {
        try {
            // Check if no view has focus:
            View view = window.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) window.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            Log.e(TAG, "forceHideKeyboard failed:", e);
        }
    }

    //此方法只是关闭软键盘
    public static void forceHideSoftKeyBoard(final Window window) {
        InputMethodManager imm = (InputMethodManager) window.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && window.getCurrentFocus() != null) {
            if (window.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * dp to px
     *
     * @param dp
     * @param context
     * @return
     */
    public static int dpToPx(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    /**
     * 安装apk
     *
     * @param apkFilePath
     */
    public static void installApk(Context ctx, String apkFilePath) {
        File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            UIHelper.toastMessage(ctx, "安装主程序失败，找不到主程序文件，请尝试重新更新。");
            return;
        }
        Log.d(TAG, "install apk: " + apkfile.getPath());
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.incubator.package-archive");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "程序包名无法找到", e);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 获取当前程序版本名称
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // Get the package info
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "获取当前程序版本名称", e);
        }
        return versionName;
    }

    /**
     * 获取程序版本名称
     */
    public static String getAppVersionName(Context context, String packageName) {
        String versionName;
        try {
            // Get the package info
            PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "获取程序版本名称", e);
            return "";
        }
        return versionName;
    }

    /**
     * 获取当前代码版本号
     */
    public static int getAppVersionCode(Context context) {
        int localVersion = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            localVersion = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "获取当前代码版本号", e);
        }
        return localVersion;
    }

    /**
     * 比较两个app版本号，看是否需要更新
     *
     * @param targetVersion
     * @param baseVersion
     * @return true if targetVersion > baseVersion,return true if haven't
     * install
     */
    public static boolean needToUpdate(String targetVersion, String baseVersion) {
        if (StringUtils.isEmpty(targetVersion)) {
            return false;// empty target, return false
        }
        if (StringUtils.isEmpty(baseVersion)) {
            return true;// not install, return true
        }
        List<String> targetItems = StringUtils.stringToList(targetVersion, StringUtils.VERSION_SEPERATOR);
        List<String> baseItems = StringUtils.stringToList(baseVersion, StringUtils.VERSION_SEPERATOR);
        Log.e("targetItems", targetItems.toString());
        Log.e("baseItems", baseItems.toString());

        if (CollectionUtils.isEmpty(targetItems) || CollectionUtils.isEmpty(baseItems)) {
            return false;
        }

        final int targetSize = targetItems.size();
        final int baseSize = baseItems.size();
        final int total = targetSize > baseSize ? targetSize : baseSize;

        for (int i = 0; i < total; i++) {
            int targetV = (i >= targetSize) ? 0 : Integer.parseInt(targetItems.get(i));
            int baseV = (i >= baseSize) ? 0 : Integer.parseInt(baseItems.get(i));
            if (targetV > baseV) {
                return true;
            }
            if (targetV < baseV) {
                return false;
            }

        }
        return false;
    }

    /**
     * 获取当前android手机型号
     */
    public static String getDriverModel() {
        String DeviceModel;
        DeviceModel = Build.MODEL;
        if (TextUtils.isEmpty(DeviceModel)) {
            return "";
        }
        return DeviceModel;
    }

    /**
     * 获取当前系统版本号
     */
    public static String getDriverVersionName() {
        String DeviceVersionName;
        DeviceVersionName = Build.VERSION.RELEASE;
        if (TextUtils.isEmpty(DeviceVersionName)) {
            return "";
        }
        return DeviceVersionName;
    }

    /**
     * 获取手机的IMEI码
     *
     * @return
     */
    public static String getIMEICode(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // check if has the permission
        if (PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(Manifest.permission.READ_PHONE_STATE, context.getPackageName())) {
            return tm.getDeviceId();
        } else {
            return null;
        }
    }

    /**
     * 获取设备唯一码（首先使用本地IMEI，如果获取失败使用保存的UUID）
     * UUID不带 “-”
     *
     * @param context
     * @return
     */
    public static String getUniqueID(Context context) {
        String uniqueID = getIMEICode(context);
        if (StringUtils.isEmpty(uniqueID)) {
            if (!Config.containsKey(CONFIG_UNIQUE_ID)) {
                Config.putString(CONFIG_UNIQUE_ID, UUID.randomUUID().toString().replaceAll("-", ""));
            }
            uniqueID = Config.getString(CONFIG_UNIQUE_ID);
        }
        return uniqueID;
    }

    /**
     * 设置状态栏和导航栏透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void setBarTranslucent(Activity activity) {
        setStatusTranslucent(activity);
        setNavigationTranslucent(activity);
    }

    /**
     * 设置导航栏透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void setNavigationTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        AndroidUtils.setNavigationColor(activity, Color.TRANSPARENT);
    }

    /**
     * 设置状态栏透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void setStatusTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        AndroidUtils.setStatusColor(activity, Color.TRANSPARENT);
    }

    /**
     * 设置状态栏和导航栏的颜色
     *
     * @param activity
     * @param color
     */
    @TargetApi(19)
    public static void setBarColor(Activity activity, int color) {
        if (activity == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;

        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        window.setAttributes(winParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            window.setNavigationBarColor(color);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(color);
    }

    /**
     * 设置状态栏和导航栏的颜色
     *
     * @param activity
     * @param color
     */
    @TargetApi(21)
    public static void setStatusColor(Activity activity, int color) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }

    /**
     * 设置状态栏和导航栏的颜色
     *
     * @param activity
     * @param color
     */
    @TargetApi(21)
    public static void setNavigationColor(Activity activity, int color) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        winParams.flags |= bits;
        window.setAttributes(winParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(color);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintColor(color);
    }

    /**
     * 获取context所对应的BuildConfig的参数
     * 如果PackageName会发生改变的话，可以通过定义string{applicationId}的方式来获取
     * http://stackoverflow.com/questions/21365928/gradle-how-to-use-buildconfig-in-an-android-library-with-a-flag-that-gets-set/31587838#31587838
     * <p/>
     * Gets a field from the project's BuildConfig. This is useful when, for example, flavors
     * are used at the project level to set custom fields.
     */
    public static <V> V getBuildConfigValue(String defaultPackageName, String fieldName) {
        try {
            Class<?> clazz = Class.forName(defaultPackageName + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return (V) field.get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
