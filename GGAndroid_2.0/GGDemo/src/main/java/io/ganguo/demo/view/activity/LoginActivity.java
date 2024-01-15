package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.databinding.ActivityLoginBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.MainLoginVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * Login Demo
 * Created by hulkyao on 26/6/2016.
 */
public class LoginActivity extends GGVModelActivity<ActivityLoginBinding, MainLoginVModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public MainLoginVModel createViewModel() {
        return new MainLoginVModel();
    }

    @Override
    public void onViewAttached(MainLoginVModel viewModel) {
        ViewModelHelper.bind(getBinding().includeHeader, viewModel, onCreateHeaderVModel());
    }
}
