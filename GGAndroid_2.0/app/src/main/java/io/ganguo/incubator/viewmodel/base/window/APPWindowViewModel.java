package io.ganguo.incubator.viewmodel.base.window;

import android.databinding.ViewDataBinding;

import io.ganguo.library.ui.view.PopupWindowInterface;
import io.ganguo.vmodel.BaseViewModel;

/**
 * <p>
 * App 内 PopupWindow ViewModel 如果需要继承BaseViewModel，现在则都改为继承该类。
 * </p>
 * Created by leo on 2018/9/15.
 */
public abstract class APPWindowViewModel<T extends ViewDataBinding> extends BaseViewModel<PopupWindowInterface<T>> {
}
