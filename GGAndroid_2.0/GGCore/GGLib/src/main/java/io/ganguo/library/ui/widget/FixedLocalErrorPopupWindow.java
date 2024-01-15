package io.ganguo.library.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/**
 * <p>
 * 修复Android 7.0以上位置显示不正确的PopupWindow
 * </p>
 * Created by leo on 2018/7/20.
 */
public class FixedLocalErrorPopupWindow extends PopupWindow {

    public FixedLocalErrorPopupWindow(Context context) {
        super(context);
    }

    public FixedLocalErrorPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedLocalErrorPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FixedLocalErrorPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FixedLocalErrorPopupWindow() {
    }

    public FixedLocalErrorPopupWindow(View contentView) {
        super(contentView);
    }

    public FixedLocalErrorPopupWindow(int width, int height) {
        super(width, height);
    }

    public FixedLocalErrorPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public FixedLocalErrorPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

}

