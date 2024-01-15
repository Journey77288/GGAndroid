package io.ganguo.vmodel;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.view.ViewGroup;

import io.ganguo.library.R;
import io.ganguo.library.ui.widget.BasePopupWindow;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.vmodel.callback.IPopupWindowInterface;
import io.ganguo.vmodel.view.ViewModelInterface;

/**
 * <p>
 * ViewModel - PopupWindow
 * </p>
 * Created by leo on 2018/7/20.
 */
public abstract class ViewModelPopupWindow<T extends ViewDataBinding, B extends BaseViewModel> extends BasePopupWindow implements
        BaseViewModel.OnViewAttachListener<B>, ViewModelInterface<B>, IPopupWindowInterface {
    private B mViewModel;

    public ViewModelPopupWindow(Context context) {
        super(context);
    }


    public Context getContext() {
        return context;
    }

    public T getBinding() {
        return (T) getViewModel().getView().getBinding();
    }

    @Override
    public B getViewModel() {
        if (mViewModel == null) {
            mViewModel = createViewModel();
        }
        return mViewModel;
    }


    @Override
    public boolean checkViewModel() {
        boolean isNotNull = getViewModel() != null;
        if (!isNotNull) {
            Logger.e("viewModel is null");
        }
        return isNotNull;
    }


    @Override
    public void beforeInitView() {
        ViewModelHelper.bind(this, getViewModel());
        setContentView(getViewModel().getRootView());
    }


    @Override
    public void initView() {
        setWindowSize();
        initTouchEvent();
        setAnimationStyle(R.style.popupWindowAnimStyle);
    }


    @Override
    public void initListener() {
        getViewModel().setOnViewAttachListener(this);
    }

    @Override
    public void initData() {

    }

    /**
     * function：处理Touch Event事件
     *
     * @return
     */
    protected void initTouchEvent() {
        if (isTouchOutsideDismiss()) {
            setBackgroundDrawable(new AnimationDrawable());
            setOutsideTouchable(true);
            setFocusable(true);
        }
    }


    /**
     * function：set window size
     *
     * @return
     */
    protected void setWindowSize() {
        if (isWindowWidthFull()) {
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (isWindowHeightFull()) {
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dismiss();
    }
}
