package io.ganguo.rx;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 条件Transformer
 * Created by Roger on 5/4/16.
 */
public class RxStatement {

    /**
     * 如果condition满足执行then,否则执行orElse
     * 用于将原来的Observable根据条件转换不同类型/同类型的Observable
     *
     * @param condition 控制条件
     * @param then      if {@code condition} is {@code true} return an Observable sequence
     * @param orElse    if {@code condition} is {@code false} return an Observable sequence
     */
    public static <T, R> ObservableTransformer<T, R> ifThen(final Condition<? super T> condition,
                                                            final Mapper<? super T, ? extends Observable<R>> then,
                                                            final Mapper<T, ? extends Observable<R>> orElse) {
        return new ObservableTransformer<T, R>() {
            @Override
            public Observable<R> apply(final Observable<T> tObservable) {
                return tObservable.flatMap(new Function<T, ObservableSource<? extends R>>() {
                    @Override
                    public Observable<? extends R> apply(T t) {
                        return condition.call(t) ? then.map(t) : orElse.map(t);
                    }
                });
            }
        };
    }

    /**
     * 如果condition满足执行then,否则执行orElse
     *
     * @param condition 控制条件
     * @param then      if {@code condition} is {@code true} return an Observable sequence
     * @param orElse    if {@code condition} is {@code false} return an Observable sequence
     */
    public static <T, R> ObservableTransformer<T, R> ifThen(final boolean condition,
                                                            final Mapper<? super T, ? extends Observable<R>> then,
                                                            final Mapper<T, ? extends Observable<R>> orElse) {
        return new ObservableTransformer<T, R>() {
            @Override
            public Observable<R> apply(Observable<T> tObservable) {
                return tObservable.compose(RxStatement.ifThen(
                        new Condition<T>() {
                            @Override
                            public boolean call(T t) {
                                return condition;
                            }
                        },
                        then,
                        orElse));
            }
        };
    }

    /**
     * 如果condition满足则执行then,否则返回原数据序列
     *
     * @param condition
     * @param then
     * @param <T>
     * @param <R>
     * @return
     */
    @SuppressWarnings("unchecked cast")
    public static <T, R> ObservableTransformer<T, R> ifThen(final Condition<? super T> condition,
                                                            final Mapper<? super T, ? extends Observable<? extends R>> then) {
        return new ObservableTransformer<T, R>() {
            @Override
            public Observable<R> apply(final Observable<T> tObservable) {
                return tObservable.flatMap(new Function<T, ObservableSource<R>>() {
                    @Override
                    public Observable<R> apply(T t) {
                        return condition.call(t) ?
                                (Observable<R>) then.map(t) :
                                Observable.just((R) t);
                    }
                });
            }
        };
    }

    @SuppressWarnings("unchecked cast")
    public static <T, R> ObservableTransformer<T, R> ifThen(final boolean condition,
                                                            final Mapper<? super T, ? extends Observable<? extends R>> then) {
        return new ObservableTransformer<T, R>() {
            @Override
            public Observable<R> apply(final Observable<T> tObservable) {
                return tObservable.flatMap(new Function<T, ObservableSource<R>>() {
                    @Override
                    public Observable<R> apply(T t) {
                        return condition ?
                                (Observable<R>) then.map(t) :
                                Observable.just((R) t);
                    }
                });
            }
        };
    }

    /**
     * 如果condition满足执行then,否则执行orElse
     * 用于场景处理数据后不用进行类型转换
     *
     * @param condition 控制条件
     * @param then      if {@code condition} is {@code true} run the action
     * @param orElse    if {@code condition} is {@code false} run the action
     */
    public static <T> ObservableTransformer<T, T> ifThen(final Condition<? super T> condition,
                                                         final Consumer<? super T> then,
                                                         final Consumer<? super T> orElse) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(final Observable<T> tObservable) {
                return tObservable.flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(final T t) {
                        return Observable.fromCallable(new Callable<T>() {
                            @Override
                            public T call() throws Exception {
                                if (condition.call(t)) {
                                    then.accept(t);
                                } else {
                                    orElse.accept(t);
                                }
                                return t;
                            }
                        });
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> ifThen(final boolean condition,
                                                         final Consumer<? super T> then,
                                                         final Consumer<? super T> orElse) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(final Observable<T> tObservable) {
                return tObservable.flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(final T t) {
                        return Observable.fromCallable(new Callable<T>() {
                            @Override
                            public T call() throws Exception {
                                if (condition) {
                                    then.accept(t);
                                } else {
                                    orElse.accept(t);
                                }
                                return t;
                            }
                        });
                    }
                });
            }
        };
    }

    /**
     * 如果condition满足执行then,否则直接返回原数据序列
     * 用于场景处理数据后不用进行类型转换
     *
     * @param condition 控制条件
     * @param then      if {@code condition} is {@code true} run the action
     */
    public static <T> ObservableTransformer<T, T> ifThen(final Condition<? super T> condition,
                                                         final Consumer<? super T> then) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(final Observable<T> tObservable) {
                return tObservable.flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(final T t) {
                        return Observable.fromCallable(new Callable<T>() {
                            @Override
                            public T call() throws Exception {
                                if (condition.call(t)) {
                                    then.accept(t);
                                }
                                return t;
                            }
                        });
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> ifThen(final boolean condition,
                                                         final Consumer<? super T> then) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(final Observable<T> tObservable) {
                return tObservable.flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(final T t) {
                        return Observable.fromCallable(new Callable<T>() {
                            @Override
                            public T call() throws Exception {
                                if (condition) {
                                    then.accept(t);
                                }
                                return t;
                            }
                        });
                    }
                });
            }
        };
    }


    /**
     * forEach操作符,传入列表,返回一个带有位置的对象
     * 适用于forEach类操作
     *
     * @param <B>
     */
    public static class forEach<B> implements ObservableTransformer<List<B>, Indexed<B>> {
        @Override
        public Observable<Indexed<B>> apply(Observable<List<B>> listObservable) {
            return listObservable
                    .flatMap(new Function<List<B>, Observable<B>>() {
                        @Override
                        public Observable<B> apply(List<B> bs) {
                            return Observable.fromIterable(bs);
                        }
                    })
                    .zipWith(NaturalNumbers.instance(), new BiFunction<B, Long, Indexed<B>>() {
                        @Override
                        public Indexed<B> apply(B b, Long aLong) {
                            return new Indexed<B>(b, aLong);
                        }
                    });
        }
    }

    public static class Indexed<T> {
        private final long index;
        private final T value;

        public Indexed(T value, long index) {
            this.index = index;
            this.value = value;
        }

        @Override
        public String toString() {
            return index + "->" + value;
        }

        public long index() {
            return index;
        }

        public T value() {
            return value;
        }
    }

    private static final class NaturalNumbers implements Iterable<Long> {
        private static class Holder {
            static final NaturalNumbers INSTANCE = new NaturalNumbers();
        }

        static NaturalNumbers instance() {
            return Holder.INSTANCE;
        }

        @Override
        public Iterator<Long> iterator() {
            return new Iterator<Long>() {
                private long n = 0;

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Long next() {
                    return n++;
                }

                @Override
                public void remove() {
                    throw new RuntimeException("not supported");
                }
            };
        }
    }
}
