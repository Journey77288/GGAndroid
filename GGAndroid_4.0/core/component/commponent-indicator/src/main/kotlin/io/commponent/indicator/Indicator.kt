package io.commponent.indicator

import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/23
 *   @desc   : Indicator interface
 * </pre>
 */
interface Indicator {
    var widthPx: Int
    var heightPx: Int
    fun createHolderView(parent: ViewGroup, position: Int): View
    fun createView(parent: ViewGroup, position: Int): View
    fun getView(position: Int): View?
    fun updateSelectedViewState(position: Int)
    fun resetAllViewState()
    fun getCount(): Int
}
