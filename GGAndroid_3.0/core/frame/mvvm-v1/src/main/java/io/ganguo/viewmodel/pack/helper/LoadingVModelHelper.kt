package io.ganguo.viewmodel.pack.helper

import android.view.ViewGroup
import io.ganguo.state.loading.ILoadingStatus
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.state.loading.LoadingHelper
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/12
 *     desc   : ViewModel Loading Heer
 * </pre>
 */
class LoadingVModelHelper private constructor(container: ViewGroup) : LoadingHelper(container) {

    companion object {
        /**
         * 绑定Loading View
         * @param container 父容器
         * @param parent 父ViewModel
         * @param view LoadingView接口
         * @return
         */
        @JvmStatic
        fun bindLoadingView(container: ViewGroup, parent: BaseViewModel<*>, loading: BaseViewModel<*>): LoadingHelper {
            ViewModelHelper.bind(container, parent, loading)
            return LoadingVModelHelper(container)
        }

    }
}