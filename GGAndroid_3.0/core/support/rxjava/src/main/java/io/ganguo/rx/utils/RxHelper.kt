package io.ganguo.rx.utils

import io.reactivex.Maybe
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable
import io.reactivex.subjects.Subject

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/12
 *     desc   : RxJava Common functions
 * </pre>
 */

/**
 * check value is null
 * @param value T?
 * @param name String
 * @return Unit
 */
fun <T> checkNull(value: T?, name: String): Unit {
    if (value == null) {
        throw NullPointerException("$name must not be null.")
    }
}

/**
 * create MaybeSource
 * @param initialValue T?
 * @return Maybe<T>?
 */
fun <T> createInitialMaybe(initialValue: T?): Maybe<T>? {
    checkNull(initialValue, "initialValue")
    return Maybe.just(initialValue)
}

/**
 * It is safe to carry out onComplete on Subject
 * @param emitter Subject<T>
 */
fun <T> safeComplete(emitter: Subject<T>) {
    if (!emitter.hasThrowable() && !emitter.hasComplete()) {
        emitter.onComplete()
    }
}

/**
 * It is safe to carry out dispose on Disposable
 * @param disposable Disposable?
 */
fun safeDispose(disposable: Disposable?) {
    if (disposable != null && !disposable.isDisposed) {
        disposable.dispose()
    }
}

/**
 * It is safe to carry out cancel on Cancellable
 * @param cancellable Cancellable?
 */
fun safeCancel(cancellable: Cancellable?) {
    if (cancellable == null) {
        return
    }
    try {
        cancellable.cancel()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}