package io.ganguo.library.ui.view.loading;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

/**
 * Created by leo on 2018/9/18.
 */
public interface ILoadMoreViewInterface<T extends ViewDataBinding> extends ILoadingView {
    void initView(Context context, ViewGroup parentView);

    T getViewBinding();
}
