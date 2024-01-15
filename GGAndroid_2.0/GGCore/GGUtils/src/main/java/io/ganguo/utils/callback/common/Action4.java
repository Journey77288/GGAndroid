package io.ganguo.utils.callback.common;

/**
 * Created by leo on 2018/11/12.
 */
public interface Action4<T1, T2, T3, T4> extends Action {
    void call(T1 t1, T2 t2, T3 t3, T4 t4);
}
