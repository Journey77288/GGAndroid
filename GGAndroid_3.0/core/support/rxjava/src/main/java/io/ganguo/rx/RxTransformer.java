package io.ganguo.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by Roger on 8/27/16.
 */
public class RxTransformer {

    public static <T> ObservableTransformer<T, Ignore> toIgnore() {
        return new ObservableTransformer<T, Ignore>() {
            @Override
            public Observable<Ignore> apply(Observable<T> source) {
                return source.flatMap(new Function<T, Observable<Ignore>>() {
                    @Override
                    public Observable<Ignore> apply(T t) {
                        return Observable.just(Ignore.INSTANCE);
                    }
                });
            }
        };
    }
}
