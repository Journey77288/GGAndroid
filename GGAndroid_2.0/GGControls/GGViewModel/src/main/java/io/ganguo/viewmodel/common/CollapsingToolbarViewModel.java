package io.ganguo.viewmodel.common;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.common.base.BaseCoordinatorViewModel;
import io.ganguo.viewmodel.databinding.IncludeCoordinatorCollapsingToolbarBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * <p>
 * CoordinatorLayout + Toolbar + CollapsingToolbarLayout 实现折叠联动效果
 * </p>
 * Created by leo on 2018/9/14.
 */
public abstract class CollapsingToolbarViewModel<T extends ViewInterface<IncludeCoordinatorCollapsingToolbarBinding>> extends BaseCoordinatorViewModel<T> {
    @Override
    public void attach(T view, int variableId) {
        super.attach(view, variableId);
        initToolbar(getToolbarLayout(), getToolbar());
    }

    @Override
    public AppBarLayout getAppBarLayout() {
        return getView().getBinding().appbar;
    }

    @Override
    public ViewGroup getAppBarContainer() {
        return getView().getBinding().llyAppbarContent;
    }

    @Override
    public ViewGroup getContentContainer() {
        return getView().getBinding().flyContent;
    }

    @Override
    public ViewGroup getLoadingView() {
        return getView().getBinding().flLoading;
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
    public ViewGroup getAppBarHeaderContainer() {
        return getView().getBinding().llyAppbarHeader;
    }

    @Override
    public BaseViewModel getLoadingViewModel() {
        return new CommonLoadingVModel();
    }


    /**
     * function：CollapsingToolbarLayout View
     *
     * @return
     */
    public CollapsingToolbarLayout getToolbarLayout() {
        return getView().getBinding().toolbarLayout;
    }

    /**
     * function：Toolbar View
     *
     * @return
     */
    public Toolbar getToolbar() {
        return getView().getBinding().toolbar;
    }

    /**
     * init Toolbar to  here
     *
     * @see {@link ViewModelHelper#bind(ViewGroup, BaseViewModel, BaseViewModel, int)}
     */
    public abstract void initToolbar(CollapsingToolbarLayout toolbarLayout, Toolbar toolbar);


    @Override
    public int getItemLayoutId() {
        return R.layout.include_coordinator_collapsing_toolbar;
    }
}
