package io.ganguo.library.ui.view.loading;

import io.ganguo.library.ui.adapter.v7.hodler.LayoutId;

/**
 * Created by leo on 2018/9/18.
 */
public interface ILoadingView extends LayoutId {
    void onStartLoading();

    void onStopLoading();

    void onDestroy();
}
