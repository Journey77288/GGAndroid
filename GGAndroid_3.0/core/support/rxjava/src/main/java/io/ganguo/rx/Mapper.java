package io.ganguo.rx;

/**
 * Created by Roger on 19/07/2017.
 */

public interface Mapper<T, R> {
    R map(T t);
}
