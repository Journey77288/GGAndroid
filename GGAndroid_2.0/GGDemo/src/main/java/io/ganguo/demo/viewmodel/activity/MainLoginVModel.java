package io.ganguo.demo.viewmodel.activity;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivityLoginBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;

/**
 * Created by hulkyao on 27/6/2016.
 */

public class MainLoginVModel extends BaseViewModel<ActivityInterface<ActivityLoginBinding>> {

    @Override
    public void onViewAttached(View view) {
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_login;
    }
}
