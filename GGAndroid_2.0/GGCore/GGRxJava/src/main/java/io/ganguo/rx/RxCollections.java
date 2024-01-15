package io.ganguo.rx;


import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 集合类常用操作符
 * Created by Roger on 4/29/16.
 */
public class RxCollections {

    /**
     * 如果集合不为空，发射原数据集合，否则发射一个ObservableEmpty
     *
     * @param <T>
     * @return
     */
    public static <T extends Collection> ObservableTransformer<T, T> filterNotEmpty() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upStream) {
                return upStream.compose(RxStatement.ifThen(
                        new Condition<T>() {
                            @Override
                            public boolean call(T collection) {
                                return isEmpty(collection);
                            }
                        },
                        new Mapper<T, Observable<T>>() {
                            @Override
                            public Observable<T> map(T t) {
                                return Observable.<T>empty();
                            }
                        }
                ));
            }
        };
    }

    public static <E, T extends Collection<E>> ObservableTransformer<T, E> emitItems() {
        return new ObservableTransformer<T, E>() {
            @Override
            public Observable<E> apply(Observable<T> tObservable) {
                return tObservable.compose(RxCollections.<T>filterNotEmpty())
                        .flatMap(new Function<T, Observable<E>>() {
                            @Override
                            public Observable<E> apply(T collection) {
                                return Observable.fromIterable(collection);
                            }
                        });
            }
        };
    }

    /**
     * Map data to List and emit items
     *
     * @param dataMapper 转换 upStream data 为 List 类型
     * @param <T>        upStream 数据类型
     * @param <E>        发射的 item 数据类型
     * @return
     */
    public static <T, E> ObservableTransformer<T, E> emitItems(@NonNull final Mapper<T, Collection<E>> dataMapper) {
        return new ObservableTransformer<T, E>() {
            @Override
            public Observable<E> apply(Observable<T> upStream) {
                return upStream
                        .flatMap(new Function<T, Observable<E>>() {
                            @Override
                            public Observable<E> apply(T t) {
                                Collection<E> collection = dataMapper.map(t);
                                if (!isEmpty(collection)) {
                                    return Observable.fromIterable(collection);
                                } else {
                                    return Observable.empty();
                                }
                            }
                        });
            }
        };
    }

    /**
     * 转换原 List 为另一种 List 类型
     */
    /**
     * @param convertor 转换器
     * @param <T>       原 List item 数据类型
     * @param <E>       转换后的 List item 数据类型
     * @return
     */
    public static <T, E> ObservableTransformer<Iterable<T>, List<E>> cast(@NonNull final Mapper<T, E> convertor) {
        return new ObservableTransformer<Iterable<T>, List<E>>() {
            @Override
            public Observable<List<E>> apply(Observable<Iterable<T>> upStream) {
                return upStream
                        .flatMap(new Function<Iterable<T>, Observable<List<E>>>() {
                            @Override
                            public Observable<List<E>> apply(Iterable<T> ts) {
                                return Observable.fromIterable(ts)
                                        .map(new Function<T, E>() {
                                            @Override
                                            public E apply(T t) throws Exception {
                                                return convertor.map(t);
                                            }
                                        })
                                        .toList()
                                        .toObservable();
                            }
                        });
            }
        };
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}