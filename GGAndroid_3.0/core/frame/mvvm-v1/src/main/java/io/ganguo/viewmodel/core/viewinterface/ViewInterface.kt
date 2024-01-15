package io.ganguo.viewmodel.core.viewinterface

import android.content.Context
import androidx.databinding.ViewDataBinding


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : View Interface
 * </pre>
 */
interface ViewInterface<T : ViewDataBinding> {
    val context: Context
   val binding: T
}