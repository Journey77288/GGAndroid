package io.ganguo.incubator.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import io.ganguo.incubator.AppContext;
import io.ganguo.incubator.bean.Constants;
import io.ganguo.incubator.ui.activity.LoginActivity;

/**
 * Created by Wilson on 29/1/15.
 */
public class LoginDialog {

    /**
     * 提示登录框
     */
    public static boolean show(final Activity activity, final Intent target) {
        if (AppContext.getInstance().isLogined()) {
            return false;
        }
        new AlertDialog
                .Builder(activity)
                .setTitle("提醒")
                .setMessage("您没有登录，请登录后再操作")
                .setNegativeButton("取消", null)
                .setPositiveButton("马上登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.PARAM_TO_ACTIVITY, target);
                        intent.setClass(activity, LoginActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .create()
                .show();
        return true;
    }

    /**
     * 提示登录框
     */
    public static boolean show(final Activity activity) {
        return show(activity, null);
    }
}
