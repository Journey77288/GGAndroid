package io.ganguo.utils.helper;

import android.app.Application;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.StringRes;

import io.ganguo.utils.util.Dimensions;


/**
 * Toast 辅助类
 * UI 安全
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
    public static void initialize(Application application) {
        appContext = application;
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
     * 显示toast
     *
     * @param strRes String字符串资源Id
     */
    public static void showMessage(@StringRes int strRes) {
        showMessage(ResHelper.getString(strRes), Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast
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
     * @param charSequence String字符串
     */
    public static void showMessage(CharSequence charSequence) {
        showMessage(charSequence, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast
     *
     * @param charSequence 需要显示的内容
     * @param duration     弹窗时长
     */
    public static void showMessage(CharSequence charSequence, int duration) {
        checkInit();
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(appContext, "", Toast.LENGTH_SHORT);
        toast.setText(charSequence);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, Dimensions.dpToPx(appContext, 64));
        toast.show();
    }

}
