package io.ganguo.incubator.view.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import io.ganguo.incubator.R;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.util.AppBars;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelActivity;

/**
 * <p>
 * AppViewModelActivity 基类
 * 说明：app内Activity，可能出现通用属性配置的，都直接继承该类，方便统一添加配置。例如：状态栏设置，语言配置等
 * </p>
 * Created by leo on 2018/9/15.
 */
public abstract class APPViewModelActivity<T extends ViewDataBinding, B extends BaseViewModel> extends ViewModelActivity<T, B> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppBarConfig();
    }


    /**
     * function: 配置StatusBar / NavigationBar 属性
     */
    protected void initAppBarConfig() {
        //设置状态栏主题为亮色（状态栏字体为黑色）
        AppBars.StatusBarLightStyle(this, ResHelper.getColor(R.color.gray_translucent));
    }
}
