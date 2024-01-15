package io.ganguo.lazy

/**
 * <pre>
 *     author : leo
 *     time   : 2019/05/28
 *     desc   : 懒加载数据加载接口
 * </pre>
 */
interface ILazyLoad {

    /**
     * 数据懒加载，只会执行一次
     */
    fun lazyLoadData()

    /**
     * 懒加载前回调，会始终回调
     */
    fun lazyLoadBefore() {

    }

    /**
     * 懒加载后回调，会始终回调
     */
    fun lazyLoadAfter() {

    }
}