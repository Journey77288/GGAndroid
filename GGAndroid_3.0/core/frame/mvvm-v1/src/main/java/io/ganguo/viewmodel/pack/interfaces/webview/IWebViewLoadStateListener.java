package io.ganguo.viewmodel.pack.interfaces.webview;

import android.graphics.Bitmap;

import com.tencent.smtt.sdk.WebView;


/**
 * WebView加载状态接口
 * Created by leo on 2018/12/4.
 */
public interface IWebViewLoadStateListener {
    void onWebLoadProgress(WebView webView, int progress);

    void onWebLoadStart(WebView webView, String s, Bitmap bitmap);

    void onWebLoadFinished(WebView webView, String s);

    void onWebLoadError(WebView webView, int i, String s, String s1);
}
