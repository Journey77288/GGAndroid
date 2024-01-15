package io.ganguo.mvvm.viewinterface

import android.app.Dialog
import androidx.databinding.ViewDataBinding

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Dialog Interface
 * </pre>
 */
interface DialogInterface<T : ViewDataBinding> : ViewInterface<T> {
    val dialog: Dialog
}