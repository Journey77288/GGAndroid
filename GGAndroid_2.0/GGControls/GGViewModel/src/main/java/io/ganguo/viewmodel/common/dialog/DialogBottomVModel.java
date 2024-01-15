package io.ganguo.viewmodel.common.dialog;


import android.view.Gravity;
import android.view.ViewGroup;

import io.ganguo.viewmodel.common.base.BaseDialogVModel;
import io.ganguo.viewmodel.databinding.DialogGgBinding;


/**
 * <p>
 * 底部弹窗
 * </p>
 * Created by leo on 2018/9/18.
 */
public abstract class DialogBottomVModel<T extends DialogGgBinding> extends BaseDialogVModel<T> {
    @Override
    public int getLayoutGravity() {
        return Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    }

    @Override
    public void initHeader(ViewGroup container) {

    }

    @Override
    public void initFooter(ViewGroup container) {

    }

    @Override
    public void initContent(ViewGroup container) {

    }
}
