package io.ganguo.viewmodel.common;

import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.common.base.BaseHFRViewModel;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.vmodel.VMLifecycleHelper;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;

/**
 * Header Footer SwipeRefresh RecyclerView 组合ViewModel
 * Created by Roger on 6/22/16.
 */

public abstract class HFSRecyclerViewModel<T extends ViewInterface<IncludeHfSwipeRecyclerBinding>> extends BaseHFRViewModel<IncludeHfSwipeRecyclerBinding, T>
        implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    public void onAttach() {
        super.onAttach();
        initSwipeRefreshLayout();
    }

    /**
     * init SwipeRefreshLayout
     */
    protected void initSwipeRefreshLayout() {
        // 在 app colors.xml里复写下面三个colorId即可改变默认的RefreshLoading颜色
        getSwipeRefreshLayout().setColorSchemeResources(R.color.refresh_one, R.color.refresh_two, R.color.refresh_three);
        getSwipeRefreshLayout().setOnRefreshListener(this);
    }

    @Override
    public void toggleEmptyView() {
        if (!isAttach()) {
            Logger.d("call: " + VMLifecycleHelper.EVENT_VIEW_MODEL_DETACH);
            return;
        }
        getSwipeRefreshLayout().setRefreshing(false);
        super.toggleEmptyView();
    }

    @Override
    public void onRefresh() {
        // ContentLoading正在显示时取消Refresh动画
        getAdapter().disableLoadMore();
        if (getLoadingContainer().getVisibility() == View.VISIBLE) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
    }


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


    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return getView().getBinding().swipeLayout;
    }


    @Override
    public int getItemLayoutId() {
        return R.layout.include_hf_swipe_recycler;
    }


}
