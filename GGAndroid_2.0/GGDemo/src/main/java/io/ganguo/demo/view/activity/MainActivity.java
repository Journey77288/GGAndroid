package io.ganguo.demo.view.activity;

import io.ganguo.demo.view.activity.base.GGHFRVModelActivity;
import io.ganguo.demo.view.activity.base.GGHFSRVModelActivity;
import io.ganguo.demo.viewmodel.activity.MainVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;

/**
 * Main Activity
 */
public class MainActivity extends GGHFRVModelActivity<IncludeHfRecyclerBinding, MainVModel> {

    @Override
    public MainVModel createViewModel() {
        return new MainVModel();
    }

    @Override
    public void onViewAttached(MainVModel viewModel) {
        super.onViewAttached(viewModel);
    }

}
