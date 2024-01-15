package io.ganguo.demo.view.activity.base;

import io.ganguo.viewmodel.common.HFSRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * HFSRecyclerViewModel - Activity
 * Created by leo on 2018/7/30.
 */
public abstract class GGHFSRVModelActivity<T extends IncludeHfSwipeRecyclerBinding, B extends HFSRecyclerViewModel> extends GGVModelActivity<T, B> {

    @Override
    public void onViewAttached(B viewModel) {
        ViewModelHelper.bind(viewModel.getHeaderContainer(), viewModel, onCreateHeaderVModel());
    }

}
