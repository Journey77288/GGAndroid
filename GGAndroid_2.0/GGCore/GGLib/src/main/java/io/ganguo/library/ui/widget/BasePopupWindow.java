package io.ganguo.library.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import io.ganguo.library.ui.dialog.InitResources;

/**
 * <p>
 * PopupWindow - 基类
 * </p>
 * Created by leo on 2018/7/20.
 */
public abstract class BasePopupWindow extends FixedLocalErrorPopupWindow implements InitResources {
    protected Context context;

    private BasePopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    private BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public Context getContext() {
        return context;
    }

    private BasePopupWindow() {
    }

    private BasePopupWindow(View contentView) {
        super(contentView);
    }

    private BasePopupWindow(int width, int height) {
        super(width, height);
    }

    private BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    private BasePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public BasePopupWindow(Context context) {
        this(context, null);
        this.context = context;
    }

    @Override
    public void showAsDropDown(View anchor) {
        beforeInitView();
        initView();
        initListener();
        initData();
        super.showAsDropDown(anchor);
    }
}
