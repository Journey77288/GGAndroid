package io.ganguo.utils.callback.common;

/**
 * Created by leo on 2018/11/12.
 */
public interface Action3<T1, T2,T3> extends Action {
    void call(T1 t1, T2 t2,T3 t3);
}
