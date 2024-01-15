package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.databinding.ActivityRxQiniuDemoBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.RxQiNiuVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * RxQiniu Demo
 * Created by Roger on 7/28/16.
 */
public class RxQiniuDemoActivity extends GGVModelActivity<ActivityRxQiniuDemoBinding, RxQiNiuVModel> {

    public static Intent intentFor(Context context) {
        Intent i = new Intent(context, RxQiniuDemoActivity.class);
        return i;
    }

    @Override
    public RxQiNiuVModel createViewModel() {
        return new RxQiNiuVModel();
    }

    @Override
    public void onViewAttached(RxQiNiuVModel viewModel) {
        ViewModelHelper.bind(getBinding().includeHeader, viewModel, onCreateHeaderVModel());
    }
}
