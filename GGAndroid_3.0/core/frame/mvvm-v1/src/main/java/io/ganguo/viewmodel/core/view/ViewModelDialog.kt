package io.ganguo.viewmodel.core.view

import android.content.Context
import androidx.databinding.ViewDataBinding
import io.ganguo.core.R
import io.ganguo.core.ui.dialog.BaseDialog
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.BaseViewModel.OnViewAttachListener
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.creator.ViewModelCreator

/**
 * <pre>
 *     @author : leo
 *     time   : 2020/05/29
 *     desc   : Base ViewModelDialog
 * </pre>
 * @param T : ViewDataBinding
 * @param B : BaseViewModel<*>
 * @property viewModel B
 * @constructor
 */
abstract class ViewModelDialog<T : ViewDataBinding, B : BaseViewModel<*>>(context: Context, theme: Int=R.style.Dialog_Immersion)
    : BaseDialog(context, theme), OnViewAttachListener<B>, ViewModelCreator<B> {
    override val viewModel: B by lazy { createViewModel() }

    override fun beforeInitView() {
        ViewModelHelper.bind(this, viewModel)
        setContentView(viewModel.rootView)
    }

    override fun initView() {}
    override fun initListener() {
        viewModel.onViewAttachListener = this as OnViewAttachListener<BaseViewModel<*>>
    }

    override fun initData() {}


    /**
     * Bind ViewModel Lifecycle
     */
    override fun onStop() {
        super.onStop()
        viewModel.lifecycleHelper.onLifecycleStop()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewModel.lifecycleHelper.onLifecycleDestroy()
    }

    /**
     * Create ViewModel here
     * if you want to getViewModel instance use [.getViewModel] instead this
     */
    abstract override fun createViewModel(): B
}