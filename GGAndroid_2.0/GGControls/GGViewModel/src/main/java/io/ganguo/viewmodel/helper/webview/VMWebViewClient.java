package io.ganguo.viewmodel.helper.webview;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import io.ganguo.utils.callback.common.Action2;
import io.ganguo.utils.callback.common.Action4;

/**
 * BaseWebViewModel - WebViewClient
 * Created by leo on 2018/11/12.
 */
public class VMWebViewClient extends WebViewClient {
    private Action2<WebView, String> actionFinished;
    private Action4<WebView, Integer, String, String> actionReceivedError;

    public VMWebViewClient setActionFinished(Action2<WebView, String> actionFinished) {
        this.actionFinished = actionFinished;
        return this;
    }

    public VMWebViewClient setActionReceivedError(Action4<WebView, Integer, String, String> actionReceivedError) {
        this.actionReceivedError = actionReceivedError;
        return this;
    }


    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        if (actionFinished != null) {
            actionFinished.call(webView, s);
        }
    }


    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
        if (actionReceivedError != null) {
            actionReceivedError.call(webView, i, s, s1);
        }
    }
}
