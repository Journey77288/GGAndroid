package io.ganguo.rxjava.transformer

import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/19
 *     desc   : hread manipulation function
 * </pre>
 */

/**
 * Determines whether a block of code is executing in a child thread
 * @return Boolean
 */
fun isWorkThread(): Boolean {
    return Looper.myLooper() != Looper.getMainLooper()
}


/**
 * Determines whether a block of code is executed on the main thread
 * @return Boolean
 */
fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}


/**
 * Detect if observer switches to the main thread, and if not, throw an exception
 * @param observer Observer<*>
 * @return Boolean
 */
fun checkMainThread(observer: Observer<*>): Boolean {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        observer.onSubscribe(Disposable.empty())
        observer.onError(
            IllegalStateException(
                "Expected to be called on the main thread but was " + Thread.currentThread().name
            )
        )
        return false
    }
    return true
}

/**
 * Switch [Observable] to the main thread
 * @return Observable<T>
 */
fun <T : Any> Observable<T>.switchMainThread(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

/**
 * Switch [Observable] to the work thread
 * @return Observable<T>
 */
fun <T : Any> Observable<T>.switchWorkThread(): Observable<T> {
    return observeOn(Schedulers.io())
}