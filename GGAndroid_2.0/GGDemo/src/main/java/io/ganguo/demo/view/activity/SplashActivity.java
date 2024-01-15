package io.ganguo.demo.view.activity;


import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivitySplashBinding;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.SplashVModel;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.util.AppBars;

/**
 * <p>
 * SplashActivity 启动页
 * </p>
 * Created by leo on 2018/8/31.
 */
public class SplashActivity extends GGVModelActivity<ActivitySplashBinding, SplashVModel> {

    @Override
    public SplashVModel createViewModel() {
        return new SplashVModel();
    }

    @Override
    public void onViewAttached(SplashVModel viewModel) {
        AppBars.Color(this, ResHelper.getColor(R.color.white));
        AppBars.NavigationBarColor(this, ResHelper.getColor(R.color.white));
    }
}
