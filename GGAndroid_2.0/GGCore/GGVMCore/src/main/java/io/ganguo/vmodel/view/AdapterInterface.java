package io.ganguo.vmodel.view;

import android.databinding.ViewDataBinding;

import io.ganguo.library.ui.adapter.v7.hodler.BaseViewHolder;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.vmodel.adapter.ViewModelAdapter;

/**
 * Created by Roger on 6/7/16.
 */
public interface AdapterInterface<T extends ViewDataBinding> extends ViewInterface<T> {
    ViewModelAdapter<T> getAdapter();

    BaseViewHolder<T> getViewHolder();
}
