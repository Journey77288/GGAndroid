package io.ganguo.mvvm.viewinterface

import android.widget.PopupWindow
import androidx.databinding.ViewDataBinding

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : PopupWindow Interface
 * </pre>
 */
interface WindowInterface<T : ViewDataBinding> : ViewInterface<T> {
    val window: PopupWindow
}