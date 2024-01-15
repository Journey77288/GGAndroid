package io.ganguo.library.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Timer;
import java.util.TimerTask;

import io.ganguo.library.AppManager;
import io.ganguo.library.BaseContext;
import io.ganguo.library.common.UIHelper;

/**
 * 退出快捷工具
 * <p/>
 * Created by tony on 9/28/14.
 */
public class ExitUtil {
    private static boolean isExit = false;

    public static void exitByDialog(final Activity activity) {
        new AlertDialog
                .Builder(activity)
                .setTitle("提醒")
                .setMessage("确定要退出程序？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exit((BaseContext) activity.getApplication());
                    }
                })
                .create()
                .show();
    }

    /**
     * 双击退出函数
     *
     * @param context
     */
    public static void exitByDoublePressed(BaseContext context) {
        if (isExit == false) {
            isExit = true; // 准备退出
            UIHelper.toastMessage(context, "再按一次退出程序");
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            exit(context);
        }
    }

    /**
     * 退出
     *
     * @param context
     */
    public static void exit(BaseContext context) {
        AppManager.getInstance().AppExit((BaseContext) context);
    }
}
