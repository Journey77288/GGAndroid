package io.ganguo.vmodel.bindadapter;

import android.databinding.BindingAdapter;
import android.os.Build;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import java.util.Collection;

import io.ganguo.utils.util.Collections;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * <p>
 * ViewModel相关DataBinding函数
 * </p>
 * Created by leo on 2018/8/3.
 */
public class BindingViewModelHelper {
    /**
     * function:Bind all viewModel to viewGroup
     *
     * @param viewGroup
     * @param viewModelList
     */
    @BindingAdapter("android:bind_appendViewList")
    public static void onBindAppendViewList(ViewGroup viewGroup, Collection<? extends BaseViewModel> viewModelList) {
        if (Collections.isEmpty(viewModelList)) {
            return;
        }
        for (BaseViewModel viewModel : viewModelList) {
            if (viewModel != null && !viewModel.isAttach()) {
                ViewModelHelper.bind(viewGroup, viewModel);
            }
        }
    }

    /**
     * function:绑定viewModel到ViewGroup
     *
     * @param viewModel
     * @param viewGroup
     */
    @BindingAdapter("android:bind_appendView")
    public static void onBindAppendView(ViewGroup viewGroup, BaseViewModel viewModel) {
        if (viewModel != null && !viewModel.isAttach()) {
            ViewModelHelper.bind(viewGroup, viewModel);
        }
    }

    /**
     * function：ViewGroup DataBinding 开启ViewGroup阴影
     *
     * @param isEnable
     * @param viewGroup
     */
    @BindingAdapter("android:bind_enable_elevation")
    public static void onBindEnableElevation(ViewGroup viewGroup, boolean isEnable) {
        if (viewGroup != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewGroup.setOutlineProvider(isEnable ? ViewOutlineProvider.BOUNDS : ViewOutlineProvider.BACKGROUND);
            viewGroup.setElevation(isEnable ? viewGroup.getResources().getDimensionPixelOffset(io.ganguo.library.R.dimen.dp_4) : 0);
        }
    }
}
