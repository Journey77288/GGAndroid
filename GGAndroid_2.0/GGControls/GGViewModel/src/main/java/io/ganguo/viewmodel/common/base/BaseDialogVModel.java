package io.ganguo.viewmodel.common.base;

import android.databinding.ObservableInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import io.ganguo.library.ui.view.DialogInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.callback.IDialogViewInterface;
import io.ganguo.viewmodel.databinding.DialogGgBinding;
import io.ganguo.vmodel.BaseViewModel;

/**
 * Dialog ViewModel 基类
 * Created by leo on 2018/11/20.
 */
public abstract class BaseDialogVModel<T extends DialogGgBinding> extends BaseViewModel<DialogInterface<T>> implements IDialogViewInterface {
    public ObservableInt dialogMargin = new ObservableInt();
    public ObservableInt dialogBgRes = new ObservableInt(R.color.transparent);


    @Override
    public void onAttach() {
        super.onAttach();
        initDialogScreenPosition();
        initDialogViews(getContentContainer());
    }

    /**
     * 設置彈窗在屏幕中的位置
     *
     * @return
     */
    protected void initDialogScreenPosition() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getRootViewContainer().getLayoutParams();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.gravity = getLayoutGravity();
    }


    /**
     * 設置彈窗顯示位置
     *
     * @return
     */
    public int getLayoutGravity() {
        return Gravity.CENTER;
    }

    /**
     * 初始化DialogViews
     *
     * @return
     */
    protected void initDialogViews(ViewGroup viewGroup) {
        initHeader(viewGroup);
        initContent(viewGroup);
        initFooter(viewGroup);
    }


    /**
     * 关闭弹窗
     *
     * @return
     */
    public View.OnClickListener onCancelClick() {
        return v -> {
            if (!isCancelable()) {
                return;
            }
            getView().getDialog().dismiss();
        };
    }


    /**
     * bottom container
     *
     * @return
     */
    protected LinearLayout getContentContainer() {
        return getView().getBinding().llContent;
    }


    public BaseDialogVModel<T> setDialogMargin(@Dimension int dialogMargin) {
        this.dialogMargin.set(dialogMargin);
        return this;
    }

    public BaseDialogVModel<T> setDialogBgRes(@DrawableRes int dialogBgRes) {
        this.dialogBgRes.set(dialogBgRes);
        return this;
    }

    /**
     * root view container
     *
     * @return
     */
    public ViewGroup getRootViewContainer() {
        return getView().getBinding().flRoot;
    }


    @Override
    public int getItemLayoutId() {
        return R.layout.dialog_gg;
    }

    /**
     * 是否点击外部关闭弹窗
     *
     * @return
     */
    public boolean isCancelable() {
        return true;
    }
}
