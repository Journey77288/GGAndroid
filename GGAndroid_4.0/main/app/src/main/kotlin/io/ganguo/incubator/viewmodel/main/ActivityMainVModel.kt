package io.ganguo.incubator.viewmodel.main

import android.view.View
import io.ganguo.incubator.R
import io.ganguo.incubator.databinding.ActivityMainBinding
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : MainActivity ViewModel
 * </pre>
 */
class ActivityMainVModel : ViewModel<ActivityInterface<ActivityMainBinding>>() {
    override val layoutId: Int by lazy { R.layout.activity_main }
    override fun onViewAttached(view: View) {
    }
}