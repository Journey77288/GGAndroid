package io.ganguo.viewmodel.pack.interfaces.webview;

import android.graphics.Bitmap;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * Created by leo on 2018/12/4.
 */
public class IWebViewClient extends WebViewClient {
    private IWebViewLoadStateListener stateListener;

    public IWebViewClient(IWebViewLoadStateListener stateListener) {
        this.stateListener = stateListener;
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
        if (stateListener != null) {
            stateListener.onWebLoadStart(webView, s, bitmap);
        }
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        if (stateListener != null) {
            stateListener.onWebLoadFinished(webView, s);
        }
    }

    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
        if (stateListener != null) {
            stateListener.onWebLoadError(webView, i,s,s1);
        }
    }
}
