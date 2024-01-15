package io.support.recyclerview.manager

import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/06
 *   @desc   : LayoutManager create delegate
 * </pre>
 */
interface LayoutManagerDelegate {

    /**
     * create RecyclerView.LayoutManager
     * @return RecyclerView.LayoutManager
     */
    fun create(): RecyclerView.LayoutManager
}
