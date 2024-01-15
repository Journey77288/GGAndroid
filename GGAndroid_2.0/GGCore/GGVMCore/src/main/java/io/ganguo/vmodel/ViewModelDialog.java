package io.ganguo.vmodel;

import android.content.Context;
import android.databinding.ViewDataBinding;

import io.ganguo.library.ui.dialog.BaseDialog;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.vmodel.view.ViewModelInterface;

/**
 * Created by Roger on 7/13/16.
 */
public abstract class ViewModelDialog<T extends ViewDataBinding, B extends BaseViewModel> extends BaseDialog
        implements BaseViewModel.OnViewAttachListener<B>, ViewModelInterface<B> {

    private B mViewModel;

    @Override
    public boolean checkViewModel() {
        boolean isNotNull = getViewModel() != null;
        if (!isNotNull) {
            Logger.e("viewModel is null");
        }
        return isNotNull;
    }

    public ViewModelDialog(Context context) {
        super(context);
    }

    public ViewModelDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void beforeInitView() {
        ViewModelHelper.bind(this, getViewModel());
        setContentView(getViewModel().getRootView());
        getViewModel().setOnViewAttachListener(this);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {

    }

    @SuppressWarnings("unchecked raw type")
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

    /**
     * Bind ViewModel Lifecycle
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onStop();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onDestroy();
        }
    }

    /**
     * Create ViewModel here
     * if you want to getViewModel instance use {@link #getViewModel()} instead this
     */
    @Override
    public abstract B createViewModel();

    @Override
    protected boolean isFullScreen() {
        return false;
    }
}
