package io.ganguo.viewmodel.core.viewinterface

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Activity Interface
 * </pre>
 */
interface ActivityInterface<T : ViewDataBinding> : ViewInterface<T> {
    val activity: FragmentActivity
    val bundle: Bundle?
}