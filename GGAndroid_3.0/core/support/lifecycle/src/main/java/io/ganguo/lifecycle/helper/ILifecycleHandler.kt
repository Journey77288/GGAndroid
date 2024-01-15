package io.ganguo.lifecycle.helper

/**
 * <pre>
 *     author : leo
 *     time   : 2020/02/12
 *     desc   : 生命周期工具类接口
 * </pre>
 */
interface ILifecycleHandler<T> {

    /**
     * add observer
     * @param observer T
     */
    fun addObserver(observer: T)

    /**
     * remove observer
     * @param observer T
     */
    fun removeObserver(observer: T)
}