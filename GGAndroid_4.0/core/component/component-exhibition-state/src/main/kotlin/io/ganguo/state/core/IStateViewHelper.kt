package io.ganguo.state.core

import android.view.ViewGroup

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/11/28
 *   @desc   : IStateViewHelper IStateViewHandler and IStateViewGatherCreator interfaces are implemented
 * </pre>
 */
interface IStateViewHelper : IStateViewHandler, IStateViewGatherCreator {

    /**
     * bind state ViewGroup
     * @param stateLayout ViewGroup
     */
    fun bindStateLayout(stateLayout: ViewGroup?)
}
