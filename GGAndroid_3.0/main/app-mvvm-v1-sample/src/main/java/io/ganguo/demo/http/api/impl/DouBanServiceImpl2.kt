package io.ganguo.demo.http.api.impl

import io.ganguo.demo.http.api.proxy.DouBanServiceStaticProxyCreator
import io.ganguo.demo.http.api.service.DouBanApiService
import io.ganguo.http.retrofit.RetrofitService
import io.ganguo.http.use.service.ApiServiceImpl
import io.ganguo.http.use.proxy.IProxyCreator
import io.ganguo.http2.core.use.service.ApiServiceImplCompanion

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/26
 *     desc   : 豆瓣API接口请求
 * </pre>
 */
class DouBanServiceImpl2 private constructor() : ApiServiceImpl<DouBanApiService>() {

    /**
     * 具体服务类
     * @return Class<DouBanApiService>
     */
    override fun apiServiceClass(): Class<DouBanApiService> {
        return DouBanApiService::class.java
    }

    /**
     * 伴生对象
     */
    companion object : ApiServiceImplCompanion<DouBanApiService, DouBanServiceImpl2>() {
        /**
         * 创建接口服务实现类
         * @return DouBanServiceImpl2
         */
        override fun createApiServiceImpl(): DouBanServiceImpl2 {
            return DouBanServiceImpl2()
        }
    }


    /**
     * 重写该函数，配置代理模式为静态代理
     * `一般情况无须重写，在网络请求前，需要特殊处理，才重写`
     * Proxy Creator instance
     * @return IProxyCreator<DouBanApiService>?
     */
    override fun createProxyCreator(apiService: DouBanApiService): IProxyCreator<DouBanApiService> {
        return DouBanServiceStaticProxyCreator(apiService)
    }

}