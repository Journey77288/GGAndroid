package io.ganguo.http.use.service

import io.ganguo.http.use.proxy.IProxyCreator

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/24
 *     desc   : API服务实现 - 顶层接口
 * </pre>
 */
abstract class AbstractApiServiceImpl<S : Any> {
    internal abstract var proxyCreator: IProxyCreator<S>?
    internal abstract var apiProxy: S?
}