package io.ganguo.demo.viewmodel.loading

import android.view.View
import com.wang.avi.AVLoadingIndicatorView
import io.ganguo.demo.R
import io.ganguo.demo.databinding.LoadingCenterBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface

/**
 * 页面中心Loading
 * Created by leo on 2018/9/18.
 */
class LoadingCenterVModel : BaseViewModel<ViewInterface<LoadingCenterBinding>>() {

    private val loadingView: AVLoadingIndicatorView
        get() = viewInterface.binding.avi

    override fun onViewAttached(view: View) {
        loadingView.smoothToShow()
    }


    override val layoutId: Int by lazy {
        R.layout.loading_center
    }
}
