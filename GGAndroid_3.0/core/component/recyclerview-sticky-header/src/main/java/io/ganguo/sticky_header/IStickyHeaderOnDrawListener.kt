package io.ganguo.sticky_header

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/09
 *     desc   : 头部item绘制回调
 * </pre>
 */
interface IStickyHeaderOnDrawListener {
    /**
     * 开始绘制粘性头部
     * @param position Int
     */
    fun onDrawStickyHeader(position: Int, latter: String)

    /**
     * 绘制结束
     * @param endPosition Int
     */
    fun onDrawStickyHeaderEnd(endPosition: Int)
}