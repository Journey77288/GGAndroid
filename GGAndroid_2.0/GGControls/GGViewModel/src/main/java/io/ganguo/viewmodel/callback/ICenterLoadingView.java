package io.ganguo.viewmodel.callback;

import io.ganguo.library.ui.view.loading.ILoadingView;
import io.ganguo.vmodel.BaseViewModel;

/**
 * <p>
 *
 * </p>
 * Created by leo on 2018/9/18.
 */
public interface ICenterLoadingView<T extends BaseViewModel> extends ILoadingView {
    T getViewModel();
}
