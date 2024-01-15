package io.ganguo.state.loading

import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 *     author : leo
 *     time   : 2019/08/29
 *     desc   : Loading 工具类
 * </pre>
 */
open class LoadingHelper(var container: ViewGroup?) : ILoadingStatus {

    /**
     * 显示Loading
     */
    override fun showLoading() {
        container?.visibility = View.VISIBLE
    }

    /**
     * 隐藏Loading
     */
    override fun hideLoading() {
        container?.visibility = View.GONE
    }
}