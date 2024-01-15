package io.ganguo.incubator.view.base;

import android.content.Context;
import android.databinding.ViewDataBinding;

import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelDialog;

/**
 * <p>
 * APPViewModelDialog 基类
 * 说明：app内Dialog，可能出现通用属性配置的，都直接继承该类，方便统一添加配置
 * </p>
 * Created by leo on 2018/9/15.
 */
public abstract class APPViewModelDialog<T extends ViewDataBinding, B extends BaseViewModel> extends ViewModelDialog<T, B> {
    public APPViewModelDialog(Context context) {
        super(context);
    }
}
