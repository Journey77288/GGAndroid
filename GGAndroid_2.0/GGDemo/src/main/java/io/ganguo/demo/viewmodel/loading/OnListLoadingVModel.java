package io.ganguo.demo.viewmodel.loading;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.LoadingOneListBinding;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.vmodel.BaseViewModel;

/**
 * OneList页面Loading
 * Created by leo on 2018/11/20.
 */
public class OnListLoadingVModel extends BaseViewModel<ViewInterface<LoadingOneListBinding>> implements ICenterLoadingView<OnListLoadingVModel> {
    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getItemLayoutId() {
        return R.layout.loading_one_list;
    }

    @Override
    public OnListLoadingVModel getViewModel() {
        return this;
    }

    @Override
    public void onStartLoading() {
        if (!isAttach()) {
            return;
        }
        getView().getBinding().shimmer.startShimmerAnimation();
    }

    @Override
    public void onStopLoading() {
        if (!isAttach()) {
            return;
        }
        getView().getBinding().shimmer.stopShimmerAnimation();
    }
}
