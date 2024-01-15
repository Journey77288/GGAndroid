package io.ganguo.state.core

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/04
 *     desc   : State View Creator
 * </pre>
 */
interface StateViewService {
    /**
     * 创建StateKey对应状态View
     * @param context StateView父容器
     * @return View
     */
    fun createStateView(context: Context): View?


    /**
     * StateView状态发生改变回调
     * @param isVisible Boolean
     */
    fun onStateViewVisibleChanged(isVisible: Boolean) {

    }
}
