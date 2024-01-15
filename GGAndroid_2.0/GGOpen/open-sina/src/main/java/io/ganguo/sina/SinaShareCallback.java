package io.ganguo.sina;

import com.sina.weibo.sdk.share.WbShareCallback;

/**
 * 新浪微博分享接口回调
 * Created by zoyen on 2018/7/11.
 */
public class SinaShareCallback implements WbShareCallback {

    private WbShareCallback callback;

    public SinaShareCallback(WbShareCallback ICallback) {
        this.callback = ICallback;
    }

    @Override
    public void onWbShareSuccess() {
        callback.onWbShareSuccess();
    }

    @Override
    public void onWbShareCancel() {
        callback.onWbShareCancel();
    }

    @Override
    public void onWbShareFail() {
        callback.onWbShareFail();
    }


}
