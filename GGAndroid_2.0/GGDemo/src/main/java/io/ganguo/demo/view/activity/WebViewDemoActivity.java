package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.databinding.ActivityWebViewDemoBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.WebViewDemoVModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * WebViewModel - Demo
 * Created by leo on 2018/11/12.
 */
public class WebViewDemoActivity extends GGVModelActivity<ActivityWebViewDemoBinding, WebViewDemoVModel> {

    public static Intent intentFor(Context context) {
        Intent intent = new Intent(context, WebViewDemoActivity.class);
        return intent;
    }

    @Override
    public WebViewDemoVModel createViewModel() {
        return new WebViewDemoVModel();
    }

    @Override
    public void onViewAttached(WebViewDemoVModel viewModel) {
        ViewModelHelper.bind(getBinding().includeHeader, viewModel, onCreateHeaderVModel());
    }
}
