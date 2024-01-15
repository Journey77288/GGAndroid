package io.ganguo.vmodel;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import io.ganguo.library.BR;
import io.ganguo.library.ui.adapter.v7.hodler.LayoutId;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.vmodel.view.AdapterInterface;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * Base ViewModel class
 * <p/>
 * 用法:
 * 1、绑定include layout
 * 创建 ViewModel 实例与 layout 中的includeLayout绑定
 * 注意:includeLayout 的布局必须与 ViewModel 的 LayoutId 相同
 * viewModel = new ViewModel(getContext());
 * ViewModelHelper.bind(getView().getBinding().includeLayout, viewModel);
 * <p/>
 * 2、直接与Activity、Fragment、Dialog、View绑定,设置viewModel的LayoutId为根布局
 * viewModel = new ViewModel(getContext());
 * ViewModelHelper.bind(this, viewModel);
 * Activity:
 * DataBindingUtil.setContentView(this, viewModel.getItemLayoutId());
 * Fragment:
 * public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 * return viewModel.getRootView();
 * }
 * Dialog: see example{@link io.ganguo.library.ui.dialog}
 * <p/>
 * Created by Roger on 5/29/16.
 */
public abstract class BaseViewModel<V extends ViewInterface> extends BaseObservable implements LayoutId {

    /**
     * 用于绑定Observable生命周期事件
     *
     * @see {@link io.ganguo.vmodel.rx.RxVMLifecycle#bindSingleViewModel(BaseViewModel)}
     */
    private VMLifecycleHelper lifecycleHelper;

    private V view;
    private OnViewAttachListener onViewAttachListener;
    private int variableId;

    public BaseViewModel() {
        lifecycleHelper = VMLifecycleHelper.register(this);
    }

    /**
     * Attach View
     *
     * @param view       {@link ViewInterface}
     * @param variableId variableId in the xml
     */
    @SuppressWarnings("unchecked")
    public void attach(V view, int variableId) {
        this.view = view;
        this.variableId = variableId;
        onAttach();
        onViewAttached(view.getBinding().getRoot());
        if (onViewAttachListener != null) {
            onViewAttachListener.onViewAttached(this);
        }
    }

    /**
     * set binding data on attach
     */
    public void onAttach() {
        if (view == null) {
            throw new NullPointerException("attach view failed, view is null");
        }
        if (view instanceof AdapterInterface) {
            AdapterInterface adapterView = (AdapterInterface) view;
            view.getBinding().setVariable(BR.vh, adapterView.getViewHolder());
            view.getBinding().setVariable(BR.adapter, adapterView.getAdapter());
        }
        view.getBinding().setVariable(getVariableId(), this);
    }

    public void setOnViewAttachListener(@Nullable OnViewAttachListener onViewAttachListener) {
        this.onViewAttachListener = onViewAttachListener;
    }

    public OnViewAttachListener getOnViewAttachListener() {
        return onViewAttachListener;
    }

    public View getRootView() {
        if (getView() == null || getView().getBinding() == null) {
            return null;
        }
        return getView().getBinding().getRoot();
    }

    public Context getContext() {
        if (view == null) {
            return null;
        }
        return view.getContext();
    }

    /**
     * @return ResourcesDelegate to access Applications resources, return null when ViewModel is not attached
     */
    public Resources getResources() {
        return ResHelper.getResources();
    }

    public V getView() {
        return view;
    }

    public int getVariableId() {
        return variableId;
    }

    /**
     * Set VariableId before attach
     */
    public void setVariableId(int variableId) {
        this.variableId = variableId;
    }

    /**
     * check if ViewModel attach to view
     */
    public boolean isAttach() {
        return view != null;
    }

    /**
     * LifecycleAction
     */
    public void onPause() {
    }

    public void onResume() {
    }

    public void onStop() {
    }

    public void onDestroy() {
        view = null;
        onViewAttachListener = null;
        lifecycleHelper.unRegister();
    }

    @NonNull
    public VMLifecycleHelper getLifecycleHelper() {
        return lifecycleHelper;
    }

    /**
     * Init data on ViewAttached
     */
    public abstract void onViewAttached(View view);

    /**
     * Interface definition for a callback to be invoked when a viewModel attach to view.
     */
    public interface OnViewAttachListener<T extends BaseViewModel> {
        void onViewAttached(T viewModel);
    }


    /**
     * 获取字符串数据
     *
     * @param resId 对应id
     * @return
     */
    public String getStrings(@StringRes int resId) {
        return ResHelper.getString(resId);
    }

    /**
     * 获取字符串数据
     *
     * @param resId 对应id
     * @return
     */
    public String getStringFormatArgs(@StringRes int resId, String data) {
        return ResHelper.getString(resId, data);
    }

    /**
     * 获取颜色资源
     *
     * @param resId 对应id
     * @return
     */
    public int getColors(@ColorRes int resId) {
        return getResources().getColor(resId);
    }


    /**
     * 获取Drawable
     *
     * @param resId 对应id
     * @return
     */
    public Drawable getDrawables(@DrawableRes int resId) {
        return getResources().getDrawable(resId);
    }


    /**
     * 获取Dimension
     *
     * @param resId 对应id
     * @return
     */
    public int getDimensionPixelOffsets(@DimenRes int resId) {
        return getResources().getDimensionPixelOffset(resId);
    }


    /**
     * 获取Dimension
     *
     * @param resId 对应id
     * @return
     */
    public int getDimensionPixelSizes(@DimenRes int resId) {
        return getResources().getDimensionPixelSize(resId);
    }
}
