package io.ganguo.demo.http.api.impl

import io.ganguo.demo.http.api.service.QiNiuApiService
import io.ganguo.http.use.service.ApiServiceImpl
import io.ganguo.http2.core.use.service.ApiServiceImplCompanion

/**
 * 七牛网络请求实现
 * Created by Roger on 7/28/16.
 */
class QiNiuApiServiceImpl private constructor() : ApiServiceImpl<QiNiuApiService>() {

    companion object : ApiServiceImplCompanion<QiNiuApiService, QiNiuApiServiceImpl>() {
        override fun createApiServiceImpl(): QiNiuApiServiceImpl {
            return QiNiuApiServiceImpl()
        }
    }

    /**
     *  api service class
     * @return DouBanApiService
     */
    override fun apiServiceClass(): Class<QiNiuApiService> {
        return QiNiuApiService::class.java
    }

}
