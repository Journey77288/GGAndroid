package io.ganguo.sample.http.api.impl

import io.ganguo.http.use.service.ApiServiceImpl
import io.ganguo.http.use.service.ApiServiceImplCompanion
import io.ganguo.sample.http.api.service.SettingService

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/13
 *   @desc   : 设置模块接口实现
 * </pre>
 */
class SettingServiceImpl : ApiServiceImpl<SettingService>() {
    override fun apiServiceClass(): Class<SettingService> {
        return SettingService::class.java
    }

    companion object : ApiServiceImplCompanion<SettingService, SettingServiceImpl>() {
        override fun createApiServiceImpl(): SettingServiceImpl {
            return SettingServiceImpl()
        }
    }
}
