package io.ganguo.rx.transformer.processor

import io.ganguo.log.core.Logger
import io.ganguo.rx.transformer.interceptor.RxObservableInterceptor
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : 错误打印处理
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class ErrorLoggerProcessor<T> : RxObservableInterceptor<T> {
    override fun applyInterceptor(observable: Observable<T>): Observable<T> {
        return observable
                .doOnError {
                    it.printStackTrace()
                    Logger.e("PrintErrorProcessor:${it.message}")
                }
    }

    /**
     * observable Transfer Function
     * @param observable Observable<T>
     * @return Observable<T>
     */
//    override fun applyInterceptor(observable: Observable<out T>): Observable<in T> {
//        return observable
//                .doOnError {
//                    it.printStackTrace()
//                    Logger.e("PrintErrorProcessor:${it.message}")
//                }
//    }

}