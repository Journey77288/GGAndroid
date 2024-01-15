package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.view.activity.base.GGHFRVModelActivity;
import io.ganguo.demo.viewmodel.activity.DialogDemoVModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.ganguo.vmodel.ViewModelActivity;

/**
 * ViewModelPopupWindow 弹窗Demo
 * Created by leo on 2018/8/2.
 */

public class DialogDemoActivity extends GGHFRVModelActivity<IncludeHfRecyclerBinding, DialogDemoVModel> {
    public static Intent intentFor(Context context) {
        return new Intent(context, DialogDemoActivity.class);
    }

    @Override
    public DialogDemoVModel createViewModel() {
        return new DialogDemoVModel();
    }

    @Override
    public void onViewAttached(DialogDemoVModel viewModel) {
        super.onViewAttached(viewModel);
    }
}
