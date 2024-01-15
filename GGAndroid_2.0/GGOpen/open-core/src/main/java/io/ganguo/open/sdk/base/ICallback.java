package io.ganguo.open.sdk.base;

/**
 * Created by Lynn on 3/25/16.
 */
public interface ICallback {
    /**
     * 分享/登录成功
     */
    void onSuccess();

    /**
     * 分享/登录取消
     */
    void onCancel();

    /**
     * 分享/登录失败
     */
    void onFailed();
}
