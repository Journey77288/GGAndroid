package io.ganguo.incubator.viewmodel;

import android.content.Intent;
import android.view.View;

import io.ganguo.incubator.R;
import io.ganguo.incubator.databinding.ActivityHomeBinding;
import io.ganguo.incubator.view.activity.ApkInfoActivity;
import io.ganguo.incubator.viewmodel.base.APPActivityViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * 首页
 */
public class HomeViewModel extends APPActivityViewModel<ActivityHomeBinding> {
    @Override
    public void onViewAttached(View view) {
        initHeader();
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_home;
    }


    /**
     * function: init Header
     *
     * @return
     */
    protected void initHeader() {
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel
                        .TitleItemViewModel(getClass().getSimpleName()))
                .build();
        ViewModelHelper.bind(getView().getBinding().includeHeader, this, headerViewModel);
    }


    /**
     * function: 查看设备及apk信息
     *
     * @return
     */
    public View.OnClickListener onReviewApkInfoClick() {
        return v -> {
            Intent intent = ApkInfoActivity.createIntent(getContext());
            getView().getActivity().startActivity(intent);
        };
    }
}
