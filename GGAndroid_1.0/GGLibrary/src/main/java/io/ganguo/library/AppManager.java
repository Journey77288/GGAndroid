package io.ganguo.library;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author ZhiHui_Chen
 * @Email: 6208317#qq.com
 * @Date 2013-5-14
 */
public class AppManager {
    private static final String TAG = AppManager.class.getName();

    /**
     * Activity记录栈
     */
    private static Stack<Activity> activityStack = new Stack<Activity>();
    /**
     * AppManager单例
     */
    private static AppManager singleton = new AppManager();

    /**
     * 单例
     */
    private AppManager() {
    }

    /**
     * 获取AppManager单一实例
     */
    public static AppManager getInstance() {
        return singleton;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack.isEmpty()) {
            return;
        }
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定类名的Activity
     */
    public Activity getFirstActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.firstElement();
    }

    /**
     * 结束指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack.isEmpty()) {
            return null;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack.isEmpty()) {
            return;
        }
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack.isEmpty()) {
            return;
        }
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack.isEmpty()) {
            return;
        }
        List<Activity> removed = new LinkedList<>();
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removed.add(activity);
                activity.finish();
            }
        }
        for (Activity activity : removed) {
            activityStack.remove(activity);
        }
        removed.clear();
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack.isEmpty()) {
            return;
        }
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(BaseContext context) {
        try {
            finishAllActivity();
            context.onExit();
        } catch (Exception e) {
            Log.e(TAG, "退出应用失败", e);
        } finally {
            // 不要执行这个，好像对推送有影响
//            incubator.os.Process.killProcess(incubator.os.Process.myPid());
            System.exit(0);
        }
    }
}
