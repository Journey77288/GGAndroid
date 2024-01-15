package io.ganguo.utils.callback.common;

/**
 * Created by zoyen on 2018/10/10.
 */
public interface Action1<T> extends Action {
    void call(T t);
}
