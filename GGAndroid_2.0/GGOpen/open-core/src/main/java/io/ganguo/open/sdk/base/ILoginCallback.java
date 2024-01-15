package io.ganguo.open.sdk.base;

/**
 * Created by zoyen on 2018/7/10.
 */
public interface ILoginCallback {

    void onLoginFailed(String error);

    void onLoginCancel();

}
