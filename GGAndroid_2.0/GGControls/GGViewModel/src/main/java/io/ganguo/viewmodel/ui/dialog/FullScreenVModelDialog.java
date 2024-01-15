package io.ganguo.viewmodel.ui.dialog;

import android.content.Context;
import android.databinding.ViewDataBinding;

import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelDialog;

/**
 * <p>
 * 全屏弹窗 - dialog
 * </p>
 * Created by leo on 2018/9/30.
 */
public abstract class FullScreenVModelDialog<T extends ViewDataBinding, B extends BaseViewModel> extends ViewModelDialog<T, B> {
    public FullScreenVModelDialog(Context context) {
        super(context);
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }
}
