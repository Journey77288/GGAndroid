package io.ganguo.rx.transformer.processor

import io.ganguo.rx.transformer.interceptor.RxObservableInterceptor
import io.ganguo.rx.utils.switchMainThread
import io.ganguo.utils.helper.ToastHelper
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : 发生错误时，同时弹出Toast提示处理
 * </pre>
 */
class ErrorToastProcessor<T> : RxObservableInterceptor<T> {

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
                        ToastHelper.showMessage(it)
                    }
                }
    }
}