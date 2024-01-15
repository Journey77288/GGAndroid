package io.ganguo.rxjava.transformer.processor

import io.ganguo.rxjava.RxHelper
import io.ganguo.rxjava.transformer.interceptor.RxObservableInterceptor
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : 错误打印处理
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class ErrorLoggerProcessor<T : Any> : RxObservableInterceptor<T> {

    /**
     * observable Transfer Function
     * @param observable Observable<T>
     * @return Observable<T>
     */
    override fun applyInterceptor(observable: Observable<T>): Observable<T> {
        return observable
                .doOnError {
                    it.printStackTrace()
                    RxHelper.get().throwable?.invoke(it)
                    RxHelper.get().logger?.invoke("ErrorLoggerProcessor:error:" + it.message)
                }
    }
}