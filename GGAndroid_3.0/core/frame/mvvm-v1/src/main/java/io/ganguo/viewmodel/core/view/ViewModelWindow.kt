package io.ganguo.viewmodel.core.view

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.ganguo.core.R
import io.ganguo.core.ui.popupwindow.BasePopupWindow
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.BaseViewModel.OnViewAttachListener
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.creator.ViewModelCreator
import io.ganguo.viewmodel.core.viewinterface.ViewInterface


/**
 * <pre>
 *     @author : leo
 *     time   : 2020/05/29
 *     desc   : Base ViewModelWindow
 * </pre>
 * @param T : ViewDataBinding
 * @param B : BaseViewModel<*>
 * @property isTouchOutsideDismiss Boolean
 * @property windowHeight Int
 * @property windowWidth Int
 * @property context Context
 * @property binding T
 * @property viewModel B
 * @constructor
 */
abstract class ViewModelWindow<T : ViewDataBinding, B : BaseViewModel<*>>(context: Context)
    : BasePopupWindow(context), OnViewAttachListener<B>, ViewInterface<T>, ViewModelCreator<B> {
    open var isTouchOutsideDismiss: Boolean = true
    open var windowHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    open var windowWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    override val context: Context = context
    override val binding: T by lazy {
        viewModel.viewInterface.binding as T
    }
    override val viewModel: B by lazy {
        createViewModel()
    }

    override fun beforeInitView() {
        ViewModelHelper.bind(this, viewModel)
        contentView = viewModel.rootView
    }

    override fun initView() {
        width = windowWidth
        height = windowHeight
        animationStyle = R.style.PopupWindow_Animation
        initTouchEvent()
    }

    override fun initListener() {
        viewModel.onViewAttachListener = this as OnViewAttachListener<BaseViewModel<*>>
    }

    protected open fun initTouchEvent() {
        if (isTouchOutsideDismiss) {
            setBackgroundDrawable(AnimationDrawable())
            isOutsideTouchable = true
            isFocusable = true
        }
    }


    override fun dismiss() {
        super.dismiss()
        if (viewModel.isAttach) {
            viewModel.lifecycleHelper.onLifecycleDestroy()
        }
    }
}