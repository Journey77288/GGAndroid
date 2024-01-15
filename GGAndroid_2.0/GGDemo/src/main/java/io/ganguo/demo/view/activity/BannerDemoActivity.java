package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.view.activity.base.GGHFRVModelActivity;
import io.ganguo.demo.view.activity.base.GGHFSRVModelActivity;
import io.ganguo.demo.viewmodel.activity.BannerDemoVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;

/**
 * Created by leo on 2017/4/28.
 * Banner - demo
 */
public class BannerDemoActivity extends GGHFRVModelActivity<IncludeHfRecyclerBinding, BannerDemoVModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, BannerDemoActivity.class);
    }

    @Override
    public BannerDemoVModel createViewModel() {
        return new BannerDemoVModel();
    }

    @Override
    public void onViewAttached(BannerDemoVModel viewModel) {
        super.onViewAttached(viewModel);
    }
}
