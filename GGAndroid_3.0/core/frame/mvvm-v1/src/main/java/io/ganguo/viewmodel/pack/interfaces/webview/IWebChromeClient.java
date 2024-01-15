package io.ganguo.viewmodel.pack.interfaces.webview;


import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by leo on 2018/12/4.
 */
public class IWebChromeClient extends WebChromeClient {
    private IWebViewLoadStateListener stateListener;

    public IWebChromeClient(IWebViewLoadStateListener stateListner) {
        this.stateListener = stateListner;
    }

    @Override
    public void onProgressChanged(WebView webView, int progress) {
        super.onProgressChanged(webView, progress);
        if (stateListener != null) {
            stateListener.onWebLoadProgress(webView, progress);
        }
    }
}
