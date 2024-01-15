package io.ganguo.viewmodel.pack;

import com.jakewharton.rxrelay2.BehaviorRelay;

import io.ganguo.viewmodel.core.BaseViewModel;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * 绑定ViewInterface,ViewInterface destroy 取消Observable订阅,
 * 防止内存泄露或操作UI时崩溃
 * Created by Roger on 6/26/16.
 */
public class RxVMLifecycle {
    private static void checkNotNull(BaseViewModel viewModel) {
        if (viewModel == null) {
            throw new NullPointerException("viewModel is null");
        }
    }

    /**
     * 当ViewModel Attach 后才发射数据
     *
     * @param viewModel
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> doWhenAttach(final BaseViewModel viewModel) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(final Observable<T> source) {
                return source.flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(T t) {
                        checkNotNull(viewModel);

                        if (viewModel.isAttach()) {
                            return Observable.just(t);
                        } else {
                            return Observable.empty();
                        }
                    }
                });
            }
        };
    }

    /**
     * 注意需在数据流doOnCompleted后添加才能拦截doOnCompleted
     * 如果订阅了Subscriber里的OnComplete,则终止数据流后会调用Subscriber的OnCompleted
     * 因此OnComplete如果引用了ViewInterface 仍需在操作前 check {@link BaseViewModel#isAttach()}
     * 或者将引用的部分写到doOnCompleted里
     */
    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> bindViewModel(final BaseViewModel viewModel) {
        return source -> {
            checkNotNull(viewModel);
            return source.takeUntil(new Predicate<T>() {
                @Override
                public boolean test(T t) throws Exception {
                    return false;
                }
            });
        };
    }

    /**
     * 注意需在数据流doOnCompleted后添加才能拦截doOnCompleted
     * 如果订阅了Subscriber里的OnComplete,则终止数据流后会调用Subscriber的OnCompleted
     * 因此OnComplete如果引用了ViewInterface 仍需在操作前 check {@link BaseViewModel#isAttach()}
     * 或者将引用的部分写到doOnCompleted里
     */
    @SuppressWarnings("unchecked")
    public static <T> SingleTransformer<T, T> bindSingleViewModel(final BaseViewModel viewModel) {
        return source -> {
            checkNotNull(viewModel);
            return source.takeUntil(new CompletableSource() {
                @Override
                public void subscribe(CompletableObserver co) {

                }
            });
        };
    }


}
