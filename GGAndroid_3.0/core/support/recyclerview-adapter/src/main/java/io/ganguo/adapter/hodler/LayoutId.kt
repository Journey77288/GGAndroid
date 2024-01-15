package io.ganguo.adapter.hodler

import androidx.annotation.LayoutRes

/**
 * <pre>
 * author : leo
 * time   : 2020/05/23
 * desc   :  layoutId
 * </pre>
 *
 * @property layoutId Int
 */
interface LayoutId {
    @get:LayoutRes
    val layoutId: Int
}