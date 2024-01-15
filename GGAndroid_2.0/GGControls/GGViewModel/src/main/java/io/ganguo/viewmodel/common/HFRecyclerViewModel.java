package io.ganguo.viewmodel.common;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import io.ganguo.library.ui.adapter.v7.LoadMoreListener;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.common.base.BaseHFRViewModel;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;

/**
 * Header Footer RecyclerView 组合ViewModel (不带下拉刷新)
 * Created by Roger on 6/22/16.
 */
public abstract class HFRecyclerViewModel<T extends ViewInterface<IncludeHfRecyclerBinding>> extends BaseHFRViewModel<IncludeHfRecyclerBinding, T>
        implements LoadMoreListener {


    @Override
    public ViewGroup getHeaderContainer() {
        return getView().getBinding().llyHeader;
    }

    @Override
    public ViewGroup getFooterContainer() {
        return getView().getBinding().llyFooter;
    }

    @Override
    public ViewGroup getEmptyContainer() {
        return getView().getBinding().llyEmpty;
    }

    @Override
    public ViewGroup getLoadingContainer() {
        return getView().getBinding().flyLoading;
    }

    @Override
    public ICenterLoadingView createLoadingView() {
        return new CommonLoadingVModel();
    }

    @Override
    public ViewDataBinding getRecyclerRootView() {
        return getView().getBinding().includeRecycler;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.include_hf_recycler;
    }

}
