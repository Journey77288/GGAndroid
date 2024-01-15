package io.ganguo.utils.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yanzhenjie.permission.RequestExecutor;

import io.ganguo.utils.R;

/**
 * MaterialDialog 工具类
 * Created by leo on 2018/7/5.
 */
public class DialogHelper {
    /**
     * function：通用弹窗提示基本函数，其他弹窗提示可以基于该函数扩展，统一风格
     *
     * @param context
     * @return
     */
    public static MaterialDialog.Builder baseDialog(Context context) {
        return new MaterialDialog.Builder(context)
                .contentColorRes(R.color.color_333333)
                .widgetColorRes(R.color.color_333333)
                .negativeColorRes(R.color.color_999999)
                .positiveColorRes(R.color.colorPrimary)
                .negativeText(R.string.dialog_cancel)
                .positiveText(R.string.dialog_sure)
                .cancelable(false)
                .title(R.string.dialog_hint);
    }


    /**
     * function：权限申请提示弹窗
     *
     * @param context
     * @return
     */
    public static MaterialDialog.Builder onPermissionDialog(Context context, CharSequence content, final RequestExecutor executor) {
        return baseDialog(context)
                .negativeText(R.string.rationale_dialog_negative)
                .positiveText(R.string.rationale_dialog_positive)
                .content(content)
                .onPositive((dialog, which) -> {
                    if (executor != null) {
                        executor.execute();
                    }
                })
                .cancelListener(dialog -> {
                    if (executor != null) {
                        executor.cancel();
                    }
                });
    }
}
