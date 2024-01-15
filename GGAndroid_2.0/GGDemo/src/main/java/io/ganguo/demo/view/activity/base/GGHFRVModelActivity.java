package io.ganguo.demo.view.activity.base;

import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * HFRecyclerViewModel -- Activity
 * Created by leo on 2018/10/27.
 */
public abstract class GGHFRVModelActivity<T extends IncludeHfRecyclerBinding, B extends HFRecyclerViewModel> extends GGVModelActivity<T, B> {
    @Override
    public void onViewAttached(B viewModel) {
        ViewModelHelper.bind(viewModel.getHeaderContainer(), viewModel, onCreateHeaderVModel());
    }


}
