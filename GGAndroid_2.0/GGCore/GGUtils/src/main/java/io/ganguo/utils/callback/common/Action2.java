package io.ganguo.utils.callback.common;

/**
 * Created by zoyen on 2018/10/10.
 */
public interface Action2<T1, T2> extends Action {
    void call(T1 t1, T2 t2);
}