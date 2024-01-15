package io.ganguo.viewmodel.core.viewinterface

import androidx.databinding.ViewDataBinding
import io.ganguo.core.ui.popupwindow.BasePopupWindow

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : PopupWindow Interface
 * </pre>
 */
interface WindowInterface<T : ViewDataBinding> : ViewInterface<T> {
    val popupWindow: BasePopupWindow
}