package io.ganguo.viewmodel.pack.common.base;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.databinding.ObservableInt;

import io.ganguo.viewmodel.core.viewinterface.DialogInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.pack.interfaces.view.IDialogViewInterface;
import io.ganguo.viewmodel.databinding.DialogGgBinding;
import io.ganguo.viewmodel.core.BaseViewModel;

/**
 * Dialog ViewModel 基类
 * Created by leo on 2018/11/20.
 */
public abstract class BaseDialogVModel<T extends DialogGgBinding, V extends DialogInterface<T>> extends BaseViewModel<V> implements IDialogViewInterface {
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
        getRootViewContainer().setLayoutParams(params);
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
            getViewInterface().getDialog().dismiss();
        };
    }


    /**
     * bottom container
     *
     * @return
     */
    protected LinearLayout getContentContainer() {
        return getViewInterface().getBinding().llContent;
    }


    /**
     * root view container
     *
     * @return
     */
    public ViewGroup getRootViewContainer() {
        return getViewInterface().getBinding().flRoot;
    }


    @Override
    public int getLayoutId() {
        return R.layout.dialog_gg;
    }

    /**
     * 是否点击外部关闭弹窗
     *
     * @return
     */
    public boolean isCancelable() {
        return false;
    }
}
