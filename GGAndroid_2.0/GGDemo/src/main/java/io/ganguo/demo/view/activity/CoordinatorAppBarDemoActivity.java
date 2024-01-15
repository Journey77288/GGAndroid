package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.CoordinatorAppBarDemoVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.viewmodel.databinding.IncludeCoordinatorLayoutAppBarBinding;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * CoordinatorAppBarVModel Demo
 * Created by leo on 2018/7/13.
 */
public class CoordinatorAppBarDemoActivity extends GGVModelActivity<IncludeCoordinatorLayoutAppBarBinding, CoordinatorAppBarDemoVModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, CoordinatorAppBarDemoActivity.class);
    }

    @Override
    public CoordinatorAppBarDemoVModel createViewModel() {
        return new CoordinatorAppBarDemoVModel();
    }

    @Override
    public void onViewAttached(CoordinatorAppBarDemoVModel viewModel) {
        ViewModelHelper.bind(viewModel.getHeaderContainer(), viewModel, onCreateHeaderVModel());
    }
}
