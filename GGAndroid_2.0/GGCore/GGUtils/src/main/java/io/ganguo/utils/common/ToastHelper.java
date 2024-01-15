package io.ganguo.utils.common;

import android.app.Application;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import io.ganguo.utils.util.Dimensions;


/**
 * Toast 辅助类
 * # UI 安全
 * <p/>
 * Update by Leo on 14/11/18.
 */
public class ToastHelper {
    private static Application appContext;
    private static Toast toast;

    /**
     * Toast init
     *
     * @param application appContext 上下文
     */
    public static void init(Application application) {
        appContext = application;
        toast = Toast.makeText(appContext, "", Toast.LENGTH_SHORT);

    }

    /**
     * check is init
     */
    protected static void checkInit() {
        if (appContext == null) {
            throw new RuntimeException("Please initialize at Application");
        }
    }

    /**
     * 显示Toast
     *
     * @param strRes String字符串资源Id
     */
    public static void showMessage(@StringRes int strRes) {
        showMessage(ResHelper.getString(strRes));
    }

    /**
     * 显示Toast
     *
     * @param strRes   String字符串资源Id
     * @param duration Toast.LENGTH_SHORT | Toast.LENGTH_LONG
     */
    public static void showMessage(@StringRes int strRes, int duration) {
        showMessage(ResHelper.getString(strRes), duration);
    }

    /**
     * 显示Toast
     *
     * @param charSequence 需要显示的内容
     * @param duration     显示时长
     */
    public static void showMessage(CharSequence charSequence, int duration) {
        checkInit();
        toast.setText(charSequence);
        toast.setDuration(duration);
        cancel();
        toast.show();
    }

    /**
     * 取消所有toast
     */
    public static void cancel() {
        checkInit();
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 弹出Toast消息
     *
     * @param charSequence
     */
    public static void showMessage(final CharSequence charSequence) {
        checkInit();
        toast.setText(charSequence);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, Dimensions.dpToPx(appContext, 64));
        toast.show();
    }

}
