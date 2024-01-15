package io.ganguo.open.sdk.callback;

import io.ganguo.open.sdk.base.ILoginCallback;

/**
 * <p>
 * QQ登录 - 结果回调API
 * </p>
 * Created by leo on 2018/9/10.
 */
public interface IQQLoginCallBack extends ILoginCallback {
    void onQQLoginQSuccess(String openId, String accessToken, String expiresIn);
}
