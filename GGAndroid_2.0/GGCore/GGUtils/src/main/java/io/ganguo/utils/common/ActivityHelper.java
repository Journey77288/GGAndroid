package io.ganguo.utils.common;

import android.app.Activity;
import android.content.Context;
import android.view.Window;

import java.util.Iterator;

import io.ganguo.utils.common.base.BaseActivityHelper;


/**
 * <p>
 * Activity相关工具类
 * <p/>
 * Created by leo on 2018/6/15.
 */
public class ActivityHelper extends BaseActivityHelper {

    /**
     * function：根据context获取Activity Window
     *
     * @param context
     * @return
     */
    public static Window getActivityWindow(Context context) {
        if (!(context instanceof Activity)) {
            return null;
        }
        return ((Activity) context).getWindow();
    }


    /**
     * function：根据class获取Activity
     *
     * @param cls
     * @return
     */
    public <T extends Activity> T get(Class<T> cls) {
        if (isEmpty()) {
            return null;
        }
        Iterator<Activity> inter = iterator();
        while (inter.hasNext()) {
            Activity stack = inter.next();
            if (stack == null) {
                continue;
            }
            if (ClassHelper.isEquals(stack.getClass(), cls)) {
                return (T) stack;
            }
        }
        return null;
    }

    /**
     * function：根据class删除对应的Activity
     *
     * @param cls
     */
    public void remove(Class<? extends Activity> cls) {
        Iterator<Activity> inter = iterator();
        while (inter.hasNext()) {
            Activity stack = inter.next();
            if (stack == null) {
                continue;
            }
            if (ClassHelper.isEquals(stack.getClass(), cls)) {
                inter.remove();
            }
        }
    }


    /**
     * function：根据class类型finish Activity
     *
     * @param cls
     */
    public void finish(Class<? extends Activity> cls) {
        Iterator<Activity> inter = iterator();
        while (inter.hasNext()) {
            Activity stack = inter.next();
            if (stack == null) {
                continue;
            }
            if (ClassHelper.isEquals(stack.getClass(), cls)) {
                inter.remove();
                stack.finish();
            }
        }
    }

    /**
     * function：结束Activity
     *
     * @param activity
     */
    public void finish(Activity activity) {
        if (activity == null) {
            return;
        }
        Iterator<Activity> inter = iterator();
        while (inter.hasNext()) {
            Activity stack = inter.next();
            if (stack == null) {
                continue;
            }
            if (stack.equals(activity)) {
                inter.remove();
            }
        }
        activity.finish();
    }


    /**
     * function：结束activityStack中所有Activity
     */
    public void finishAll() {
        Iterator<Activity> inter = iterator();
        while (inter.hasNext()) {
            Activity stack = inter.next();
            if (stack == null) {
                continue;
            }
            stack.finish();
        }
        clear();
    }

    /**
     * function：结束除cls类型以外的所有Activity
     *
     * @param cls
     */
    public void finishOtherAll(Class<? extends Activity> cls) {
        Iterator<Activity> inter = iterator();
        while (inter.hasNext()) {
            Activity stack = inter.next();
            if (stack == null) {
                continue;
            }
            if (ClassHelper.isEquals(stack.getClass(), cls)) {
                continue;
            }
            inter.remove();
            stack.finish();
        }
    }


    /**
     * function:根据cls类型，判断activityStack中是否包含该Activity
     *
     * @param cls
     * @param
     */
    public boolean isContains(Class<? extends Activity> cls) {
        Iterator<Activity> inter = iterator();
        while (inter.hasNext()) {
            Activity stack = inter.next();
            if (stack == null) {
                continue;
            }
            if (ClassHelper.isEquals(stack.getClass(), cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * function:获取最后一个存放进Stack中的Activity
     *
     * @return
     */
    public Activity lastElement() {
        return isEmpty() ? null : getActivityStack().lastElement();
    }

    /**
     * function:获取第一个存放进Stack中的Activity
     *
     * @return
     */
    public Activity firstElement() {
        return isEmpty() ? null : getActivityStack().firstElement();
    }


}
