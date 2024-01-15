package io.ganguo.incubator.view.base;

import android.content.Context;
import android.databinding.ViewDataBinding;

import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelPopupWindow;

/**
 * <p>
 * APPViewModelPopupWindow 基类
 * 说明：app内PopupWindow，可能出现通用属性配置的，都直接继承该类，方便统一添加配置
 * </p>
 * Created by leo on 2018/9/15.
 */
public abstract class APPViewModelPopupWindow<T extends ViewDataBinding, B extends BaseViewModel> extends ViewModelPopupWindow<T, B> {
    public APPViewModelPopupWindow(Context context) {
        super(context);
    }
}
