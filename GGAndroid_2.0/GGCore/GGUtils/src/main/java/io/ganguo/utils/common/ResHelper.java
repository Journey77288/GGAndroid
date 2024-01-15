package io.ganguo.utils.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import io.ganguo.utils.AppManager;

/**
 * function：Resources资源工具类
 * Created by leo on 2018/5/28.
 */
public class ResHelper {
    private Context context;

    private ResHelper() {
    }


    /**
     * function：init 一般在AppContext中调用该函数
     *
     * @param context
     */
    public static ResHelper init(Context context) {
        if (get().getContext() != null) {
            return get();
        }
        get().setContext(context.getApplicationContext());
        return get();
    }

    /**
     * function：Context
     *
     * @param context
     * @return
     */
    protected ResHelper setContext(Context context) {
        this.context = context;
        return this;
    }


    /**
     * function：单例
     * <p>
     * 懒汉式 - 使用了LazyHolder，只有被调用时，ResHelper才会被实例化，避免内存消耗，保证线程安全
     * </p>
     *
     * @return
     */
    protected static ResHelper get() {
        return LazyHolder.helper;
    }


    static class LazyHolder {
        static volatile ResHelper helper = new ResHelper();
    }


    /**
     * 通过资源id，获取Drawable
     *
     * @param id 资源id
     */
    public static Drawable getDrawable(@DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return get().getResources().getDrawable(id, null);
        }
        return get().getResources().getDrawable(id);
    }


    /**
     * 通过资源Id，获取颜色值
     *
     * @param id 资源id
     */
    public static int getColor(@ColorRes int id) {
        return get().getResources().getColor(id);
    }


    /**
     * 通过资源Id，获取颜色值
     *
     * @param id 资源id
     */
    public static String getString(@StringRes int id) {
        return get().getResources().getString(id);
    }


    /**
     * 格式化字符串数据
     *
     * @param id         资源id
     * @param formatArgs 需要被格式化的数据
     * @return
     */
    public static String getString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    /**
     * 获取字符串数组资源
     *
     * @param resId 资源id
     * @return
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取Dimension
     *
     * @param id 资源id
     * @return
     */
    public static int getDimensionPixelOffsets(@DimenRes int id) {
        return getResources().getDimensionPixelOffset(id);
    }

    /**
     * 获取Dimension
     *
     * @param id 资源id
     * @return
     */
    public static float getDimension(@DimenRes int id) {
        return getResources().getDimension(id);
    }


    /**
     * 获取Dimension
     *
     * @param id 资源id
     * @return
     */
    public static int getDimensionPixelSize(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }


    /**
     * @return ResourcesDelegate to access Applications resources, return null when ViewModel is not attached
     */
    public static Resources getResources() {
        return get().getContextResources();
    }

    /**
     * @return ResourcesDelegate to access Applications resources, return null when ViewModel is not attached
     */
    public Resources getContextResources() {
        if (getContext() == null) {
            setContext(AppManager.currentActivity().getApplicationContext());
        }
        return getContext().getResources();
    }

    /**
     * @return Context
     */
    public Context getContext() {
        return context;
    }
}
