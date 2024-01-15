package io.ganguo.viewmodel.pack.common;

import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ObservableBoolean;

import org.jetbrains.annotations.NotNull;

import io.ganguo.utils.util.AppBars;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.pack.common.base.BaseCoordinatorViewModel;
import io.ganguo.viewmodel.databinding.IncludeCoordinatorCollapsingToolbarBinding;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.ViewModelHelper;
import io.ganguo.viewmodel.core.viewinterface.ViewInterface;

/**
 * CoordinatorLayout + Toolbar + CollapsingToolbarLayout 实现折叠联动效果
 * Created by leo on 2018/9/14.
 */
public abstract class CollapsingToolbarViewModel<T extends ViewInterface<IncludeCoordinatorCollapsingToolbarBinding>> extends BaseCoordinatorViewModel<T> {
    public ObservableBoolean isEnableHeaderElevation = new ObservableBoolean(false);
    public ObservableBoolean isEnableFooterElevation = new ObservableBoolean(false);

    @Override
    public void attach(@NotNull ViewInterface<?> view, int variableId) {
        super.attach(view, variableId);
        initToolbar(getToolbarLayout(), getToolbar());
    }


    @Override
    public void initSmartRefresh(@NotNull RefreshLayout refreshLayout) {
        super.initSmartRefresh(refreshLayout);
        refreshLayout.setHeaderInsetStartPx(AppBars.getStatusBarHeight(getContext()));
    }

    @Override
    public AppBarLayout getAppBarLayout() {
        return getViewInterface().getBinding().appbar;
    }

    @Override
    public ViewGroup getAppBarContainer() {
        return getViewInterface().getBinding().llyAppbarContent;
    }

    @Override
    public ViewGroup getContentContainer() {
        return getViewInterface().getBinding().flyContent;
    }

    @Override
    public ViewGroup getLoadingContainer() {
        return getViewInterface().getBinding().flLoading;
    }

    @Override
    public ViewGroup getHeaderContainer() {
        return getViewInterface().getBinding().llyHeader;
    }

    @Override
    public ViewGroup getFooterContainer() {
        return getViewInterface().getBinding().llyFooter;
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return getViewInterface().getBinding().smartLayout;
    }

    @Override
    public ViewGroup getAppBarHeaderContainer() {
        return getViewInterface().getBinding().llyAppbarHeader;
    }


    /**
     * CollapsingToolbarLayout View
     *
     * @return
     */
    public CollapsingToolbarLayout getToolbarLayout() {
        return getViewInterface().getBinding().toolbarLayout;
    }

    /**
     * Toolbar View
     *
     * @return
     */
    public Toolbar getToolbar() {
        return getViewInterface().getBinding().toolbar;
    }

    /**
     * init Toolbar to  here
     *
     * @see {@link ViewModelHelper#bind(ViewGroup, BaseViewModel, BaseViewModel, int)}
     */
    public abstract void initToolbar(CollapsingToolbarLayout toolbarLayout, Toolbar toolbar);


    @Override
    public int getLayoutId() {
        return R.layout.include_coordinator_collapsing_toolbar;
    }
}
