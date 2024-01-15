package io.ganguo.mvvm.viewinterface

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
    val viewContext: Context
    val binding: T
}