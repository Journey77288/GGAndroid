package io.ganguo.viewmodel.pack.common.base;

import android.animation.AnimatorInflater;
import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.ViewModelHelper;

/**
 * <p>
 * CoordinatorLayout ViewModel 基类
 * </p>
 * Created by leo on 2018/9/14.
 */
public abstract class BaseCoordinatorViewModel<T extends ViewInterface<?>> extends BaseViewModel<T>
        implements AppBarLayout.OnOffsetChangedListener, OnRefreshListener {

    @Override
    public void onAttach() {
        super.onAttach();
        initView();
        initContent(getContentContainer());
        initHeader(getHeaderContainer());
        initFooter(getFooterContainer());
        initAppBarConfig(getAppBarLayout());
        initAppBarHeader(getAppBarHeaderContainer());
        initAppBarContent(getAppBarContainer());
    }

    /**
     * 开启AppBarLayout 阴影
     */
    protected void enableAppBarElevation() {
        if (getAppBarLayout() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getAppBarLayout().setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), R.animator.appbar_elevation_dp4));
        }
    }

    /**
     * 关闭AppBarLayout 阴影
     */
    protected void disableAppBarElevation() {
        if (getAppBarLayout() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getAppBarLayout().setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), R.animator.appbar_elevation_dp0));
        }
    }


    /**
     * initView
     *
     * @return
     */
    protected void initView() {
        getRootView().setBackground(getDrawable(getBackgroundRes()));
        getAppBarLayout().addOnOffsetChangedListener(this);
        initSmartRefresh(getSmartRefreshLayout());
    }


    public void initSmartRefresh(@NotNull RefreshLayout refreshLayout) {
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float factor = Math.abs(Float.valueOf(verticalOffset) / Float.valueOf(appBarLayout.getTotalScrollRange()));
        onAppBarLayoutExpandState(factor == 0, factor);
    }

    /**
     * AppBarLayout 展开/折叠 状态
     *
     * @param isExpand 是否完全展开
     * @param factor   factor==0时表示完全展开，factor==1时表示为完全折叠
     */
    public void onAppBarLayoutExpandState(boolean isExpand, float factor) {
        if (getSmartRefreshLayout() != null) {
            getSmartRefreshLayout().setEnableRefresh(isExpand);
        }
    }


    /**
     * SwipeRefreshLayout
     *
     * @return
     */
    public SmartRefreshLayout getSmartRefreshLayout() {
        return null;
    }


    /**
     * AppBarLayout
     *
     * @return
     */
    public abstract AppBarLayout getAppBarLayout();

    /**
     * AppBarLayout Root ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getAppBarContainer();

    /**
     * Content ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getContentContainer();


    /**
     * Loading View
     *
     * @return
     */
    public abstract ViewGroup getLoadingContainer();


    /**
     * Header ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getHeaderContainer();


    /**
     * Header ViewGroup
     *
     * @return
     */
    public abstract ViewGroup getFooterContainer();

    /**
     * AppBar Header ViewGroup
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

    /**
     * SmartRefreshLayout 下拉刷新回调
     *
     * @return
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

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
