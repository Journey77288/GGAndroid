package io.ganguo.utils.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;

import io.ganguo.utils.bean.Globals;

/**
 * <p>
 * App 状态栏与导航栏相关工具类
 * 注：如app有改变状态栏字体颜色(黑/白)需求，可以考虑 <a helf="http://qmuiteam.com/android/documents">QQ团队开源的工具类</a>方案实现
 * </p>
 * Created by leo on 2018/8/18.
 */
public class AppBars {

    private AppBars() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }


    /**
     * 设置状态栏透明且内容延伸到状态栏
     *
     * @param activity
     */
    public static void statusBarTranslucent(Activity activity) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(Color.TRANSPARENT);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color    颜色值
     * @param activity
     */
    public static void statusBarColor(Activity activity, @ColorInt int color) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }


    /**
     * 设置statusBar主题为亮色 -- 字体图标为黑色
     * Android M之后才支持修改状态栏主题
     *
     * @param activity
     */
    public static void statusBarLightStyle(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(visibility);
        }
    }


    /**
     * 设置statusBar主题为暗色 -- 字体图标为白色
     * Android M之后才支持修改状态栏主题
     *
     * @param activity
     */
    public static void statusBarDarkStyle(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(visibility);
        }
    }


    /**
     * 设置NavigationBar主题为暗色 -- 字体图标为白色
     *
     * @param activity
     */
    public static void navigationBarDarkStyle(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            window.getDecorView().setSystemUiVisibility(visibility);
        }
    }


    /**
     * 设置NavigationBar主题为亮色 -- 字体、图标为黑色
     *
     * @param activity
     */
    public static void navigationBarLightStyle(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            window.getDecorView().setSystemUiVisibility(visibility);
        }
    }


    /**
     * 设置状态栏和导航栏的背景
     *
     * @param activity
     * @param drawable
     */
    public static SystemBarTintManager statusBarDrawable(Activity activity, Drawable drawable) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return tintManager;
        }
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintDrawable(drawable);
        tintManager.setStatusBarTintEnabled(true);
        return tintManager;
    }


    /**
     * 隐藏NavigationBar
     *
     * @param context
     */
    public static void navigationBarHide(Context context) {
        if (!(context instanceof Activity) || ((Activity) context).getWindow().getDecorView() == null) {
            return;
        }
        View view = ((Activity) context).getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            view.setSystemUiVisibility(View.GONE);
            return;
        }
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }


    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        return getStatusBarHeight(context.getResources());
    }

    /**
     * 获取状态栏高度
     *
     * @param resources
     */
    public static int getStatusBarHeight(Resources resources) {
        //默认为38，貌似大部分是这样的
        int x, height = 38;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            height = resources.getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return height;
    }

}
