package io.ganguo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

import io.ganguo.utils.common.ActivityHelper;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.log.Logger;

/**
 * App Activity - 堆栈管理工具类
 * Created by leo on 2018/6/15.
 */
public class AppManager {
    public static final ActivityHelper activityHelper = new ActivityHelper();

    public static ActivityHelper getActivityHelper() {
        return activityHelper;
    }


    /**
     * function:获取栈顶的Activity(当前显示的Activity)
     *
     * @return
     */
    public static Activity currentActivity() {
        return activityHelper.lastElement();
    }


    /**
     * function:获取当前显示的Activity的前一个Activity
     *
     * @return
     */
    public static Activity beforeActivity() {
        if (activityHelper.isEmpty()) {
            return null;
        }
        if (activityHelper.size() < 2) {
            return null;
        }
        int currentIndex = activityHelper.indexOf(currentActivity());
        int beforeIndex = currentIndex - 1;
        return (beforeIndex < 0 || beforeIndex >= activityHelper.size()) ? null : activityHelper.get(beforeIndex);
    }


    /**
     * function:获取栈底的Activity(一般是启动页/首页)
     *
     * @return
     */
    public static Activity stackBottomActivity() {
        return activityHelper.firstElement();
    }

    /**
     * 结束指定类名的Activity
     */
    public static Activity getActivity(Class<? extends Activity> cls) {
        return activityHelper.get(cls);
    }


    /**
     * function:添加Activity到堆栈
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activityHelper.add(activity);
    }

    /**
     * 移除指定的Activity
     *
     * @param activity
     * @return
     */
    public static boolean removeActivity(Activity activity) {
        return activityHelper.remove(activity);
    }


    /**
     * function：判断是否存在cls类型的Activity
     *
     * @param cls
     * @return
     */
    public static boolean isContainsActivity(Class<? extends Activity> cls) {
        return activityHelper.isContains(cls);
    }

    /**
     * function：结束指定类名的Activity
     *
     * @param cls
     */
    public static void finishActivity(Class<? extends Activity> cls) {
        activityHelper.finish(cls);
    }

    /**
     * function：结束指定Activity
     *
     * @param activity
     */
    public static void finishActivity(Activity activity) {
        activityHelper.finish(activity);
    }

    /**
     * function：结束堆栈中所有Activity
     */
    public static void finishAllActivity() {
        activityHelper.finishAll();
    }


    /**
     * function：结束堆栈中，除cls以外的所有Activity
     *
     * @param cls
     */
    public static void finishOtherAllActivity(Class<? extends Activity> cls) {
        activityHelper.finishOtherAll(cls);
    }

}
