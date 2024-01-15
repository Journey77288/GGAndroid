package io.ganguo.demo.view.activity.base;

import android.databinding.ViewDataBinding;

import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelActivity;

/**
 * <p>
 * 基类- 用于封装一些通用的函数
 * </p>
 * Created by leo on 2018/7/30.
 */
public abstract class GGVModelActivity<T extends ViewDataBinding, B extends BaseViewModel> extends ViewModelActivity<T, B> {


    /**
     * function：create header ViewModel
     *
     * @return
     */
    protected HeaderViewModel onCreateHeaderVModel() {
        return new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel
                        .TitleItemViewModel(getClass().getSimpleName()))
                .appendItemLeft(new HeaderItemViewModel
                        .BackItemViewModel(this))
                .build();
    }
}
