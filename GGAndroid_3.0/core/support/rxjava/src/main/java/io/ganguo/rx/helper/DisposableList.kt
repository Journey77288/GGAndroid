package io.ganguo.rx.helper

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/18
 *     desc   : Disposable management interface
 * </pre>
 */
interface DisposableList : DisposableContainer, Disposable {
    val lifecycleComposite: CompositeDisposable

    /**
     * Add multiple Disposable
     * @param disposables Array<out Disposable>
     */
    fun add(vararg disposables: Disposable)


    /**
     * Batch removal of Disposable
     * @param disposables Array<out Disposable>
     */
    fun remove(vararg disposables: Disposable)


    /**
     * Release Disposable reference
     */
    fun release()

}