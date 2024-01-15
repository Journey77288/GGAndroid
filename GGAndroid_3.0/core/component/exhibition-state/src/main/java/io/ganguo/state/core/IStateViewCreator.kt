package io.ganguo.state.core

import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/04
 *     desc   : State View Creator
 * </pre>
 */
interface IStateViewCreator {
    /**
     * 创建StateKey对应状态View
     * @param stateLayout StateView父容器
     * @return View
     */
    fun attachStateView(stateLayout: ViewGroup): View?
}