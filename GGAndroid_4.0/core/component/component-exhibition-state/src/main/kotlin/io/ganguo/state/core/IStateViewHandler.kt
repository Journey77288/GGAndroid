package io.ganguo.state.core

import io.ganguo.state.Constants

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/04
 *     desc   : 状态切换顶层接口
 * </pre>
 * @see showErrorView 显示发生错误提示View
 * @see showLoadingView 显示加载中提示View
 * @see showNetWorkErrorView 显示网络错误提示View
 * @see showStateLayout 显示状态父布局
 * @see hideStateLayout 隐藏状态父布局
 * @see hideLoadingView 隐藏Loading，实际上是调用了[hideStateLayout]，如果是要做状态切换，请勿调用该方法
 * @see showContentView 显示内容
 */
interface IStateViewHandler {

    fun showErrorView() {
        showStateView(Constants.ERROR)
    }

    fun showEmptyView() {
        showStateView(Constants.EMPTY)
    }

    fun showNetWorkErrorView() {
        showStateView(Constants.NETWORK_ERROR)
    }

    fun showLoadingView() {
        showStateView(Constants.LOADING)
    }

    fun showContentView() {
        hideStateLayout()
    }

    fun hideLoadingView() {
        hideStateView(Constants.LOADING)
    }

    fun hideStateView(stateViewKey: String)

    fun showStateView(stateViewKey: String)


    fun hideStateLayout()

    fun showStateLayout()
}
