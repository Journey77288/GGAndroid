package io.ganguo.utils.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

import io.ganguo.log.core.Logger;
import io.ganguo.utils.R;
import io.ganguo.utils.bean.Globals;
import io.ganguo.utils.helper.ToastHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * <p>
 * APK/APP 信息工具类
 * </p>
 * Created by leo on 2018/8/18.
 */
public class Apps {
    protected static boolean isExit = false;
    public static final String KEY_PACKAGE = "package";
    public static final String TYPE_PACKAGE_ARCHIVE = "application/vnd.android.package-archive";

    private Apps() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * whether application is in background
     * <ul>
     * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
     * </ul>
     *
     * @param context 上下文
     * @return if application is in background return true, otherwise return
     * false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList == null || Collections.isEmpty(taskList)) {
            return false;
        }
        ComponentName topActivity = taskList.get(0).topActivity;
        if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
            return true;
        }
        return false;
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件
     */
    public static void installApk(Context context, File file) {
        installApk(context, Uri.fromFile(file));
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件uri
     */
    public static void installApk(Context context, Uri file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(file, TYPE_PACKAGE_ARCHIVE);
        context.startActivity(intent);
    }

    /**
     * 卸载apk
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse(KEY_PACKAGE + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }


    /**
     * 退出应用程序
     */
    public static void exitApp(Context context) {
        toHome(context);
        TasksKt.postDelayed(context, 200, new Function1<Context, Unit>() {
            @Override
            public Unit invoke(Context context) {
                try {
                    // TODO: 2020/7/9  关闭程序
//                    Activit.INSTANCE.finishAllActivity();
                } catch (Exception e) {
                    Logger.INSTANCE.w("Exit application failure", e);
                } finally {
                    System.exit(0);
                }
                return Unit.INSTANCE;
            }
        });
    }


    /**
     * 双击退出函数
     *
     * @param context
     */
    public static void exitByDoublePressed(Context context) {
        if (isExit == false) {
            // 准备退出
            isExit = true;
            ToastHelper.showMessage(R.string.dialog_exit_press_message);
            //使用count down timer更简洁，且避免内存泄露
            getExitCDownTimer().start();
        } else {
            exitApp(context);
        }
    }

    /**
     * 实现2秒定时
     * 使用count down timer更简洁，且避免内存泄露
     *
     * @return
     */
    protected static CountDownTimer getExitCDownTimer() {
        return new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                // 如果2秒钟内没有按下返回键，则取消退出状态
                isExit = false;
            }
        };
    }


    /**
     * 设置页面全屏
     *
     * @param activity
     */
    public static void fullscreen(AppCompatActivity activity) {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        //隐藏标题栏
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
        //专门设置一下状态栏导航栏背景颜色为透明，凸显效果。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 跳转到主屏幕
     *
     * @param context
     */
    public static void toHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }
}
