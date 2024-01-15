package io.ganguo.incubator.view.activity;

import android.view.View;

import io.ganguo.incubator.databinding.ActivityHomeBinding;
import io.ganguo.incubator.view.base.APPViewModelActivity;
import io.ganguo.incubator.viewmodel.HomeViewModel;


/**
 * 首页
 * Created by leo on 2018/11/1.
 */
public class HomeActivity extends APPViewModelActivity<ActivityHomeBinding, HomeViewModel> {
    @Override
    public HomeViewModel createViewModel() {
        return new HomeViewModel();
    }

    @Override
    public void onViewAttached(HomeViewModel viewModel) {

    }

}
