package io.ganguo.mvvm.viewmodel

import android.content.Context
import android.view.View
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.resources.ResourcesDelegate
import io.ganguo.resources.ResourcesHelper
import io.ganguo.rxjava.transformer.disposable.DisposableHelper
import io.ganguo.rxjava.transformer.disposable.DisposableList
import io.support.recyclerview.adapter.hodler.LayoutId


/**
 *
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Base ViewModel
 * </pre>
 *
 * @param V : ViewInterface<*>
 * @property lifecycleOwner LifecycleHelper
 * @property viewIF V
 * @property onViewAttachListener OnViewAttachListener<ViewModel<*>>?
 * @property variableId Int
 * @property rootView View
 * @property context Context
 * @property isAttach Boolean
 * @property lifecycleComposite CompositeDisposable
 */
@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class ViewModel<V : ViewInterface<*>> : ViewModelLifecycle(), LayoutId,
        ResourcesDelegate by ResourcesHelper, DisposableList by DisposableHelper.create() {

    /**
     * ViewModel rootView
     */
    val rootView: View
        get() = let {
            checkAttach()
            viewIF.binding.root
        }

    /**
     * ViewModel Context
     */
    open val context: Context
        get() = let {
            checkAttach()
            viewIF.viewContext
        }

    /**
     * check if ViewModel attach to viewInterface
     */
    fun isAttach(): Boolean = let {
        ::viewIF.isInitialized
    }

    /**
     * check if ViewModel attach to viewInterface，If not, an exception is thrown
     */
    protected open fun checkAttach() {
        check(isAttach()) {
            "${javaClass.simpleName}: is Not attach，Please check the code！！"
        }
    }

    /**
     * check if ViewModel Not attach to viewInterface，If not, an exception is thrown
     * @param tag String
     */
    protected open fun checkNotAttach(tag: String = "") {
        check(!isAttach()) {
            "${javaClass.simpleName}: $tag  must be called before Attach，Please check the code！！"
        }
    }

    /**
     * OnViewAttachListener
     *
     * @return
     */
    var onViewAttachListener: OnViewAttachListener<ViewModel<*>>? = null

    /**
     * Set VariableId before attach
     */
    var variableId: Int = 0

    /**
     * ViewInterface<*>
     */
    lateinit var viewIF: V

    /**
     * Attach View
     *
     * @param viewInterface [ViewInterface]
     * @param variableId    variableId in the xml
     */
    open fun attach(viewInterface: ViewInterface<*>, variableId: Int) {
        this.variableId = variableId
        this.viewIF = viewInterface as V
        onAttach()
        onViewAttached(viewInterface.binding.root)
    }

    /**
     * set binding data on attach
     */
    open fun onAttach() {
        viewIF.binding.setVariable(variableId, this)
        //must,Otherwise, a data refresh exception will occur
        viewIF.binding.executePendingBindings()
        onViewAttachListener?.onViewAttached(this)
    }

    /**
     * Init data on ViewAttached
     */
    abstract fun onViewAttached(view: View)

    /**
     * Interface definition for a callback to be invoked when a viewInterfaceModel attach to viewInterface.
     */
    interface OnViewAttachListener<T : ViewModel<*>> {
        fun onViewAttached(viewModel: T)
    }


    /**
     * 绑定ViewModel生命周期到父类
     * @param parent ViewModel<*>
     */
    fun bindLifecycle(parent: ViewModel<*>) {
        bindLifecycle(parent.lifecycleOwner)
    }


    /**
     * Lifecycle
     */
    override fun onPause() {}
    override fun onResume() {}
    override fun onStop() {}
    override fun onStart() {
    }

    override fun onRelease() {
        onViewAttachListener = null
        release()
    }


}
