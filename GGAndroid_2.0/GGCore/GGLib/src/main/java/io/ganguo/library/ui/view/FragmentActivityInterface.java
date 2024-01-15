package io.ganguo.library.ui.view;

import android.databinding.ViewDataBinding;

import io.ganguo.library.ui.base.FragmentNavigator;

/**
 * 带操作Fragment相关方法的ViewInterface
 * ted by Roger on 7/6/16.
 */
public interface FragmentActivityInterface<T extends ViewDataBinding> extends ActivityInterface<T> {
    FragmentNavigator getNavigator();
}
