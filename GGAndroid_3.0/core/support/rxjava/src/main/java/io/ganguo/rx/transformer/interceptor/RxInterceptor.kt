package io.ganguo.rx.transformer.interceptor

import io.ganguo.rx.transformer.transfer.RxTransfer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/04
 *     desc   : RxJava Interceptor
 * </pre>
 */
class RxInterceptor<T> internal constructor(var interceptor: RxObservableInterceptor<T>) : RxTransfer<T, T>(interceptor) {
}