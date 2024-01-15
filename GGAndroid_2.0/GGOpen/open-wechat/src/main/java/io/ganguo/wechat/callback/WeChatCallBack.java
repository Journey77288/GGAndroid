package io.ganguo.wechat.callback;

import io.ganguo.open.sdk.callback.IWeChatCallBack;
import io.ganguo.utils.util.Tasks;

/**
 * <p>
 * 微信登录/分享 - 结果回调包装类
 * </p>
 * Created by leo on 2018/9/10.
 */
public class WeChatCallBack implements IWeChatCallBack {
    private IWeChatCallBack iWeChatCallBack;

    public WeChatCallBack(IWeChatCallBack iWeChatCallBack) {
        this.iWeChatCallBack = iWeChatCallBack;
    }

    @Override
    public void onCancel() {
        if (iWeChatCallBack == null) {
            return;
        }
        Tasks.runOnUiThread(() -> iWeChatCallBack.onCancel());
    }

    @Override
    public void onSuccess(String code) {
        if (iWeChatCallBack == null) {
            return;
        }
        Tasks.runOnUiThread(() -> iWeChatCallBack.onSuccess(code));
    }

    @Override
    public void onFailed(RuntimeException t) {
        if (iWeChatCallBack == null) {
            return;
        }
        Tasks.runOnUiThread(() -> iWeChatCallBack.onFailed(t));
    }

}
