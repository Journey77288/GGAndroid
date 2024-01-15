package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.R;
import io.ganguo.demo.view.activity.base.GGHFSRVModelActivity;
import io.ganguo.demo.viewmodel.activity.DiffUtilDemoViewModel;
import io.ganguo.viewmodel.common.item.ItemSampleVModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * DiffUtil 刷新数据工具类 Demo
 * Created by leo on 2018/8/27.
 */
public class DiffUtilDemoActivity extends GGHFSRVModelActivity<IncludeHfSwipeRecyclerBinding, DiffUtilDemoViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, DiffUtilDemoActivity.class);
    }

    @Override
    public DiffUtilDemoViewModel createViewModel() {
        return new DiffUtilDemoViewModel();
    }

    @Override
    public void onViewAttached(DiffUtilDemoViewModel viewModel) {
    }
}
