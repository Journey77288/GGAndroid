package io.ganguo.lifecycle

import androidx.lifecycle.LifecycleOwner

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/21
 *   @desc   : Lifecycle Listener
 * </pre>
 */
interface LifecycleBindingAdapter {
    /**
     * Binding Lifecycle Observer Lifecycle to LifecycleOwner
     * @param owner
     */
    fun bindLifecycle(owner: LifecycleOwner)


    /**
     * Remove  Lifecycle Observer from LifecycleOwner
     * @param owner
     */
    fun unbindLifecycle(owner: LifecycleOwner)
}
