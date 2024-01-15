package io.ganguo.viewmodel.ui.dialog;

import android.content.Context;
import android.databinding.ViewDataBinding;

import io.ganguo.vmodel.BaseViewModel;

/**
 * <p>
 * 底部弹窗 - dialog
 * </p>
 * Created by leo on 2018/9/30.
 */
public abstract class BottomVModelDialog<T extends ViewDataBinding, B extends BaseViewModel> extends FullScreenVModelDialog<T, B> {
    public BottomVModelDialog(Context context) {
        super(context);
    }

}
