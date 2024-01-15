package io.ganguo.incubator.ui.dialog;

import android.content.Context;

import io.ganguo.library.ui.extend.BaseDialog;

/**
 * Created by Tony on 3/4/15.
 */
public class DemoDialgo extends BaseDialog {

    public DemoDialgo(Context context) {
        super(context);
    }

    public DemoDialgo(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 加载layout xml
     */
    @Override
    protected void beforeInitView() {
//        setContentView(R.layout.dialog_xxx);
    }

    /**
     * 加载UI
     */
    @Override
    protected void initView() {

    }

    /**
     * 监听控件
     */
    @Override
    protected void initListener() {

    }

    /**
     * 加载网络数据
     */
    @Override
    protected void initData() {

    }

}
