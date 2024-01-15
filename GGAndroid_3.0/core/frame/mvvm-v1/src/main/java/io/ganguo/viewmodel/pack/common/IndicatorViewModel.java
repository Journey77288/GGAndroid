package io.ganguo.viewmodel.pack.common;

import android.view.View;

import io.ganguo.banner.view.IndicatorView;

import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.IncludeIndicatorBinding;
import io.ganguo.viewmodel.core.BaseViewModel;

/**
 * <p>
 * Banner 指示器ViewModel
 * </p>
 * Created by leo on 2018/10/25.
 */
public class IndicatorViewModel extends BaseViewModel<ViewInterface<IncludeIndicatorBinding>> {
    private IndicatorView.Builder builder;

    public IndicatorViewModel(IndicatorView.Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {
        builder.build(getIndicatorView());
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_indicator;
    }

    /**
     * function: get IndicatorView
     *
     * @return
     */
    public IndicatorView getIndicatorView() {
        return getViewInterface().getBinding().indicator;
    }

}
