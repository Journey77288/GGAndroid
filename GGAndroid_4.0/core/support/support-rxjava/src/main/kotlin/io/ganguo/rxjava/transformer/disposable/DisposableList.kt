package io.ganguo.rxjava.transformer.disposable

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.disposables.DisposableContainer


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