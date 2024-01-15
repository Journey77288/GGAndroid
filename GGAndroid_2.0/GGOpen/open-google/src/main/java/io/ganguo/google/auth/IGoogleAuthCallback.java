package io.ganguo.google.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.ganguo.open.sdk.base.IOpenCallBack;

/**
 * <p>
 * Google 账号登录 结果回调
 * </p>
 * Created by leo on 2018/9/5.
 */
public interface IGoogleAuthCallback extends IOpenCallBack<GoogleSignInAccount, Throwable> {
}
