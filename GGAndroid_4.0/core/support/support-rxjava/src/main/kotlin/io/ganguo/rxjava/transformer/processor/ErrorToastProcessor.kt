package io.ganguo.rxjava.transformer.processor

import io.ganguo.rxjava.RxHelper
import io.ganguo.rxjava.transformer.interceptor.RxObservableInterceptor
import io.ganguo.rxjava.transformer.switchMainThread
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : 发生错误时，同时弹出Toast提示处理
 * </pre>
 */
class ErrorToastProcessor<T : Any> : RxObservableInterceptor<T> {

    /**
     * observable Transfer Function
     * @param observable Observable<T>
     * @return Observable<T>
     */
    override fun applyInterceptor(observable: Observable<T>): Observable<T> {
        return observable
                .switchMainThread()
                .doOnError { throwable: Throwable ->
                    throwable.message?.let {
                        RxHelper.get().toast?.invoke(it)
                    }
                    RxHelper.get().throwable?.invoke(throwable)
                }
    }
}
