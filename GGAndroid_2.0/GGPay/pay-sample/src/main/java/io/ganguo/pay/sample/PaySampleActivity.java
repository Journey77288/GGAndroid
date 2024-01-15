package io.ganguo.pay.sample;

import io.ganguo.pay.sample.viewmodel.PaySampleViewModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;

/**
 * <p>
 * 支付相关Demo
 * </p>
 * Created by leo on 2018/8/7.
 */
public class PaySampleActivity extends ViewModelActivity<IncludeHfRecyclerBinding, PaySampleViewModel> {
    @Override
    public PaySampleViewModel createViewModel() {
        return new PaySampleViewModel();
    }

    @Override
    public void onViewAttached(PaySampleViewModel viewModel) {

    }
}
