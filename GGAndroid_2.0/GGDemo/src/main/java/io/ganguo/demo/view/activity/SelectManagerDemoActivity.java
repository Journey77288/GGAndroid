package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.view.activity.base.GGHFRVModelActivity;
import io.ganguo.demo.viewmodel.activity.SelectManagerDemoVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;

/**
 * SelectManager Demo
 * Created by Roger on 12/05/2017.
 */

public class SelectManagerDemoActivity extends GGHFRVModelActivity<IncludeHfRecyclerBinding, SelectManagerDemoVModel> {

    public static Intent intentFor(Context context) {
        Intent i = new Intent(context, SelectManagerDemoActivity.class);
        return i;
    }

    @Override
    public SelectManagerDemoVModel createViewModel() {
        return new SelectManagerDemoVModel();
    }

    @Override
    public void onViewAttached(SelectManagerDemoVModel viewModel) {
        super.onViewAttached(viewModel);
    }
}
