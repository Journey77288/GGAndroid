package io.ganguo.open.sdk.base;

/**
 * <p>
 * 基类
 * </p>
 * Created by leo on 2018/9/5.
 */
public interface IOpenCallBack<T, E> {
    void onSuccess(T t);

    void onFailed(E t);

}
