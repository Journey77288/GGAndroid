package io.ganguo.vmodel;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;

import com.afollestad.materialdialogs.MaterialDialog;

import io.ganguo.utils.util.log.Logger;
import io.ganguo.library.ui.view.DialogInterface;
import io.ganguo.vmodel.view.ViewModelInterface;

/**
 * ViewModel for MaterialDialog
 * Created by Roger on 7/25/16.
 */
public abstract class ViewModelMaterialDialog<T extends ViewDataBinding, B extends BaseViewModel>
        implements BaseViewModel.OnViewAttachListener<B>, DialogInterface<T>, ViewModelInterface<B> {

    private static final int MESSAGE_DIALOG_DISMISS = 100;

    private B mViewModel;
    private MaterialDialog dialog;
    private Handler handler;
    private Context mContext;
    private T mBinding;

    @Override
    public boolean checkViewModel() {
        boolean isNotNull = getViewModel() != null;
        if (!isNotNull) {
            Logger.e("viewModel is null");
        }
        return isNotNull;
    }

    public ViewModelMaterialDialog(Context context) {
        this(context, true);
    }

    public ViewModelMaterialDialog(Context context, boolean cancelable) {
        this.mContext = context;
        dialog = new MaterialDialog.Builder(context)
                .cancelable(cancelable)
                .customView(getBinding().getRoot(), false)
                .build();
        dialog.setDismissMessage(getDismissMessage());

        bindViewModel();
    }

    public void bindViewModel() {
        ViewModelHelper.bind((DialogInterface) this, getViewModel());
        getViewModel().setOnViewAttachListener(this);
    }

    private Message getDismissMessage() {
        return Message.obtain(getHandler(), MESSAGE_DIALOG_DISMISS);
    }

    @SuppressWarnings("unchecked raw type")
    public T getBinding() {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getViewModel().getItemLayoutId(), null, false);
        }
        return mBinding;
    }

    @Override
    public B getViewModel() {
        if (mViewModel == null) {
            mViewModel = createViewModel();
        }
        return mViewModel;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public MaterialDialog getDialog() {
        return dialog;
    }

    /**
     * Bind ViewModel Lifecycle
     */
    protected void onDetach() {
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onDestroy();
        }
    }

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case MESSAGE_DIALOG_DISMISS:
                            onDetach();
                            break;
                        default:
                            break;
                    }
                }
            };
        }
        return handler;
    }

    /**
     * Delegates
     */
    @UiThread
    public void show() {
        dialog.show();
    }

    public void cancel() {
        dialog.cancel();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    /**
     * Create ViewModel here
     * if you want to getViewModel instance use {@link #getViewModel()} instead this
     */
    @Override
    public abstract B createViewModel();
}

