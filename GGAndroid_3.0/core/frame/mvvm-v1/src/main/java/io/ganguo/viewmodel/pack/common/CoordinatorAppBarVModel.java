package io.ganguo.viewmodel.pack.common;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.databinding.ObservableBoolean;

import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.pack.common.base.BaseCoordinatorViewModel;
import io.ganguo.viewmodel.databinding.IncludeCoordinatorLayoutAppBarBinding;

/**
 * function：CoordinatorLayout 配合 AppBarLayout 实现联动效果
 * Created by leo on 2018/7/13.
 */
public abstract class CoordinatorAppBarVModel<T extends ViewInterface<IncludeCoordinatorLayoutAppBarBinding>> extends BaseCoordinatorViewModel<T> {
    private ObservableBoolean isEnableHeaderElevation = new ObservableBoolean(false);
    private ObservableBoolean isEnableFooterElevation = new ObservableBoolean(false);


    /**
     * function：AppBarLayout
     *
     * @return
     */
    @Override
    public AppBarLayout getAppBarLayout() {
        return !isAttach() ? null : getViewInterface().getBinding().appbar;
    }

    /**
     * function：AppBarLayout Root ViewGroup
     *
     * @return
     */
    @Override
    public LinearLayout getAppBarContainer() {
        return !isAttach() ? null : getViewInterface().getBinding().llyAppbarContent;
    }

    /**
     * function：Content ViewGroup
     *
     * @return
     */
    @Override
    public FrameLayout getContentContainer() {
        return !isAttach() ? null : getViewInterface().getBinding().flyContent;
    }


    /**
     * function：Loading View
     *
     * @return
     */
    @Override
    public ViewGroup getLoadingContainer() {
        return !isAttach() ? null : getViewInterface().getBinding().flLoading;
    }


    /**
     * function：Header ViewGroup
     *
     * @return
     */
    @Override
    public LinearLayout getHeaderContainer() {
        return !isAttach() ? null : getViewInterface().getBinding().llyHeader;
    }


    /**
     * function：Header ViewGroup
     *
     * @return
     */
    @Override
    public LinearLayout getFooterContainer() {
        return !isAttach() ? null : getViewInterface().getBinding().llyFooter;
    }


    /**
     * function：AppBar Header ViewGroup
     *
     * @return
     */
    @Override
    public LinearLayout getAppBarHeaderContainer() {
        return !isAttach() ? null : getViewInterface().getBinding().llyAppbarHeader;
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return getViewInterface().getBinding().smartLayout;
    }

    @Override
    public int getLayoutId() {
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


    public ObservableBoolean getIsEnableFooterElevation() {
        return isEnableFooterElevation;
    }

    public ObservableBoolean getIsEnableHeaderElevation() {
        return isEnableHeaderElevation;
    }


}
