package io.ganguo.incubator.viewmodel.activity;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.incubator.BuildConfig;
import io.ganguo.incubator.R;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.TextViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * Apk相关数据信息
 * Created by leo on 2018/11/1.
 */
public class ApkInfoViewModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {
    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        ViewModelHelper.bind(container, this, createHeaderVModel());
    }

    @Override
    public void onViewAttached(View view) {
        addApkViewModel();
    }

    /**
     * function：add apk info
     */
    protected void addApkViewModel() {
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_package_info)));
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_packaging_environment, BuildConfig.ENVIRONMENT)));
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_packaging_base_url, BuildConfig.BASE_URL)));
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_packaging_time, BuildConfig.PACKAGING_TIME)));
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_version_sdk, Build.VERSION.SDK_INT)));
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_sys_version, Build.VERSION.RELEASE)));
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_device_model, Build.MODEL)));
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_version_name, BuildConfig.VERSION_NAME)));
        getAdapter().add(createTextVModel(ResHelper.getString(R.string.str_version_code, BuildConfig.VERSION_CODE)));
        getAdapter().notifyDataSetChanged();
        toggleEmptyView();
    }


    /**
     * function：create header ViewModel
     *
     * @return
     */
    protected BaseViewModel createHeaderVModel() {
        return new HeaderViewModel.Builder()
                .appendItemLeft(new HeaderItemViewModel
                        .BackItemViewModel(getView().getActivity())
                        .paddingRes(R.dimen.dp_16)
                        .drawableRes(android.R.drawable.ic_menu_close_clear_cancel))
                .appendItemCenter(new HeaderItemViewModel
                        .TitleItemViewModel(getStrings(R.string.str_packaging_apk_info)))
                .appendItemLeft(new HeaderItemViewModel
                        .BackItemViewModel(getView().getActivity()))
                .build();
    }


    /**
     * function: create TextViewModel
     *
     * @param text
     * @return
     */
    private BaseViewModel createTextVModel(String text) {
        return new TextViewModel
                .Builder()
                .content(text)
                .textSizeRes(R.dimen.font_16)
                .paddingRightRes(R.dimen.dp_20)
                .paddingLeftRes(R.dimen.dp_20)
                .textColorRes(R.color.black)
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .build();
    }
}
