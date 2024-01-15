package io.ganguo.mvvm.viewinterface

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import io.ganguo.rxresult.GGActivityResultLauncher

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Activity Interface
 * </pre>
 */
interface ActivityInterface<T : ViewDataBinding> : ViewInterface<T> {
    val activity: FragmentActivity
    val resultLauncher: GGActivityResultLauncher<Intent, ActivityResult>
    val bundle: Bundle?
}