package io.ganguo.open.sdk.callback;

import io.ganguo.open.sdk.base.IOpenCallBack;

/**
 * <p>
 * 微信登录/分享 - 结果回调
 * </p>
 * Created by leo on 2018/9/10.
 */
public interface IWeChatCallBack extends IOpenCallBack<String, RuntimeException> {
    void onCancel();
}
