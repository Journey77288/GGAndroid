package io.ganguo.library.common;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import io.ganguo.library.ui.dialog.LoadingDialog;


/**
 * 界面辅助工具
 * <p/>
 * Created by zhihui_chen on 14-8-4.
 */
public class UIHelper {
    private static Toast toast;

    /**
     * 弹出Toast消息
     *
     * @param charSequence
     */
    public static void toastMessage(Context context, CharSequence charSequence) {
        if (toast == null) {
            toast = Toast.makeText(context, charSequence, Toast.LENGTH_SHORT);
        } else {
            toast.setText(charSequence);
        }
        toast.show();
    }

    /**
     * 弹出Toast消息
     *
     * @param charSequence
     */
    public static void toastMessageMiddle(Context context, CharSequence charSequence) {
        if (toast == null) {
            toast = Toast.makeText(context, charSequence, Toast.LENGTH_SHORT);
        } else {
            toast.setText(charSequence);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 弹出Toast消息
     *
     * @param resId
     */
    public static void toastMessageMiddle(Context context, int resId) {
        toastMessageMiddle(context, context.getResources().getText(resId));
    }

    /**
     * 资源ID信息显示
     *
     * @param context
     * @param resId
     */
    public static void toastMessage(Context context, int resId) {
        toastMessage(context, context.getResources().getText(resId));
    }

    /**
     * 指定消息显示时间
     *
     * @param context
     * @param charSequence
     * @param time
     */
    public static void toastMessage(Context context, CharSequence charSequence, int time) {
        if (toast == null) {
            toast = Toast.makeText(context, charSequence, time);
        } else {
            toast.setText(charSequence);
        }
        toast.show();
    }

    /**
     * 当ScrollView与ListView冲突的时候，用这个方法设置listView的高
     *
     * @param listView
     */
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * show loading
     */
    public static void showLoading(Activity context, String msg) {
        LoadingDialog.show(context, msg);
    }

    /**
     * hide loading
     */
    public static void hideLoading() {
        LoadingDialog.dispose();
    }

    static MaterialDialog mMaterialDialog = null;

    /**
     * show loading
     */
    public static void showMaterLoading(Activity context, String msg) {
        if (mMaterialDialog != null) {
            hideMaterLoading();
        }
        mMaterialDialog = new MaterialDialog.Builder(context)
                .progress(true, 0)
                .content(msg)
                .show();
    }

    /**
     * hide loading
     */
    public static void hideMaterLoading() {
        if (mMaterialDialog != null && mMaterialDialog.isShowing()) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }

}
