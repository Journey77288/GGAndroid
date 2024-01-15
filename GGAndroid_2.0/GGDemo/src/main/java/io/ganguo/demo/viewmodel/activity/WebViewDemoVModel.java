package io.ganguo.demo.viewmodel.activity;


import android.view.View;

import io.ganguo.demo.AppContext;
import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivityWebViewDemoBinding;
import io.ganguo.demo.viewmodel.loading.CenterLoadingVModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.common.webview.UrlWebViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * WebView - ViewModel  Demo
 * Created by leo on 2018/11/12.
 */
public class WebViewDemoVModel extends BaseViewModel<ViewInterface<ActivityWebViewDemoBinding>> {
    private String url = "https://ganguo.io/";

    @Override
    public void onViewAttached(View view) {
        ViewModelHelper.bind(getView().getBinding().includeWebview, this, getWebViewModel());
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_web_view_demo;
    }

    /**
     * WebViewModel
     *
     * @return
     */
    public BaseViewModel getWebViewModel() {
        return new UrlWebViewModel(AppContext.get(), url)
                .setShowCenterLoading(false)
                .setShowHorizontalBar(true)
                .setCenterLoadingView(new CenterLoadingVModel());
    }
}
