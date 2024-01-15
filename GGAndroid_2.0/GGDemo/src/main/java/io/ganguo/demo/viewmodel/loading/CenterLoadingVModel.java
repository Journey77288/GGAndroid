package io.ganguo.demo.viewmodel.loading;

import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.LoadingCenterBinding;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.vmodel.BaseViewModel;

/**
 * <p>
 * 页面中心Loading
 * </p>
 * Created by leo on 2018/9/18.
 */
public class CenterLoadingVModel extends BaseViewModel<ViewInterface<LoadingCenterBinding>> implements ICenterLoadingView<CenterLoadingVModel> {
    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getItemLayoutId() {
        return R.layout.loading_center;
    }

    @Override
    public CenterLoadingVModel getViewModel() {
        return this;
    }

    @Override
    public void onStartLoading() {
        if (!isAttach()) {
            return;
        }
        getLoadingView().smoothToShow();
    }

    @Override
    public void onStopLoading() {
        if (!isAttach()) {
            return;
        }
        getLoadingView().smoothToHide();
    }


    private AVLoadingIndicatorView getLoadingView() {
        return getView().getBinding().avi;
    }
}
