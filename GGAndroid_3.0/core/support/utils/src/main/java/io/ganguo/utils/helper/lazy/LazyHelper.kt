package io.ganguo.utils.helper.lazy

/**
 * <pre>
 *     author : leo
 *     time   : 2019/05/16
 *     desc   : 懒加载工具类
 * </pre>
 * @property isLoad 是否执行过懒加载
 * @param load 懒加载接口回调
 */
class LazyHelper(var load: ILazyLoad) {
    var isLoad: Boolean = false

    /**
     * 执行懒加载
     */
    fun lazyLoad() {
        load.lazyLoadBefore()
        if (!isLoad) {
            isLoad = true
            load.lazyLoadData()
        }
        load.lazyLoadAfter()
    }
}