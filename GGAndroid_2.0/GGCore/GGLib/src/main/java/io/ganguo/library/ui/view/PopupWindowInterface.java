package io.ganguo.library.ui.view;

import android.databinding.ViewDataBinding;
import android.widget.PopupWindow;

import io.ganguo.library.ui.widget.BasePopupWindow;


/**
 * <p>
 * PopupWindowInterface
 * </p>
 * Created by leo on 2018/7/20.
 */

public interface PopupWindowInterface<T extends ViewDataBinding> extends ViewInterface<T> {
    BasePopupWindow getPopupWindow();
}
