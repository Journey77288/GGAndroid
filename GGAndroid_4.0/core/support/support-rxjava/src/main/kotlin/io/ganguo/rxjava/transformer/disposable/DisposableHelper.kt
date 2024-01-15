package io.ganguo.rxjava.transformer.disposable

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/18
 *     desc   : Disposable management tool class, aa instance, after use, pay attention to release, to avoid memory leak
 * </pre>
 */
class DisposableHelper private constructor() : DisposableList {
    override val lifecycleComposite: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    /**
     * Add multiple Disposable
     * @param disposables Disposable
     */
    override fun add(vararg disposables: Disposable) {
        lifecycleComposite.addAll(*disposables)
    }

    /**
     * Add  Disposable
     * @param d Disposable
     */
    override fun add(d: Disposable?): Boolean {
        return if (null == d) {
            false
        } else {
            lifecycleComposite.add(d)
        }
    }


    /**
     * Batch removal of Disposable
     * @param disposables Disposable
     */
    override fun remove(vararg disposables: Disposable) {
        disposables.forEach {
            remove(it)
        }
    }

    /**
     * Remove the Disposable
     * @param d Disposable
     * @return Boolean
     */
    override fun remove(d: Disposable?): Boolean {
        return if (null == d) {
            false
        } else {
            lifecycleComposite.remove(d)
        }
    }

    /**
     * Release Disposable reference
     */
    override fun release() {
        dispose()
    }

    /**
     * Delete aa from [lifecycleComposite],The dispose operation is not performed here
     * @param d Disposable
     * @return Boolean
     */
    override fun delete(d: Disposable?): Boolean {
        return if (d == null) {
            false
        } else {
            lifecycleComposite.delete(d)
        }
    }

    /**
     * 判断[lifecycleComposite]是否以及被释放
     * @return Boolean
     */
    override fun isDisposed(): Boolean {
        return lifecycleComposite.isDisposed
    }

    /**
     * 释放[lifecycleComposite]容器中所有[Disposable]
     */
    override fun dispose() {
        lifecycleComposite.dispose()
        lifecycleComposite.clear()
    }

    companion object {

        /**
         * 创建 RxDisposable
         * @return
         */
        @JvmStatic
        fun create(): DisposableList {
            return DisposableHelper()
        }

    }

}