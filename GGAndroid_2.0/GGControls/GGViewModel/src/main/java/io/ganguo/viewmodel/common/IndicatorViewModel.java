package io.ganguo.viewmodel.common;

import android.view.View;

import com.ganguo.banner.view.IndicatorView;

import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.IncludeIndicatorBinding;
import io.ganguo.vmodel.BaseViewModel;

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
    public int getItemLayoutId() {
        return R.layout.include_indicator;
    }

    /**
     * function: get IndicatorView
     *
     * @return
     */
    public IndicatorView getIndicatorView() {
        return getView().getBinding().indicator;
    }

}
