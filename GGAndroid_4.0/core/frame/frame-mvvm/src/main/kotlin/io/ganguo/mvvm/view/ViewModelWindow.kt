package io.ganguo.mvvm.view

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.ViewGroup
import androidx.core.app.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.app.ui.popupwindow.BasePopupWindow
import io.ganguo.lifecycle.LifecycleBindingAdapter
import io.ganguo.mvvm.creator.ViewModelCreator
import io.ganguo.mvvm.viewinterface.WindowInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModel.OnViewAttachListener
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.resources.ResourcesDelegate
import io.ganguo.resources.ResourcesHelper


/**
 * <pre>
 *     @author : leo
 *     time   : 2020/05/29
 *     desc   : Base ViewModelWindow
 * </pre>
 * @param T : ViewDataBinding
 * @param B : ViewModel<*>
 * @property windowHeight Int
 * @property windowWidth Int
 * @property viewContext Context
 * @property binding T
 * @property viewModel B
 * @constructor
 */
@Suppress("UNCHECKED_CAST")
abstract class ViewModelWindow<T : ViewDataBinding, B : ViewModel<*>>(override val viewContext: Context) :
        BasePopupWindow(viewContext), ResourcesDelegate by ResourcesHelper, OnViewAttachListener<B>, WindowInterface<T>, ViewModelCreator<B>, LifecycleBindingAdapter {
    open var windowHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    open var windowWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    private var lifecycleOwner: LifecycleOwner? = null
    override val binding: T by lazy {
        ViewModelHelper.inflate(viewContext, viewModel.layoutId)
    }
    override val viewModel: B by lazy {
        createViewModel()
    }
    override val window: BasePopupWindow by lazy {
        this
    }


    override fun beforeInitView() {
        initLifecycle()
        ViewModelHelper.bind(this, viewModel)
        contentView = viewModel.rootView
    }

    private fun initLifecycle() {
        val currentActivity = ActivityHelper.currentActivity()
        if (currentActivity != null && currentActivity is ComponentActivity) {
            bindLifecycle(currentActivity)
        }
    }

    /**
     * Bind ViewModel onStart Lifecycle
     */
    override fun initView() {
        width = windowWidth
        height = windowHeight
        animationStyle = io.ganguo.resources.R.style.Animation_Window_Fade
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(AnimationDrawable())
    }

    /**
     * Binding ViewModel AttachListener
     */
    override fun initListener() {
        viewModel.onViewAttachListener = this as OnViewAttachListener<ViewModel<*>>
    }

    /**
     * Remove  Lifecycle Observer from LifecycleOwner
     * @param owner
     */
    override fun bindLifecycle(owner: LifecycleOwner) {
        viewModel.bindLifecycle(owner)
    }


    /**
     * Remove  Lifecycle Observer from LifecycleOwner
     * @param owner
     */
    override fun unbindLifecycle(owner: LifecycleOwner) {
        viewModel.unbindLifecycle(owner)
    }


    /**
     * Remove  Lifecycle Observer from LifecycleOwner
     * @param owner
     */
    private fun unbindLifecycle() {
        lifecycleOwner?.let {
            unbindLifecycle(it)
        }
    }

    override fun dismiss() {
        super.dismiss()
        unbindLifecycle()
    }


}
