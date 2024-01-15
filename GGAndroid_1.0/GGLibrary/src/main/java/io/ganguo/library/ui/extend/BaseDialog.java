package io.ganguo.library.ui.extend;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import io.ganguo.library.BaseContext;
import io.ganguo.library.R;
import io.ganguo.library.core.event.BusProvider;

/**
 * 对话框 - 基类
 * <p/>
 * Created by zhihui_chen on 14-9-9.
 */
public abstract class BaseDialog extends Dialog implements IContext {

    public BaseDialog(Context context) {
        this(context, R.style.customDialog);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeInitView();
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 返回通过Action
        View actionBack = findViewById(R.id.action_back);
        if (actionBack != null) {
            actionBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        BusProvider.getInstance().unregister(this);
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
     * 获取上下文环境
     *
     * @return
     */
    @Override
    public <T extends BaseContext> T getAppContext() {
        return BaseContext.getInstance();
    }

    /**
     * 加载layout xml
     */
    protected abstract void beforeInitView();

    /**
     * 加载UI
     */
    protected abstract void initView();

    /**
     * 监听控件
     */
    protected abstract void initListener();

    /**
     * 加载网络数据
     */
    protected abstract void initData();
}
