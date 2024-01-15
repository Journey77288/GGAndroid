package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.CustomLoadMoreStyleVModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;

/**
 * 自定义ViewModel Adapter LoadMore Loading
 * Created by leo on 2018/9/14.
 */
public class CustomLoadMoreStyleActivity extends GGVModelActivity<IncludeHfSwipeRecyclerBinding, CustomLoadMoreStyleVModel> {
    public static Intent intentFor(Context context) {
        return new Intent(context, CustomLoadMoreStyleActivity.class);
    }

    @Override
    public CustomLoadMoreStyleVModel createViewModel() {
        return new CustomLoadMoreStyleVModel();
    }

    @Override
    public void onViewAttached(CustomLoadMoreStyleVModel viewModel) {

    }
}
