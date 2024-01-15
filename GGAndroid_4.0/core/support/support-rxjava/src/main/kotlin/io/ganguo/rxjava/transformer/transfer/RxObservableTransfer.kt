package io.ganguo.rxjava.transformer.transfer

import io.reactivex.rxjava3.core.Observable


/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/27
 *     desc   : Transfer Observable
 * </pre>
 */
interface RxObservableTransfer<T : Any, R : Any> {
    /**
     * use the observable transfer (对Observable数据进行转换)
     * @param observable Observable
     * @return Observable<T>
     */
    fun applyTransfer(observable: Observable<T>): Observable<R>
}