package io.ganguo.utils.common;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.util.DisplayMetrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.ganguo.utils.util.AppBars;
import io.ganguo.utils.util.Screens;
import io.ganguo.utils.util.Strings;


/**
 * <p>
 * <a helf="https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA">实现方案相关文章</a>
 * 屏幕适配工具类
 * 1、使用时需要在AppContext中init
 * 2、BaseActivity已经引入该工具类
 * 3、布局文件设置尺寸和之前方式一样，直接使用即可。例如@dimen/dp_5
 * 4、java代码中需要获取dimen资源Id的参数，使用ResHelper中的方法进行获取，避免出现使用AppContext获取dimen参数
 * </p>
 * Created by leo on 2018/8/4.
 */
public class ScreenAdaptors implements ComponentCallbacks {
    public static final String ORIENTATION_WIDTH = "width";//以屏幕宽度为基准
    public static final String ORIENTATION_HEIGHT = "height";//以屏幕高度为基准
    //以下基准值都是基于720x1280尺寸的设计为基准，可以根据设计进行调整，360f表示设计尺寸宽度是360dp
    public static final float STANDARD_WIDTH = 360f;//设计宽度
    public static final float STANDARD_HEIGHT = 667f;//设计高度，去除了statusHeight
    private Application mContext;
    private DisplayMetrics displayMetrics;
    private float density;
    private float scaledDensity;
    private int statusHeight;

    private ScreenAdaptors() {
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef(value = {ORIENTATION_WIDTH, ORIENTATION_HEIGHT})
    public @interface OrientationType {

    }

    /**
     * 单例- 饿汉式
     *
     * @return
     */
    public static ScreenAdaptors get() {
        return Holder.HOLDER;
    }


    /**
     * 单例[饿汉式] -懒加载
     */
    static class Holder {
        static ScreenAdaptors HOLDER = new ScreenAdaptors();
    }

    /**
     * function: init
     * <p>
     * 获取一些基本配置
     * <p/>
     */
    public static void init(Application application) {
        if (application == null) {
            return;
        }
        get().setContext(application);
        DisplayMetrics displayMetrics = Screens.getDisplayMetrics(application);
        int statusHeight = AppBars.getStatusBarHeight(application);
        float density = displayMetrics.density;
        float scaledDensity = displayMetrics.scaledDensity;
        get().setDensity(density);
        get().setDisplayMetrics(displayMetrics);
        get().setStatusHeight(statusHeight);
        get().setScaledDensity(scaledDensity);
        application.registerComponentCallbacks(get());
    }

    /**
     * function：对Activity界面进行绑定适配，默认以宽度进行适配
     *
     * @param activity
     */
    public static void onBindScreenAdapter(Activity activity) {
        onBindScreenAdapter(activity, ORIENTATION_WIDTH);
    }

    /**
     * function：对Activity界面进行绑定适配
     *
     * @param activity
     * @param orientation
     */
    public static void onBindScreenAdapter(Activity activity, @OrientationType String orientation) {
        get().onChangeDisplayMetricsValue(activity, orientation);
    }


    /**
     * @param context
     * @param orientation
     */
    private void onChangeDisplayMetricsValue(@Nullable Context context, @OrientationType String orientation) {
        //以ORIENTATION_HEIGHT/STANDARD_WIDTH 为基准，重新计算density，scaledDensity，densityDpi
        float targetDensity;
        if (Strings.isEquals(orientation, ORIENTATION_HEIGHT)) {
            targetDensity = (displayMetrics.heightPixels - statusHeight) / STANDARD_HEIGHT;
        } else {
            targetDensity = displayMetrics.widthPixels / STANDARD_WIDTH;
        }
        float targetScaledDensity = targetDensity * (scaledDensity / density);
        int targetDensityDpi = (int) (160 * targetDensity);
        //将计算过的targetDensity、targetScaledDensity以及targetDensityDpi的值，赋值给系统
        DisplayMetrics activityDisplayMetrics = context.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }


    /**
     * function: 字体变化监听
     *
     * @return
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //字体改变后,将appScaledDensity重新赋值
        if (newConfig == null && newConfig.fontScale <= 0) {
            return;
        }
        setScaledDensity(Screens.getScaledDensity(mContext));
    }


    /**
     * function: 字体 scaledDensity
     *
     * @param scaledDensity
     */
    public void setScaledDensity(float scaledDensity) {
        this.scaledDensity = scaledDensity;
    }


    public ScreenAdaptors setContext(Application mContext) {
        this.mContext = mContext;
        return this;
    }

    public ScreenAdaptors setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
        return this;
    }

    public ScreenAdaptors setDensity(float density) {
        this.density = density;
        return this;
    }

    public ScreenAdaptors setStatusHeight(int statusHeight) {
        this.statusHeight = statusHeight;
        return this;
    }

    @Override
    public void onLowMemory() {

    }
}
