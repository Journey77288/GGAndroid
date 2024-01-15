package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.databinding.ActivityRxbusDemoBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.RxBusDemoVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * RxBusDemo
 * Created by leo on 2018/8/15.
 */
public class RxBusDemoActivity extends GGVModelActivity<ActivityRxbusDemoBinding, RxBusDemoVModel> {
    public static Intent intentFor(Context context) {
        return new Intent(context, RxBusDemoActivity.class);
    }

    @Override
    public RxBusDemoVModel createViewModel() {
        return new RxBusDemoVModel();
    }

    @Override
    public void onViewAttached(RxBusDemoVModel viewModel) {
        ViewModelHelper.bind(getBinding().includeHeader, viewModel, onCreateHeaderVModel());
    }
}
