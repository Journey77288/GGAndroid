package io.ganguo.mvvm.viewmodel

import androidx.databinding.BaseObservable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import io.ganguo.lifecycle.LifecycleObserverAdapter
import io.ganguo.lifecycle.LifecycleBindingAdapter
import io.ganguo.lifecycle.LifecycleObserverListener

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : ViewModel 生命周期管理
 * </pre>
 */
abstract class ViewModelLifecycle : BaseObservable(), LifecycleObserverListener, LifecycleBindingAdapter {
    /**
     * 用于绑定Observable生命周期事件
     *
     * @see {@link io.ganguo.mvvm.pack.RxVMLifecycle.bindSingleViewModel
     */
    protected lateinit var lifecycleOwner: LifecycleOwner
    protected val viewModelScope: LifecycleCoroutineScope by lazy { lifecycleOwner.lifecycleScope }
    private val lifecycleAdapter: LifecycleObserver by lazy {
        LifecycleObserverAdapter(this)
    }


    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onDestroy() {
    }


    /**
     * Binding Lifecycle Observer Lifecycle to LifecycleOwner
     * @param owner
     */
    override fun bindLifecycle(owner: LifecycleOwner) {
        if (::lifecycleOwner.isInitialized) {
            unbindLifecycle(lifecycleOwner)
        }
        this.lifecycleOwner = owner
        this.lifecycleOwner.lifecycle.addObserver(lifecycleAdapter)
    }

    /**
     * Remove  Lifecycle Observer from LifecycleOwner
     * @param owner
     */
    override fun unbindLifecycle(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(lifecycleAdapter)
    }

}
