package io.ganguo.viewmodel.common.base;

import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.ganguo.library.ui.adapter.v7.LoadMoreListener;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.utils.util.Views;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.viewmodel.common.RecyclerViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.VMLifecycleHelper;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.adapter.ViewModelAdapter;

/**
 * <p>
 * HFRecyclerViewModel && HFSRecyclerViewModel 基类
 * </p>
 * Created by leo on 2018/8/21.
 */
public abstract class BaseHFRViewModel<B extends ViewDataBinding, T extends ViewInterface<B>> extends BaseViewModel<T>
        implements LoadMoreListener, ViewModelAdapter.OnNotifyDiffUtilDataChangedListener {
    private RecyclerViewModel<BaseViewModel, ViewDataBinding> recyclerViewModel;
    public ObservableBoolean isAnimateLayoutChanges = new ObservableBoolean(true);
    public ObservableBoolean isEnableHeaderElevation = new ObservableBoolean(true);
    public ObservableBoolean isEnableFooterElevation = new ObservableBoolean(true);
    protected ICenterLoadingView centerLoadingView;


    @Override
    public void onAttach() {
        super.onAttach();
        initHeader(getHeaderContainer());
        initFooter(getFooterContainer());
        initEmpty(getEmptyContainer());
        initRecyclerView();
        initLoadingView();
    }


    /**
     * function:配置加载Loading
     * 注意：如果设置了skeletonScreen，则优先使用skeletonScreen！
     */
    public void initLoadingView() {
        initCenterLoading();
        showCenterLoading();
    }


    /**
     * init center Loading
     */
    protected void initCenterLoading() {
        centerLoadingView = createLoadingView();
        if (centerLoadingView == null) {
            return;
        }
        ViewModelHelper.bind(getLoadingContainer(), this, centerLoadingView.getViewModel());
    }


    /**
     * init RecyclerView
     */
    protected void initRecyclerView() {
        ViewModelHelper.bind(getRecyclerRootView(), this, recyclerViewModel = initRecyclerViewModel());
        getAdapter().setLoadMoreListener(this);
        //set recyclerView default match parent
        initRecyclerViewHeight();
        initRecyclerViewAnimator();
        getAdapter().setDataChangedListener(this);
    }


    /**
     * init RecyclerView ItemAnimator
     */
    protected void initRecyclerViewAnimator() {
        RecyclerView.ItemAnimator itemAnimator = getRecyclerView().getItemAnimator();
        int duration = 300;
        itemAnimator.setAddDuration(duration);
        itemAnimator.setChangeDuration(duration);
        itemAnimator.setMoveDuration(duration);
        itemAnimator.setRemoveDuration(duration);
    }

    /**
     * 创建RecyclerViewViewModel
     *
     * @return
     */
    protected RecyclerViewModel<BaseViewModel, ViewDataBinding> initRecyclerViewModel() {
        return RecyclerViewModel
                .linerLayout(getContext(), LinearLayoutManager.VERTICAL)
                .setViewHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }


    /**
     * 设置RecyclerView高度是否MatchParent
     *
     * @return
     */
    protected void initRecyclerViewHeight() {
        ViewGroup.LayoutParams params = getRecyclerView().getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getRecyclerView().setLayoutParams(params);
    }

    /**
     * 隐藏Loading并判断是否显示EmptyView
     */
    public void toggleEmptyView() {
        if (!isAttach()) {
            Logger.d("call: " + VMLifecycleHelper.EVENT_VIEW_MODEL_DETACH);
            return;
        }
        hideCenterLoading();
        if (getAdapter().size() == 0) {
            Views.visible(getEmptyContainer());
        } else {
            Views.gone(getEmptyContainer());
        }
    }

    /**
     * 显示中心Loading
     */
    public void showCenterLoading() {
        Views.visible(getLoadingContainer());
        if (centerLoadingView != null) {
            centerLoadingView.onStartLoading();
        }
    }

    /**
     * 隐藏中心Loading
     */
    public void hideCenterLoading() {
        Views.gone(getLoadingContainer());
        if (centerLoadingView != null) {
            centerLoadingView.onStopLoading();
        }
    }


    public void setAnimateLayoutChanges(boolean isAnimate) {
        isAnimateLayoutChanges.set(isAnimate);
    }

    public void setEnableFooterElevation(boolean isEnable) {
        isEnableFooterElevation.set(isEnable);
    }

    public void setEnableHeaderElevation(boolean isEnable) {
        isEnableHeaderElevation.set(isEnable);
    }


    /**
     * Getters
     */
    public RecyclerViewModel<BaseViewModel, ViewDataBinding> getRecyclerViewModel() {
        return recyclerViewModel;
    }

    public RecyclerView getRecyclerView() {
        if (recyclerViewModel == null) {
            return null;
        }
        return recyclerViewModel.getRecyclerView();
    }

    public ViewModelAdapter<ViewDataBinding> getAdapter() {
        if (recyclerViewModel == null) {
            return null;
        }
        return recyclerViewModel.getAdapter();
    }

    public abstract ViewGroup getHeaderContainer();

    public abstract ViewGroup getFooterContainer();

    public abstract ViewGroup getEmptyContainer();

    public abstract ViewGroup getLoadingContainer();

    public abstract ViewDataBinding getRecyclerRootView();

    protected abstract ICenterLoadingView createLoadingView();


    /**
     * Bind ViewModel to container here
     *
     * @see {@link ViewModelHelper#bind(ViewGroup, BaseViewModel, int)}
     */
    public void initHeader(ViewGroup container) {
    }

    public void initFooter(ViewGroup container) {
    }

    public void initEmpty(ViewGroup container) {

    }

    @Override
    public void onLoadMore() {
        //进入LoadMore函数后，强制停止RecyclerView滑动，避免滑动过快，出现多次LoadMore的情况！
        Views.onCancelRecyclerViewScroll(getRecyclerView());
    }

    @Override
    public void onAdapterNotifyComplete() {

    }
}
