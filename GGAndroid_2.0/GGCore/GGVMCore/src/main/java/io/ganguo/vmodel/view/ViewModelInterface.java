package io.ganguo.vmodel.view;


import io.ganguo.vmodel.BaseViewModel;

/**
 * <p>
 * ViewModelInterface
 * </p>
 * Created by leo on 2018/7/20.
 */
public interface ViewModelInterface<B extends BaseViewModel> {

    B getViewModel();

    /**
     * Create ViewModel here
     * if you want to getViewModel instance use {@link #getViewModel()} instead this
     */
    B createViewModel();

    boolean checkViewModel();
}
