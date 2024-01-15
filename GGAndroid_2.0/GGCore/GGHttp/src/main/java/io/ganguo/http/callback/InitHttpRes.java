package io.ganguo.http.callback;

/**
 * <p>
 * </p>
 * Created by leo on 2018/7/30.
 */
public interface InitHttpRes<T> {

    T createModule(Class<T> tClass);

}
