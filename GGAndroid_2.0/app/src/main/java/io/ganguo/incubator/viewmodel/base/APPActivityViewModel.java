package io.ganguo.incubator.viewmodel.base;

import android.databinding.ViewDataBinding;

import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ActivityInterface;

/**
 * <p>
 * App 内Activity ViewModel 如果需要继承BaseViewModel，现在则都改为继承该类。
 * </p>
 * Created by leo on 2018/9/15.
 */
public abstract class APPActivityViewModel<T extends ViewDataBinding> extends BaseViewModel<ActivityInterface<T>> {
}
