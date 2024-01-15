package io.ganguo.incubator.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.incubator.viewmodel.activity.ApkInfoViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.ganguo.vmodel.ViewModelActivity;

/**
 * Apk相关数据信息
 * Created by leo on 2018/11/1.
 */
public class ApkInfoActivity extends ViewModelActivity<IncludeHfRecyclerBinding, ApkInfoViewModel> {

    /**
     * function:create intent
     *
     * @param context
     */
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ApkInfoActivity.class);
        return intent;
    }


    @Override
    public ApkInfoViewModel createViewModel() {
        return new ApkInfoViewModel();
    }

    @Override
    public void onViewAttached(ApkInfoViewModel viewModel) {

    }
}
