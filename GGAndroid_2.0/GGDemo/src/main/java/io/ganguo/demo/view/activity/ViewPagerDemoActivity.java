package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivityViewPagerDemoBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.ViewPagerDemoVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;

/**
 * Created by leo on 16/7/8.
 * BasePagerViewAdapter - demo
 */
public class ViewPagerDemoActivity extends GGVModelActivity<ActivityViewPagerDemoBinding, ViewPagerDemoVModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, ViewPagerDemoActivity.class);
    }

    @Override
    public ViewPagerDemoVModel createViewModel() {
        return new ViewPagerDemoVModel();
    }

    @Override
    public void onViewAttached(ViewPagerDemoVModel viewModel) {
        ViewModelHelper.bind(getBinding().includeHeader, viewModel, onCreateHeaderVModel());
    }

}
