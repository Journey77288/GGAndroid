package io.ganguo.rxjava.transformer.interceptor

import io.ganguo.rxjava.transformer.transfer.RxTransfer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/04
 *     desc   : RxJava Interceptor
 * </pre>
 */
class RxInterceptor<T : Any> internal constructor(interceptor: RxObservableInterceptor<T>) : RxTransfer<T, T>(interceptor) {
}