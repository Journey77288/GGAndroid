package io.ganguo.viewmodel.core

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BaseObservable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.ganguo.adapter.hodler.LayoutId
import io.ganguo.lifecycle.LifecycleListener
import io.ganguo.lifecycle.helper.LifecycleHelper
import io.ganguo.rx.helper.DisposableList
import io.ganguo.rx.helper.DisposableHelper.Companion.create
import io.ganguo.utils.helper.ResHelper
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 *
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Base ViewModel class
 * </pre>
 *
 * @param V : ViewInterface<*>
 * @property lifecycleHelper LifecycleHelper
 * @property rxDisposable IRxDisposableHelper
 * @property viewInterface V
 * @property onViewAttachListener OnViewAttachListener<BaseViewModel<*>>?
 * @property variableId Int
 * @property rootView View
 * @property context Context
 * @property resources Resources
 * @property isAttach Boolean
 * @property compositeDisposable CompositeDisposable
 * @property lifecycleComposite CompositeDisposable
 */
@Suppress("LeakingThis")
abstract class BaseViewModel<V : ViewInterface<*>> : BaseObservable(), LayoutId, LifecycleOwner, LifecycleListener {
    /**
     * 用于绑定Observable生命周期事件
     *
     * @see {@link io.ganguo.viewmodel.pack.RxVMLifecycle.bindSingleViewModel
     */
    val lifecycleHelper: LifecycleHelper = LifecycleHelper(this)

    /**
     * 用于批量存储Disposable，并在onDestroy中取消订阅Disposable
     */
    private val rxDisposable: DisposableList = create()
    lateinit var viewInterface: V
    var onViewAttachListener: OnViewAttachListener<BaseViewModel<*>>? = null

    /**
     * Set VariableId before attach
     */
    var variableId = 0


    /**
     * Attach View
     *
     * @param viewInterface [ViewInterface]
     * @param variableId    variableId in the xml
     */
    open fun attach(viewInterface: ViewInterface<*>, variableId: Int) {
        this.viewInterface = viewInterface as V
        this.variableId = variableId
        onAttach()
        onViewAttached(viewInterface.binding.root)
    }

    /**
     * set binding data on attach
     */
    open fun onAttach() {
        viewInterface.binding.setVariable(variableId, this)
        onViewAttachListener?.onViewAttached(this)
    }

    val rootView: View by lazy {
        check(::viewInterface.isInitialized) {
            "ViewModel is Not onAttach"
        }
        viewInterface.binding.root
    }

    open val context: Context by lazy {
        viewInterface.context
    }

    /**
     * @return ResourcesDelegate to access Applications resources, return null when ViewModel is not attached
     */
    val resources: Resources
        get() = ResHelper.getResources()

    /**
     * check if ViewModel attach to viewInterface
     */
    val isAttach: Boolean
        get() = ::viewInterface.isInitialized

    /**
     * LifecycleAction
     */
    override fun onPause() {}
    override fun onResume() {}
    override fun onStop() {}
    override fun onStart() {
    }

    override fun onDestroy() {
        onViewAttachListener = null
        rxDisposable.release()
    }


    /**
     * Init data on ViewAttached
     */
    abstract fun onViewAttached(view: View)

    /**
     * Interface definition for a callback to be invoked when a viewInterfaceModel attach to viewInterface.
     */
    interface OnViewAttachListener<T : BaseViewModel<*>> {
        fun onViewAttached(viewModel: T)
    }

    /**
     * 获取字符串数据
     *
     * @param resId 对应id
     * @return
     */
    fun getString(@StringRes resId: Int): String {
        return ResHelper.getString(resId)
    }

    /**
     * 获取字符串数据
     *
     * @param resId 对应id
     * @return
     */
    fun getStringFormatArgs(@StringRes resId: Int, vararg data: Any?): String {
        return ResHelper.getString(resId, *data)
    }

    /**
     * 获取颜色资源
     *
     * @param resId 对应id
     * @return
     */
    fun getColor(@ColorRes resId: Int): Int {
        return resources.getColor(resId)
    }

    /**
     * 获取Drawable
     *
     * @param resId 对应id
     * @return
     */
    fun getDrawable(@DrawableRes resId: Int): Drawable {
        return resources.getDrawable(resId)
    }

    /**
     * 获取Dimension
     *
     * @param resId 对应id
     * @return
     */
    fun getDimensionPixelOffset(@DimenRes resId: Int): Int {
        return resources.getDimensionPixelOffset(resId)
    }

    /**
     * 获取Dimension
     *
     * @param resId 对应id
     * @return
     */
    fun getDimensionPixelSize(@DimenRes resId: Int): Int {
        return resources.getDimensionPixelSize(resId)
    }

    /**
     * get CompositeDisposable
     *
     * @return
     */
    @get:Deprecated("Use {@link #getLifecycleComposite()} instead.", ReplaceWith("lifecycleComposite"))
    val compositeDisposable: CompositeDisposable
        get() = lifecycleComposite

    /**
     * get CompositeDisposable
     *
     * @return
     */
    val lifecycleComposite: CompositeDisposable
        get() = rxDisposable.lifecycleComposite

    /**
     * 添加Disposable到RxDisposableHelper，统一管理生命周期，避免内存泄露
     *
     * @param disposable
     */
    protected fun addDisposable(disposable: Disposable?) {
        rxDisposable.add(disposable)
    }

    /**
     * 移除Disposable，释放内存
     *
     * @param disposable
     */
    protected fun removeDisposable(disposable: Disposable?) {
        rxDisposable.remove(disposable)
    }

    /**
     * 生命周期感知组件
     *
     * @return
     */
    override fun getLifecycle(): Lifecycle {
        return lifecycleHelper.lifecycle
    }

    /**
     * 绑定ViewModel生命周期到父类
     * @param parent BaseViewModel<*>
     */
    fun bindLifecycle(parent: BaseViewModel<*>) {
        parent.lifecycleHelper.bindLifecycle(lifecycleHelper)
    }

}