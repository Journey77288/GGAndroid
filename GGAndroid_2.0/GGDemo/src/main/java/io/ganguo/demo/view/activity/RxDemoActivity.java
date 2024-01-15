package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.databinding.ActivityRxDemoBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.RxDemoVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * Created by Roger on 7/26/16.
 */
public class RxDemoActivity extends GGVModelActivity<ActivityRxDemoBinding, RxDemoVModel> {

    public static Intent intentFor(Context context) {
        Intent i = new Intent(context, RxDemoActivity.class);
        return i;
    }

    @Override
    public RxDemoVModel createViewModel() {
        return new RxDemoVModel();
    }

    @Override
    public void onViewAttached(RxDemoVModel viewModel) {
        ViewModelHelper.bind(getBinding().includeHeader, viewModel, onCreateHeaderVModel());
    }
}
