package io.ganguo.http.use.service

import io.ganguo.http.use.HttpUse
import io.ganguo.http.use.proxy.IProxyCreator
import io.ganguo.http.use.proxy.ProxyDynamicCreator

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/26
 *     desc   : API接口服务实现 - 顶层基类
 * </pre>
 */
abstract class ApiServiceImpl<S : Any> : AbstractApiServiceImpl<S>() {
    protected open var apiService: S? = null
        get() = if (field == null) {
            createApiService()
        } else {
            field
        }
    override var proxyCreator: IProxyCreator<S>? = null
        get() = if (field == null) {
            createProxyCreator()
        } else {
            field
        }
    override var apiProxy: S? = null
        get() = if (field == null) {
            createApiProxy()
        } else {
            field
        }

    /**
     * 创建API服务代理对象
     * @return S
     */
    private fun createApiProxy(): S? {
        return proxyCreator?.createProxy()
    }

    /**
     * ApiServiceImpl 代理对象创建接口示例
     * 默认为：动态代理方式实现。
     *        如需在Api函数调用前后，做一些其他操作，可以通过实现[IProxyCreator<S>]接口，使用静态代理，实现对应操作。
     * @return IProxyCreator<S>
     * @param apiService 接口请求服务对象
     */
    protected open fun createProxyCreator(apiService: S): IProxyCreator<S> {
        return ProxyDynamicCreator(apiService)
    }

    /**
     * ApiServiceImpl 代理对象创建接口示例
     * 默认为：动态代理方式实现。
     *        如需在Api函数调用前后，做一些其他操作，可以通过实现[IProxyCreator<S>]接口，使用静态代理，实现对应操作。
     * @return IProxyCreator<S>
     */
    protected open fun createProxyCreator(): IProxyCreator<S> {
        return createProxyCreator(apiService!!)
    }

    /**
     * 创建接口服务实例
     * @return DouBanApiService
     */
    private fun createApiService(): S {
        return HttpUse.getConfig().creator.createApiService(apiServiceClass())
    }


    /**
     * API服务类
     * @return Class<S>
     */
    abstract fun apiServiceClass(): Class<S>

}