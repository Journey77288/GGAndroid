@file:JvmName("RxHelper")

package com.oneplus.mall.util.rx

import io.ganguo.rxjava.transformer.RxTransformer
import io.ganguo.rxjava.transformer.interceptor.RxInterceptor
import io.ganguo.rxjava.transformer.interceptor.RxObservableInterceptor
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *   @author : leo
 *    time   : 2020/08/20
 *    desc   : RxJava 工具类
 * </pre>
 */



/**
 * 对数据进行for循环遍历
 * @return RxInterceptor<List<T>>
 */
fun <T> forEachItem(itemFunc: (Int, T) -> Unit): RxInterceptor<List<T>> {
    return RxTransformer.warp(object : RxObservableInterceptor<List<T>> {
        override fun applyInterceptor(observable: Observable<List<T>>): Observable<List<T>> = let {
            observable
                    .doOnNext {
                        for (index in it.indices) {
                            itemFunc.invoke(index, it[index])
                        }
                    }
        }

    })
}