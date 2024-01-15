package io.ganguo.state.loading


/**
 * <pre>
 *     @author : leo
 *     time   : 2019/09/04
 *     desc   : loading status switch
 * </pre>
 */
interface ILoadingStatus {
    /**
     * 显示Loading
     */
    fun showLoading()

    /**
     * 隐藏Loading
     */
    fun hideLoading()
}