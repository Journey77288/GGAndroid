package io.ganguo.utils.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import io.ganguo.utils.R;
import io.ganguo.utils.util.Colors;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.log.Logger;


/**
 * UI 辅助工具
 * <p/>
 * Created by Tony on 9/30/15.
 */
public class UIHelper {

    /**
     * 中止activity运行
     *
     * @param activity
     * @param o
     */
    public static boolean finishActivity(Activity activity, Object o) {
        if (activity != null && o == null) {
            activity.finish();
            return true;
        }
        return false;
    }

    /**
     * 重新启动当前Activity
     *
     * @param activity
     */
    public static void restartActivity(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

    /**
     * 绑定返回 自动finish
     *
     * @param activity
     * @param actionBack
     */
    public static void bindActionBack(final Activity activity, final View actionBack) {
        if (activity == null || actionBack == null) {
            return;
        }
        actionBack.setOnClickListener(v -> {
            //如果用到了transition, 请考虑
            activity.finish();
            //activity.finishAfterTransition();
        });
    }

    /**
     * 绑定返回 自动finish
     *
     * @param dialog
     * @param actionBack
     */
    public static void bindActionBack(final Dialog dialog, View actionBack) {
        if (dialog == null || actionBack == null) {
            return;
        }
        actionBack.setOnClickListener(v -> dialog.dismiss());
    }

    /**
     * 显示一个snackbar
     * 只适用于{@link android.support.v7.app.AppCompatActivity}
     *
     * @param view
     * @param text
     */
    public static void snackBar(View view, String text) {
        snackBar(view, text, null, null);
    }

    /**
     * 显示一个snackbar
     *
     * @param view
     * @param text
     * @param action
     * @param listener
     */
    public static void snackBar(View view, String text, String action, View.OnClickListener listener) {
        try {
            Snackbar sb = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                    .setAction(Strings.isEmpty(action) ? "隐藏" : action, listener == null ? (View.OnClickListener) v -> {
                    } : listener)
                    .setActionTextColor(view.getContext().getResources().getColor(R.color.colorAccent));//action颜色
            //文字颜色
            ((TextView) sb.getView().findViewById(R.id.snackbar_text)).setTextColor(Colors.WHITE);
            //背景色
            //snackBarView.setBackgroundColor(colorId);
            sb.show();
        } catch (NullPointerException e) {
            Log.e("error:", "snackBar: " + "can't not find context of view");
        }
    }


    /**
     * function： get InputMethodManager
     *
     * @param context
     * @return
     */
    public static InputMethodManager getInputManager(Context context) {
        return (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

    /**
     * function：get windowToken
     *
     * @param context
     * @return
     */
    public static IBinder getWindowToken(Context context) {
        IBinder iBinder = ActivityHelper
                .getActivityWindow(context)
                .getCurrentFocus()
                .getWindowToken();
        return iBinder;
    }

    /**
     * 打开软键盘
     *
     * @param context
     * @param view
     */
    public static void showKeyboard(Context context, View view) {
        if (!(context instanceof Activity)) {
            return;
        }
        InputMethodManager inputManager = getInputManager(context);
        if (view != null) {
            inputManager.showSoftInput(view, 0);
        }
        inputManager.showSoftInputFromInputMethod(getWindowToken(context), 0);
    }


    /**
     * 关闭软键盘
     *
     * @param context
     */
    public static void hideKeyboard(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        hideKeyboard(((Activity) context).getCurrentFocus());
    }

    /**
     * 关闭软键盘
     *
     * @param v
     */
    public static void hideKeyboard(View v) {
        if (v == null) {
            return;
        }
        InputMethodManager manager = getInputManager(v.getContext());
        if (v != null) {
            manager.hideSoftInputFromWindow(getWindowToken(v.getContext()), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
