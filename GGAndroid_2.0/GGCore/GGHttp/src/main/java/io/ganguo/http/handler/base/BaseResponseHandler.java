package io.ganguo.http.handler.base;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import io.ganguo.http.error.ExceptionFactory;
import io.ganguo.http.error.ExceptionHelper;
import io.ganguo.utils.exception.BaseException;
import io.ganguo.utils.util.Collections;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * <p>
 * ResponseHandler基类 - 封装了已知，但不确定具体规则的抽象方法，由子类处理
 * </p>
 * Created by leo on 2018/7/17.
 */

public abstract class BaseResponseHandler<B, R> implements ObservableTransformer<B, R> {
    //用于过滤API错误，后添加的过滤优先级大于先添加的，且优先级大于默认的onDefaultInterceptFunction()
    public List<Function<B, ? extends Throwable>> interceptErrors = new ArrayList<>();

    public BaseResponseHandler() {
    }

    @Override
    public ObservableSource<R> apply(Observable<B> upstream) {
        return upstream
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(onInterceptResponse())
                .doOnError(onHttpErrorAction());
    }

    /**
     * function：处理一些全局的API错误/异常
     * <p>
     * 注：如果某些API接口，需要处理特殊错误，可以直接重写RxJava的doOnError函数处理
     * <p/>
     *
     * @return
     */
    protected Consumer<Throwable> onHttpErrorAction() {
        return throwable -> ExceptionHelper.onHandlerThrowable(throwable);
    }


    /**
     * function：处理API返回的数据
     *
     * @return
     */
    protected Function<B, ObservableSource<R>> onInterceptResponse() {
        return b -> {
            //自定义错误过滤规则判断
            BaseException exception = onInterceptResponseError(b);
            if (exception != null) {
                return Observable.error(exception);
            }
            R r = onHandlerResponse(b);
            //接口返回data如果为null，则返回一个空Observable
            if (r == null) {
                return Observable.empty();
            }
            return Observable.just(r);
        };
    }

    /**
     * function：Api is Response Error
     * <p>
     * 1、遍历interceptErrors数组中的接口，根据接口中的错误规则，进行错误数据过滤
     * 2、无错误则返回null，说明API请求数据成功
     * <p/>
     *
     * @param b
     * @return
     */
    protected BaseException onInterceptResponseError(B b) throws Exception {
        BaseException exception = onFilterInterceptErrors(b);
        return exception;
    }


    /**
     * function：用于过滤API错误，后添加到interceptErrors数组中的接口，过滤优先级大于先添加的
     *
     * @param b
     * @return
     */
    protected <T extends Throwable> T onFilterInterceptErrors(B b) throws Exception {
        Iterator<Function<B, ? extends Throwable>> it = interceptErrors.iterator();
        T throwable;
        while (it.hasNext()) {
            throwable = (T) it.next().apply(b);
            if (throwable != null) {
                return throwable;
            }
        }
        throwable = (T) onDefaultInterceptFunction().apply(b);
        return throwable;
    }


    /**
     * function：get InterceptErrors
     *
     * @return
     */
    public List<Function<B, ? extends Throwable>> getInterceptErrors() {
        if (interceptErrors == null) {
            interceptErrors = new ArrayList<>();
        }
        return interceptErrors;
    }

    /**
     * function：add Function
     * <p>
     * 可以传入Func1，在外部过滤判断一些特别的出错情况
     * <p/>
     *
     * @return
     */
    public <S extends BaseResponseHandler<B, R>> S addInterceptError(@NonNull Function<B, ? extends BaseException>... func1) {
        this.interceptErrors.addAll(Arrays.asList(func1));
        return (S) this;
    }


    /**
     * function：创建默认的，接口数据错误过滤规则
     *
     * @see {@link #addInterceptError(Function[])}
     * <p>
     * 注：Handler必须要有一个错误数据过滤规则，如果某些API接口，需要处理特殊错误，也可以直接重写RxJava的doOnError函数处理
     * <p/>
     */
    protected abstract Function<B, ? extends Throwable> onDefaultInterceptFunction();

    /**
     * function：handler Response data
     * <p>
     * 处理API返回数据
     * </p>
     *
     * @return
     */
    protected abstract R onHandlerResponse(B b);
}
