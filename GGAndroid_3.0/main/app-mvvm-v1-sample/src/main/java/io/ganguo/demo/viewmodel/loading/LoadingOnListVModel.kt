package io.ganguo.demo.viewmodel.loading

import android.view.View
import io.ganguo.demo.R
import io.ganguo.demo.databinding.LoadingOneListBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface

/**
 * <pre>
 * author : leo
 * time   : 2018/11/20
 * desc   : Http Demo页面Loading
</pre> *
 */
class LoadingOnListVModel : BaseViewModel<ViewInterface<LoadingOneListBinding>>() {
    override fun onViewAttached(view: View) {
        viewInterface.binding.shimmer.startShimmerAnimation()
    }

    override val layoutId: Int by lazy { R.layout.loading_one_list }
}
