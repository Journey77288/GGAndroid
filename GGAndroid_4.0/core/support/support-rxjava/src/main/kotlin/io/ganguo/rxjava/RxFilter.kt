@file:JvmName("RxFilter")

package io.ganguo.rxjava

import io.ganguo.rxjava.transformer.RxTransformer
import io.ganguo.rxjava.transformer.interceptor.RxInterceptor
import io.ganguo.rxjava.transformer.interceptor.RxObservableInterceptor
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *   @author : leo
 *    time   : 2020/08/20
 *    desc   : RxJava数据过滤
 * </pre>
 */


/**
 * 过滤空数据，只保留非空数据
 * @return RxInterceptor<T>
 */
fun <T : Any> filterNotNull(): RxInterceptor<T> {
    return RxTransformer.warp(object : RxObservableInterceptor<T> {
        override fun applyInterceptor(observable: Observable<T>): Observable<T> {
            return observable
                    .filter {
                        it != null
                    }
        }
    })
}

/**
 * 过滤空list
 * @return RxInterceptor<List<T>>
 */
fun <T> filterListNotEmpty(): RxInterceptor<List<T>> {
    return RxTransformer.warp(object : RxObservableInterceptor<List<T>> {
        override fun applyInterceptor(observable: Observable<List<T>>): Observable<List<T>> {
            return observable
                    .filter {
                        !it.isNullOrEmpty()
                    }
        }
    })
}