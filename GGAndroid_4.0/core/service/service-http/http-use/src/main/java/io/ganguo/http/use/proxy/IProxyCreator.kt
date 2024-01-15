package io.ganguo.http.use.proxy

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/24
 *     desc   : 代理对象创建器
 * </pre>
 */
interface IProxyCreator<T:Any> {
    var target: T

    /**
     * 创建代理服务对象
     * @return T
     */
    fun createProxy(): T?

}