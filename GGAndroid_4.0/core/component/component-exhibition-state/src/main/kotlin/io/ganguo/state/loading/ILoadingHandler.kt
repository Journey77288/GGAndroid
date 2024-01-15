package io.ganguo.state.loading

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/04
 *     desc   : Loading Utils
 * </pre>
 */
interface ILoadingHandler : ILoadingStatus {
    val loadingHelper: LoadingHelper

    override fun showLoading() {
        loadingHelper.showLoading()
    }

    override fun hideLoading() {
        loadingHelper.hideLoading()
    }
}