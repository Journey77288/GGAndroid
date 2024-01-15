package io.ganguo.viewmodel.helper.webview;

import android.view.View;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import io.ganguo.utils.callback.common.Action2;
import io.ganguo.utils.util.Views;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * BaseWebViewModel - WebChromeClient
 * Created by leo on 2018/11/12.
 */
public class VMWebChromeClient extends WebChromeClient {
    protected MaterialProgressBar horizontalBar;
    private boolean isShowHorizontalBar;
    private Action2<WebView, String> actionLoadFinish;

    public VMWebChromeClient setHorizontalBar(MaterialProgressBar horizontalBar) {
        this.horizontalBar = horizontalBar;
        return this;
    }

    public VMWebChromeClient setActionLoadFinish(Action2<WebView, String> actionLoadFinish) {
        this.actionLoadFinish = actionLoadFinish;
        return this;
    }


    public VMWebChromeClient setShowHorizontalBar(boolean showHorizontalBar) {
        isShowHorizontalBar = showHorizontalBar;
        return this;
    }


    @Override
    public void onProgressChanged(WebView webView, int progress) {
        super.onProgressChanged(webView, progress);
        updateLoadProgress(webView, progress);
    }


    /**
     * function: 更新加载进度
     *
     * @param progress
     */
    private void updateLoadProgress(WebView webView, int progress) {
        boolean isLoadFinish = (progress >= 100);
        if (isShowHorizontalBar && horizontalBar != null) {
            horizontalBar.setProgress(progress);
        }
        if (isLoadFinish && actionLoadFinish != null) {
            actionLoadFinish.call(webView, webView.getUrl());
        }
    }
}
