package io.ganguo.rx;

import android.os.Looper;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Roger on 26/05/2017.
 */

public class SafeOperators {


    /**
     * Auto switch to main thread to handle error if current on worker thread.
     */
    public static <T> ObservableTransformer<? super T, T> doOnError(@NonNull final Consumer<Throwable> onErrorAction) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> source) {
                return source
                        // 仅在发生异常时才切回主线程处理错误，避免无谓的切换线程阻塞数据流，每切换一次线程大概需要花20~30ms
                        .onErrorResumeNext(new Function<Throwable, Observable<? extends T>>() {
                            @Override
                            public Observable<? extends T> apply(Throwable throwable) {
                                boolean isWorkerThread = Looper.myLooper() != Looper.getMainLooper();
                                // check is mainThread
                                Observable<T> errorObservable = Observable.error(throwable);
                                if (isWorkerThread) {
                                    errorObservable = errorObservable.subscribeOn(AndroidSchedulers.mainThread());
                                }
                                return errorObservable
                                        .doOnError(onErrorAction);
                            }
                        });
            }
        };
    }

    /**
     * Auto switch to main thread to do on next if current on worker thread.
     */
    public static <T> ObservableTransformer<? super T, T> doOnNext(@NonNull final Consumer<? super T> onNextAction) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(final Observable<T> source) {
                return source
                        // 仅当当前线程不是主线程时才切回主线程
                        .flatMap(new Function<T, Observable<T>>() {
                            @Override
                            public Observable<T> apply(T t) {
                                // check is mainThread
                                boolean isWorkerThread = Looper.myLooper() != Looper.getMainLooper();
                                Observable<T> stream = Observable.just(t);
                                if (isWorkerThread) {
                                    stream = stream.subscribeOn(AndroidSchedulers.mainThread());
                                }
                                return stream.doOnNext(onNextAction);
                            }
                        });
            }
        };
    }

    /**
     * Auto switch to main thread to do on complete if current on worker thread.
     */
    public static <T> ObservableTransformer<? super T, T> doOnComplete(@NonNull final Action onCompleteAction) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(final Observable<T> source) {
                return source
                        .flatMap(new Function<T, Observable<T>>() {
                            @Override
                            public Observable<T> apply(T t) {
                                // check is mainThread
                                boolean isWorkerThread = Looper.myLooper() != Looper.getMainLooper();
                                Observable<T> transformed = Observable.just(t);
                                if (isWorkerThread) {
                                    transformed = transformed.observeOn(AndroidSchedulers.mainThread());
                                }
                                return transformed;
                            }
                        })
                        .doOnComplete(onCompleteAction);
            }
        };
    }
}
