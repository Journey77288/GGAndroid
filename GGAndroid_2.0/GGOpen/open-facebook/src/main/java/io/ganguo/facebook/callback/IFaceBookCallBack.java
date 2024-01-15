package io.ganguo.facebook.callback;

import io.ganguo.open.sdk.base.IOpenCallBack;

/**
 * <p>
 * FaceBook分享、登录接口回调
 * </p>
 * Created by leo on 2018/9/10.
 */
public interface IFaceBookCallBack<T> extends IOpenCallBack<T, Throwable> {
    void onCancel();
}
