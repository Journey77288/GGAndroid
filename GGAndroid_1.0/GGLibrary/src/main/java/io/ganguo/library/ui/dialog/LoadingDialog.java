package io.ganguo.library.ui.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import io.ganguo.library.R;
import io.ganguo.library.ui.extend.BaseDialog;

/**
 * Loading - 自定义加载信息框
 * <p/>
 * Created by tony on 8/25/14.
 */
public class LoadingDialog extends BaseDialog {
    private static LoadingDialog loadingDialog;

    private TextView tvMsg;
    private ImageView loadingImg;
    private String msg;

    public LoadingDialog(Context context, String msg) {
        super(context, R.style.loadingDialog);
        this.msg = msg;
    }

    @Override
    protected void beforeInitView() {
        setContentView(R.layout.dialog_loading);
    }

    @Override
    protected void initView() {
        tvMsg = (TextView) findViewById(R.id.loadingTextView);
        loadingImg = (ImageView) findViewById(R.id.loadingImageView);
    }

    @Override
    protected void initListener() {
        setCancelable(false);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    @Override
    protected void initData() {
        tvMsg.setText(msg);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingImg.getBackground();
        animationDrawable.start();
    }

    /**
     * 显示 - loading
     *
     * @param context
     * @param msg
     * @return
     */
    public static LoadingDialog show(Context context, String msg) {
        dispose();

        loadingDialog = new LoadingDialog(context, msg);
        loadingDialog.show();
        return loadingDialog;
    }

    /**
     * 销毁 - loading
     */
    public static void dispose() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }

}
