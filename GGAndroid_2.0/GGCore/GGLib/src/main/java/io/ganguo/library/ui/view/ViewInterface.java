package io.ganguo.library.ui.view;

import android.content.Context;

import android.databinding.ViewDataBinding;

/**
 * Created by Roger on 6/7/16.
 */
public interface ViewInterface<T extends ViewDataBinding> {
    Context getContext();

    T getBinding();
}
