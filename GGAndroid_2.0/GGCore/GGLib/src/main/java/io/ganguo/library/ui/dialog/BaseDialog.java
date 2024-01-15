package io.ganguo.library.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import io.ganguo.library.R;
import io.ganguo.utils.common.UIHelper;

/**
 * 对话框 - 基类
 * <p/>
 * Created by zhihui_chen on 14-9-9.
 */
public abstract class BaseDialog extends Dialog implements InitResources {

    public BaseDialog(Context context) {
        this(context, R.style.CustomDialogStyle);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init resources
        beforeInitView();
        initView();
        initListener();
        initData();
        initDialogWindow();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 绑定返回Action
        UIHelper.bindActionBack(this, findViewById(R.id.action_back));
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    /**
     * 对话框大小
     *
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        getWindow().setLayout(width, height);
    }


    /**
     * function: Dialog init
     *
     * @return
     */
    protected void initDialogWindow() {
        if (isFullScreen()) {
            setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }


    /**
     * function: 是否全屏
     *
     * @return
     */
    protected abstract boolean isFullScreen();
}
