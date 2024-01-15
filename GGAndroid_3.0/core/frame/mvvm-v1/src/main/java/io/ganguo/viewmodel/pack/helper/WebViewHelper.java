package io.ganguo.viewmodel.pack.helper;

import android.os.Build;
import android.view.View;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import io.ganguo.viewmodel.pack.interfaces.webview.IWebChromeClient;
import io.ganguo.viewmodel.pack.interfaces.webview.IWebViewClient;
import io.ganguo.viewmodel.pack.interfaces.webview.IWebViewLoadStateListener;

/**
 * WebViewHelper
 * Created by leo on 2018/12/4.
 */
public class WebViewHelper {
    //App WebView最大缓存
    private static final long APP_CACHE_MAX_SIZE = 1024 * 1024 * 50;

    /**
     * bind WebView
     *
     * @param webView
     */
    public static void bindTbsWebView(WebView webView, IWebViewLoadStateListener loadStateListener) {
        if (webView == null) {
            return;
        }
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条在WebView外侧显示
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_INSET);
        webView.setScrollbarFadingEnabled(true);
        webView.setWebChromeClient(new IWebChromeClient(loadStateListener));
        webView.setWebViewClient(new IWebViewClient(loadStateListener));
        if (webView.getX5WebViewExtension() != null) {
            webView.getX5WebViewExtension().setHorizontalScrollBarEnabled(false);
            webView.getX5WebViewExtension().setVerticalScrollBarEnabled(false);
        }
        if (!(webView.getView() instanceof android.webkit.WebView)) {
            return;
        }
        android.webkit.WebView webkit = (android.webkit.WebView) webView.getView();
        webkit.setHorizontalScrollBarEnabled(false);
        webkit.setVerticalScrollBarEnabled(false);
        webkit.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条在WebView外侧显示
    }


    /**
     * bind WebView Setting
     *
     * @param webView
     */
    public static void bindTbsWebViewSetting(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.supportMultipleWindows();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLoadsImagesAutomatically(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheMaxSize(APP_CACHE_MAX_SIZE);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(settings.getMixedContentMode());
        }
    }
}
