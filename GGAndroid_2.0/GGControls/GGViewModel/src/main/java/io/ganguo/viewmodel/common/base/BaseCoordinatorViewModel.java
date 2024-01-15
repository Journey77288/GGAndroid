package io.ganguo.viewmodel.common.base;

import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.viewmodel.R;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * <p>
 * CoordinatorLayout ViewModel 基类
 * </p>
 * Created by leo on 2018/9/14.
 */
public abstract class BaseCoordinatorViewModel<T extends ViewInterface> extends BaseViewModel<T>
        implements AppBarLayout.OnOffsetChangedListener {

    @Override
    public void onAttach() {
        super.onAttach();
        initView();
        initLoading(getLoadingView());
        showLoading();
        initContent(getContentContainer());
        initHeader(getHeaderContainer());
        initFooter(getFooterContainer());
        initAppBarConfig(getAppBarLayout());
        initAppBarHeader(getAppBarHeaderContainer());
        initAppBarContent(getAppBarContainer());
    }

    /**
     * function：initView
     *
     * @return
     */
    protected void initView() {
        getRootView().setBackground(getDrawables(getBackgroundRes()));
        getAppBarLayout().addOnOffsetChangedListener(this);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        setSwipeLayoutEnabled(verticalOffset, getSwipeRefreshLayout());
    }


    /**
     * function：处理SwipeLayout 与 CoordinatorLayout冲突
     *
     * @param swipeRefreshLayout
     * @param verticalOffset
     */
    public void setSwipeLayoutEnabled(int verticalOffset, SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout == null) {
            return;
        }
        if (swipeRefreshLayout.isRefreshing() && verticalOffset != 0) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (verticalOffset == 0 && !swipeRefreshLayout.isEnabled()) {
            swipeRefreshLayout.setEnabled(true);
            return;
        }
        if (verticalOffset != 0 && swipeRefreshLayout.isEnabled()) {
            swipeRefreshLayout.setEnabled(false);
        }
    }


    /**
     * function：SwipeRefreshLayout
     *
     * @return
     */
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }


    /**
     * function：AppBarLayout
     *
     * @return
     */
    public abstract AppBarLayout getAppBarLayout();

    /**
     * function：AppBarLayout Root ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getAppBarContainer();

    /**
     * function：Content ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getContentContainer();


    /**
     * function：Loading View
     *
     * @return
     */
    public abstract ViewGroup getLoadingView();


    /**
     * function：Header ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getHeaderContainer();


    /**
     * function：Header ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getFooterContainer();

    /**
     * function：AppBar Header ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getAppBarHeaderContainer();

    /**
     * Bind ViewModel to container here
     *
     * @see {@link ViewModelHelper#bind(ViewGroup, BaseViewModel, BaseViewModel, int)}
     */
    public void initAppBarConfig(ViewGroup container) {
    }

    public void initContent(ViewGroup container) {
    }

    public void initAppBarContent(ViewGroup container) {

    }

    public void initHeader(ViewGroup container) {

    }

    public void initAppBarHeader(ViewGroup container) {

    }

    public void initFooter(ViewGroup container) {

    }

    public void initLoading(View container) {
        ViewModelHelper.bind(container, this, getLoadingViewModel());
    }

    public abstract BaseViewModel getLoadingViewModel();


    /**
     * function：隐藏Loading
     */
    public void showLoading() {
        if (!isAttach()) {
            return;
        }
        getLoadingView().setVisibility(View.VISIBLE);
    }

    /**
     * function：显示Loading
     */
    public void hideLoading() {
        if (!isAttach()) {
            return;
        }
        getLoadingView().setVisibility(View.GONE);
    }


    /**
     * function：Loading 背景
     *
     * @return
     */
    @DrawableRes
    protected int getLoadingBackgroundRes() {
        return R.color.white;
    }


    /**
     * function：background
     *
     * @return
     */
    @DrawableRes
    protected int getBackgroundRes() {
        return R.color.white;
    }
}
