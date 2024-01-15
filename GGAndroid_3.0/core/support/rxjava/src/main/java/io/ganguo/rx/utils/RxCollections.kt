package io.ganguo.rx.utils

import io.ganguo.rx.transformer.RxTransformer
import io.ganguo.rx.transformer.interceptor.RxInterceptor
import io.ganguo.rx.transformer.interceptor.RxObservableInterceptor
import io.ganguo.rx.transformer.transfer.RxObservableTransfer
import io.ganguo.rx.transformer.transfer.RxTransfer
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/12
 *     desc   : RxJava Collections Transformer
 * </pre>
 */


/**
 * Filter for an empty collection
 * @param <T>
 * @return
 */
fun <T> filterNotEmpty(): RxInterceptor<Collection<T>> {
    return RxTransformer.warp(object : RxObservableInterceptor<Collection<T>> {
        override fun applyInterceptor(observable: Observable<Collection<T>>): Observable<Collection<T>> {
            return observable
                    .flatMap {
                        if (it.isEmpty()) {
                            Observable.empty()
                        } else {
                            Observable.just(it)
                        }
                    }
        }

    })
}


/**
 * Takes a single item from the collection
 * @return RxTransfer<T, E>
 */
fun <E> emitItems(): RxTransfer<Collection<E>, E> {
    return RxTransformer.warp(object : RxObservableTransfer<Collection<E>, E> {
        override fun applyTransfer(observable: Observable<Collection<E>>): Observable<E> {
            return observable
                    .compose(filterNotEmpty())
                    .flatMap {
                        Observable.fromIterable(it)
                    }
        }

    })
}

/**
 * Map data to List and emit items
 * @param dataMapper Convert observable data to type List
 * @return
 */
fun <T, E> emitItems(dataMapper: Function<T, Collection<E>?>): RxTransfer<T, E> {
    return RxTransformer.warp(object : RxObservableTransfer<T, E> {
        override fun applyTransfer(observable: Observable<T>): Observable<E> {
            return observable
                    .flatMap {
                        val collection = dataMapper.apply(it)
                        if (!collection.isNullOrEmpty()) {
                            Observable.fromIterable(collection)
                        } else {
                            Observable.empty()
                        }
                    }
        }
    })
}

/**
 * List<T> convert to List<E>
 * @param convert Function<T, E>
 * @return RxTransfer<Iterable<T>, List<E>>
 */
fun <T, E> cast(convert: Function<T, E>): RxTransfer<Iterable<T>, List<E>> {
    return RxTransformer.warp(object : RxObservableTransfer<Iterable<T>, List<E>> {
        override fun applyTransfer(observable: Observable<Iterable<T>>): Observable<List<E>> {
            return observable
                    .flatMap {
                        Observable.fromIterable(it)
                    }
                    .map {
                        convert.apply(it)
                    }
                    .toList()
                    .toObservable()
        }
    })
}
