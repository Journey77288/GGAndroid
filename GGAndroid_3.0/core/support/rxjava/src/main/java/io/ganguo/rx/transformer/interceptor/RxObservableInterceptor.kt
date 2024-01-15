package io.ganguo.rx.transformer.interceptor

import io.ganguo.rx.transformer.transfer.RxObservableTransfer
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/04
 *     desc   : Observable  data Interceptor
 * </pre>
 */
interface RxObservableInterceptor<T> : RxObservableTransfer<T, T> {
    /**
     * use the observable interceptor (对Observable进行拦截过滤)
     * @param observable Observable
     * @return Observable<T>
     */
    fun applyInterceptor(observable: Observable<T>): Observable<T>


    /**
     * observable Transfer Function
     * @param observable Observable<T>
     * @return Observable<T>
     */
    override fun applyTransfer(observable: Observable<T>): Observable<T> {
        return applyInterceptor(observable)
    }
}