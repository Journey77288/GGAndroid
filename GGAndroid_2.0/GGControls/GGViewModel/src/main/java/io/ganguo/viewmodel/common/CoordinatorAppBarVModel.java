package io.ganguo.viewmodel.common;

import android.databinding.ObservableBoolean;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.common.base.BaseCoordinatorViewModel;
import io.ganguo.viewmodel.databinding.IncludeCoordinatorLayoutAppBarBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * function：CoordinatorLayout 配合 AppBarLayout 实现联动效果
 * Created by leo on 2018/7/13.
 */
public abstract class CoordinatorAppBarVModel<T extends ViewInterface<IncludeCoordinatorLayoutAppBarBinding>> extends BaseCoordinatorViewModel<T> {
    private ObservableBoolean isEnableHeaderElevation = new ObservableBoolean(false);
    private ObservableBoolean isEnableFooterElevation = new ObservableBoolean(false);
    private ObservableBoolean isEnableAppBarElevation = new ObservableBoolean(false);


    /**
     * function：AppBarLayout
     *
     * @return
     */
    @Override
    public AppBarLayout getAppBarLayout() {
        return !isAttach() ? null : getView().getBinding().appbar;
    }

    /**
     * function：AppBarLayout Root ViewGroup
     *
     * @return
     */
    @Override
    public LinearLayout getAppBarContainer() {
        return !isAttach() ? null : getView().getBinding().llyAppbarContent;
    }

    /**
     * function：Content ViewGroup
     *
     * @return
     */
    @Override
    public FrameLayout getContentContainer() {
        return !isAttach() ? null : getView().getBinding().flyContent;
    }


    /**
     * function：SwipeRefreshLayout
     *
     * @return
     */
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    /**
     * function：Loading View
     *
     * @return
     */
    @Override
    public ViewGroup getLoadingView() {
        return !isAttach() ? null : getView().getBinding().flLoading;
    }


    /**
     * function：Header ViewGroup
     *
     * @return
     */
    @Override
    public LinearLayout getHeaderContainer() {
        return !isAttach() ? null : getView().getBinding().llyHeader;
    }


    /**
     * function：Header ViewGroup
     *
     * @return
     */
    @Override
    public LinearLayout getFooterContainer() {
        return !isAttach() ? null : getView().getBinding().llyFooter;
    }


    /**
     * function：AppBar Header ViewGroup
     *
     * @return
     */
    @Override
    public LinearLayout getAppBarHeaderContainer() {
        return !isAttach() ? null : getView().getBinding().llyAppbarHeader;
    }


    @Override
    public int getItemLayoutId() {
        return R.layout.include_coordinator_layout_app_bar;
    }

    /**
     * function：是否开启Header阴影
     *
     * @param isEnableHeaderElevation
     * @return
     */
    public CoordinatorAppBarVModel<T> setEnableHeaderElevation(boolean isEnableHeaderElevation) {
        this.isEnableHeaderElevation.set(isEnableHeaderElevation);
        return this;
    }

    /**
     * function：是否开启Footer阴影
     *
     * @param isEnableFooterElevation
     * @return
     */
    public CoordinatorAppBarVModel<T> setEnableFooterElevation(boolean isEnableFooterElevation) {
        this.isEnableFooterElevation.set(isEnableFooterElevation);
        return this;
    }

    /**
     * function：是否开启AppBar Layout阴影
     *
     * @param isEnableAppBarElevation
     * @return
     */
    public CoordinatorAppBarVModel<T> setEnableAppBarElevation(boolean isEnableAppBarElevation) {
        this.isEnableAppBarElevation.set(isEnableAppBarElevation);
        return this;
    }

    public ObservableBoolean getIsEnableFooterElevation() {
        return isEnableFooterElevation;
    }

    public ObservableBoolean getIsEnableHeaderElevation() {
        return isEnableHeaderElevation;
    }

    public ObservableBoolean getIsEnableAppBarElevation() {
        return isEnableAppBarElevation;
    }

    @Override
    public BaseViewModel getLoadingViewModel() {
        return new CommonLoadingVModel();
    }
}
