package io.ganguo.rx;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Predicate;

/**
 * 常用的filter
 * Created by Roger on 4/29/16.
 */
public class RxFilter {

    /**
     * 过滤null Object
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> filterNotNull() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.filter(new Predicate<T>() {
                    @Override
                    public boolean test(T t) throws Exception {
                        return t != null;
                    }
                });
            }
        };
    }

    /**
     * 过滤非null Object
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> filterNull() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.filter(new Predicate<T>() {
                    @Override
                    public boolean test(T t) throws Exception {
                        return t == null;
                    }
                });
            }

        };
    }
}
