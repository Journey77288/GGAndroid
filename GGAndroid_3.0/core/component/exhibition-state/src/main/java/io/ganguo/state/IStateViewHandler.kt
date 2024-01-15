package io.ganguo.state

import io.ganguo.state.core.StateViewHelper

/**
 * <pre>
 *     @author : leo
 *     time   : 2019/12/04
 *     desc   : StateView Handler
 * </pre>
 */
interface IStateViewHandler : IStateView {
    val stateHelper: StateViewHelper


    override fun showErrorView() {
        stateHelper.showErrorView()
    }

    override fun showNetWorkErrorView() {
        stateHelper.showNetWorkErrorView()
    }

    override fun showLoadingView() {
        stateHelper.showLoadingView()
    }

    override fun showStateLayout() {
        stateHelper.showStateLayout()
    }

    override fun hideStateLayout() {
        stateHelper.hideStateLayout()
    }

    override fun showEmptyView() {
        stateHelper.showEmptyView()
    }
}