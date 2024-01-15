package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.viewmodel.activity.CollapsingToolbarDemoVModel;
import io.ganguo.viewmodel.databinding.IncludeCoordinatorCollapsingToolbarBinding;
import io.ganguo.vmodel.ViewModelActivity;

/**
 * CollapsingToolbar 折叠联动效果 Demo
 * Created by leo on 2018/9/14.
 */
public class CollapsingToolbarDemoActivity extends ViewModelActivity<IncludeCoordinatorCollapsingToolbarBinding, CollapsingToolbarDemoVModel> {
    public static Intent intentFor(Context context) {
        return new Intent(context, CollapsingToolbarDemoActivity.class);
    }

    @Override
    public CollapsingToolbarDemoVModel createViewModel() {
        return new CollapsingToolbarDemoVModel();
    }

    @Override
    public void onViewAttached(CollapsingToolbarDemoVModel viewModel) {

    }
}
