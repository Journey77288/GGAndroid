package io.ganguo.viewmodel.pack.ui.dialog

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.view.ViewModelDialog

/**
 *
 *
 * 全屏弹窗 - dialog
 *
 * Created by leo on 2018/9/30.
 */
abstract class FullScreenVModelDialog<T : ViewDataBinding, B :  BaseViewModel<*>>(context: Context, theme: Int) : ViewModelDialog<T, B>(context, theme) {
    override val dialogWidth: Int
        protected get() = ViewGroup.LayoutParams.MATCH_PARENT

    override val dialogHeight: Int
        protected get() = ViewGroup.LayoutParams.MATCH_PARENT
}