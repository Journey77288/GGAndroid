package io.ganguo.qq.share;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import io.ganguo.open.sdk.base.ICallback;
import io.ganguo.qq.QQManager;

/**
 * QQ 分享回调
 * Created by zoyen on 2018/7/11.
 */
public class QQShareListener implements IUiListener {

    private ICallback mICallback;

    public QQShareListener(ICallback ICallback) {
        this.mICallback = ICallback;
    }

    @Override
    public void onComplete(Object o) {
        mICallback.onSuccess();
        QQManager.clearService();
    }

    @Override
    public void onError(UiError uiError) {
        mICallback.onFailed();
        QQManager.clearService();
    }

    @Override
    public void onCancel() {
        mICallback.onCancel();
        QQManager.clearService();
    }
}
