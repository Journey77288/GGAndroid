package io.ganguo.viewmodel.common.base;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import io.ganguo.library.Config;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.utils.callback.common.Action2;
import io.ganguo.utils.callback.common.Action4;
import io.ganguo.utils.util.Networks;
import io.ganguo.utils.util.Views;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.IncludeWebviewBinding;
import io.ganguo.viewmodel.helper.webview.VMWebChromeClient;
import io.ganguo.viewmodel.helper.webview.VMWebViewClient;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * WebViewModel- 基类
 * Created by leo on 2018/11/12.
 */
public abstract class BaseWebViewModel<T extends ViewInterface<IncludeWebviewBinding>> extends BaseViewModel<T> {
    //App WebView最大缓存
    private static final long APP_CACHE_MAX_SIZE = 1024 * 1024 * 50;
    private WebView webView;
    private Application application;
    private ICenterLoadingView centerLoadingView;
    public ObservableBoolean isShowHorizontalBar = new ObservableBoolean(false);
    public ObservableBoolean isShowCenterLoading = new ObservableBoolean(true);

    public BaseWebViewModel(Application application) {
        this.application = application;
    }


    @Override
    public void onAttach() {
        super.onAttach();
        initCenterLoadView();
        initWebView();
        initWebViewSetting();
    }

    /**
     * init WebView
     */
    protected void initWebView() {
        if (webView != null) {
            return;
        }
        webView = new WebView(application);
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条在WebView外侧显示
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_INSET);
        webView.setScrollbarFadingEnabled(true);
        webView.setWebChromeClient(onCreateWebChromeClient());
        webView.setWebViewClient(onCreateWebViewClient());
        getWebContainer().removeAllViews();
        getWebContainer().addView(webView);
        if (!(webView.getView() instanceof android.webkit.WebView)) {
            return;
        }
        android.webkit.WebView webkit = (android.webkit.WebView) webView.getView();
        webkit.setHorizontalScrollBarEnabled(false);
        webkit.setVerticalScrollBarEnabled(false);
        webkit.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条在WebView外侧显示
    }


    /**
     * init WebView Setting
     */
    protected void initWebViewSetting() {
        WebView webView = getWebView();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.supportMultipleWindows();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLoadsImagesAutomatically(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setBlockNetworkImage(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(Config.getDataPath().getAbsolutePath());
        settings.setAppCacheMaxSize(APP_CACHE_MAX_SIZE);
        settings.setAppCacheEnabled(true);
        settings.setBlockNetworkImage(true);
        if (Networks.isConnected(getContext())) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//不使用网络，只加载缓存
        }
        //允许加载http资源
        settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }


    /**
     * 配置页面中心Loading
     */
    protected void initCenterLoadView() {
        if (!isShowCenterLoading.get()) {
            return;
        }
        if (centerLoadingView == null) {
            return;
        }
        getLoadingContainer().removeAllViews();
        ViewModelHelper.bind(getLoadingContainer(), this, centerLoadingView.getViewModel());
        centerLoadingView.onStartLoading();
    }

    /**
     * set CenterLoading
     *
     * @param centerLoadingView
     */
    public <S extends BaseWebViewModel<T>> S setCenterLoadingView(ICenterLoadingView centerLoadingView) {
        this.centerLoadingView = centerLoadingView;
        return (S) this;
    }

    /**
     * set isShowHorizontalBar
     *
     * @param isShowHorizontalBar 是否显示水平进度
     * @return
     */
    public <S extends BaseWebViewModel<T>> S setShowHorizontalBar(boolean isShowHorizontalBar) {
        this.isShowHorizontalBar.set(isShowHorizontalBar);
        return (S) this;
    }

    /**
     * set isShowCenterLoading
     *
     * @param isShowCenterLoading 是否显示中心Loading
     */
    public <S extends BaseWebViewModel<T>> S setShowCenterLoading(boolean isShowCenterLoading) {
        this.isShowCenterLoading.set(isShowCenterLoading);
        return (S) this;
    }

    /**
     * WebView ViewGroup
     *
     * @return
     */
    protected ViewGroup getWebContainer() {
        return getView().getBinding().flWeb;
    }

    /**
     * 中心Loading ViewGroup
     *
     * @return
     */
    protected ViewGroup getLoadingContainer() {
        return getView().getBinding().flCenterLoading;
    }

    /**
     * 水平进度条 MaterialProgressBar
     *
     * @return
     */
    protected MaterialProgressBar getHorizontalBar() {
        return getView().getBinding().mpHorizontalBar;
    }

    /**
     * get webView
     *
     * @return
     */
    public WebView getWebView() {
        return webView;
    }

    /**
     * layout Id
     *
     * @return
     */
    @Override
    public int getItemLayoutId() {
        return R.layout.include_webview;
    }


    /**
     * create WebChromeClient
     *
     * @return
     */
    protected WebChromeClient onCreateWebChromeClient() {
        return new VMWebChromeClient()
                .setHorizontalBar(getHorizontalBar())
                .setActionLoadFinish(onActionFinished())
                .setShowHorizontalBar(isShowHorizontalBar.get());
    }

    /**
     * create WebViewClient
     *
     * @return
     */
    protected WebViewClient onCreateWebViewClient() {
        return new VMWebViewClient()
                .setActionFinished(onActionFinished())
                .setActionReceivedError(onActionReceivedError());
    }

    /**
     * WebView加载完成回调接口
     *
     * @return
     */
    protected Action2<WebView, String> onActionFinished() {
        return (webView, s) -> {
            webView.getSettings().setBlockNetworkImage(false);
            hideLoading();
        };
    }

    /**
     * WebView加载失败回调接口
     *
     * @return
     */
    protected Action4<WebView, Integer, String, String> onActionReceivedError() {
        return (webView, integer, s, s2) -> hideLoading();
    }

    /**
     * 隐藏Loading
     *
     * @return
     */
    protected void hideLoading() {
        Views.gone(getHorizontalBar());
        Views.gone(getLoadingContainer());
        if (centerLoadingView != null) {
            centerLoadingView.onStopLoading();
        }
    }


    /**
     * 释放 WebView资源
     */
    private void releaseWebView() {
        if (!isAttach() || webView == null) {
            return;
        }
        ViewParent viewGroup = getWebView().getParent();
        if (viewGroup != null) {
            ((ViewGroup) viewGroup).removeView(getWebView());
        }
        getWebView().clearHistory();
        getWebView().destroy();
        webView = null;
    }


    @Override
    public void onDestroy() {
        releaseWebView();
        super.onDestroy();
    }

}
