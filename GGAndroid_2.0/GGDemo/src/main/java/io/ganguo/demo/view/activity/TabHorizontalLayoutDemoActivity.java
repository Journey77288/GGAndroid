package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivityTabLayoutDemoBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.TabHorizontalLayoutDemoVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;

/**
 * Created by leo on 16/7/12.
 * TabLayoutDemo - activity
 */
public class TabHorizontalLayoutDemoActivity extends GGVModelActivity<ActivityTabLayoutDemoBinding, TabHorizontalLayoutDemoVModel> {
    public static Intent intentFor(Context context) {
        Intent intent = new Intent(context, TabHorizontalLayoutDemoActivity.class);
        return intent;
    }

    @Override
    public TabHorizontalLayoutDemoVModel createViewModel() {
        return new TabHorizontalLayoutDemoVModel();
    }

    @Override
    public void onViewAttached(TabHorizontalLayoutDemoVModel viewModel) {
        ViewModelHelper.bind(getBinding().includeHeader, viewModel, onCreateHeaderVModel());
    }
}
