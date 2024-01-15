package io.ganguo.viewmodel.common;


import android.support.annotation.DrawableRes;
import android.view.View;

import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.databinding.IncludeCommonLoadingBinding;

/**
 * function：通用Loading
 * Created by leo on 2018/7/13.
 */
public class CommonLoadingVModel extends BaseViewModel<ViewInterface<IncludeCommonLoadingBinding>> implements ICenterLoadingView<CommonLoadingVModel> {
    @DrawableRes
    public int bgColorRes = R.color.white;

    public CommonLoadingVModel setBgColorRes(@DrawableRes int bgColorRes) {
        this.bgColorRes = bgColorRes;
        return this;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.include_common_loading;
    }

    @Override
    public void onViewAttached(View view) {
        getRootView().setBackgroundResource(bgColorRes);
    }

    @Override
    public CommonLoadingVModel getViewModel() {
        return this;
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onStopLoading() {
    }


}
