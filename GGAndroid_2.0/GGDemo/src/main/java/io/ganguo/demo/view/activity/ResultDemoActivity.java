package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.databinding.ActivityResultBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.ResultVModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * View model sendMessage & handle Message demo
 * 利用Handler实现onActivityResult,用于ViewModel之间通信
 * Created by Roger on 6/7/16.
 */
public class ResultDemoActivity extends GGVModelActivity<ActivityResultBinding, ResultVModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, ResultDemoActivity.class);
    }

    @Override
    public ResultVModel createViewModel() {
        return new ResultVModel();
    }

    @Override
    public void onViewAttached(ResultVModel viewModel) {
        Logger.d("onViewAttached: " + "isAttach: " + getViewModel().isAttach());
        ViewModelHelper.bind(getBinding().includeHeader, viewModel, onCreateHeaderVModel());
    }
}
