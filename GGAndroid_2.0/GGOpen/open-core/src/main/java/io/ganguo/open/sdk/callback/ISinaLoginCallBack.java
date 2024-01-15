package io.ganguo.open.sdk.callback;

import io.ganguo.open.sdk.base.ILoginCallback;

/**
 * <p>
 * 新浪微博登录 - 接口回调
 * </p>
 * Created by leo on 2018/9/10.
 */
public interface ISinaLoginCallBack extends ILoginCallback {

    void onLoginSuccess(String accessToken, String uid);
}
