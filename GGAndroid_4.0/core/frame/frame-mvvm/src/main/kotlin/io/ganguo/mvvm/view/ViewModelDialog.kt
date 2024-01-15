package io.ganguo.mvvm.view

import android.app.Dialog
import android.content.Context
import androidx.core.app.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.app.ui.dialog.BaseDialog
import io.ganguo.lifecycle.LifecycleBindingAdapter
import io.ganguo.mvvm.creator.ViewModelCreator
import io.ganguo.mvvm.viewinterface.DialogInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModel.OnViewAttachListener
import io.ganguo.mvvm.viewmodel.ViewModelHelper

/**
 * <pre>
 *     @author : leo
 *     time   : 2020/05/29
 *     desc   : Base ViewModelDialog
 * </pre>
 * @param T : ViewDataBinding
 * @param B : ViewModel<*>
 * @property viewModel B
 * @constructor
 */
@Suppress("UNCHECKED_CAST")
abstract class ViewModelDialog<T : ViewDataBinding, B : ViewModel<*>>(context: Context, theme: Int = io.ganguo.resources.R.style.Dialog_Immersion) : BaseDialog(context, theme),
        DialogInterface<T>,
        OnViewAttachListener<B>,
        ViewModelCreator<B>,
        LifecycleBindingAdapter {
    private var lifecycleOwner: LifecycleOwner? = null
    override val viewContext: Context by lazy { getContext() }
    override val viewModel: B by lazy { createViewModel() }
    override val dialog: Dialog by lazy { this }
    override val binding: T by lazy {
        ViewModelHelper.inflate(context, viewModel.layoutId)
    }

    override fun beforeInitView() {
        initLifecycle()
        ViewModelHelper.bind(this, viewModel)
        setContentView(binding.root)
    }

    private fun initLifecycle() {
        val currentActivity = ActivityHelper.currentActivity()
        if (currentActivity != null && currentActivity is ComponentActivity) {
            bindLifecycle(currentActivity)
        }
    }

    override fun initListener() {
        viewModel.onViewAttachListener = this as OnViewAttachListener<ViewModel<*>>
    }


    /**
     * Create ViewModel here
     * if you want to getViewModel instance use [.getViewModel] instead this
     */
    abstract override fun createViewModel(): B

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
