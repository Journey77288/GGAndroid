package io.ganguo.core.viewmodel.common.component

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import io.ganguo.core.R
import io.ganguo.core.databinding.ComponentLoadMoreLoadingBinding
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/15
 *   @desc   : LoadMore Loading ViewModel
 * </pre>
 */
class LoadMoreLoadingVModel(@ColorInt var loadingColor: Int = Color.GREEN) : ViewModel<ViewInterface<ComponentLoadMoreLoadingBinding>>(), RefreshFooter {
    override val layoutId: Int by lazy {
        R.layout.component_load_more_loading
    }

    override fun onViewAttached(view: View) {
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        return 500
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
        viewIF.binding.mpBar.isIndeterminate = false
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        return false
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun getView(): View {
        return viewIF.binding.root
    }

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        viewIF.binding.mpBar.isIndeterminate = !(newState == RefreshState.LoadFinish || newState == RefreshState.None)
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }
}
