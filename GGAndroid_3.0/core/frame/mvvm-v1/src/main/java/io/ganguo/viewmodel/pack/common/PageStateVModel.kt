package io.ganguo.viewmodel.pack.common

import android.view.View
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.R
import io.ganguo.viewmodel.databinding.IncludePageStateBinding
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/04
 *     desc   : 页面状态ViewModel
 * </pre>
 */
class PageStateVModel(var stateHint: String) : BaseViewModel<ViewInterface<IncludePageStateBinding>>() {
    override val layoutId: Int
        get() = R.layout.include_page_state

    override fun onViewAttached(view: View) {
    }

}