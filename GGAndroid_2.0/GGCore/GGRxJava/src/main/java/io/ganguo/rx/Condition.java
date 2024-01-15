package io.ganguo.rx;

/**
 * Created by Roger on 19/07/2017.
 */

public interface Condition<T> {
    boolean call(T t);
}
