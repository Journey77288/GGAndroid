package io.ganguo.http2.config

import io.ganguo.http2.config.domain.AbstractHttpMultiUrlSettingAdapter

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : 具备基础能力封装的[HttpGlobalConfig]
 * </pre>
 */
open class BaseHttpGlobalConfig : HttpGlobalConfig {
    private var baseUrl: String = ""
    private var globalHeaders: MutableMap<String, Any> = LinkedHashMap<String, Any>()
    private var globalParameters: MutableMap<String, Any> = LinkedHashMap<String, Any>()
    private var isSupportDynamicDomain: Boolean = false
    private var domainAdapter: AbstractHttpMultiUrlSettingAdapter? = null
    override fun getMainBaseUrl(): String {
        return baseUrl
    }

    override fun getGlobalHeaders(): Map<String, Any> {
        return globalHeaders
    }

    override fun getGlobalParameters(): Map<String, Any> {
        return globalParameters
    }

    override fun isSupportMultiBaseUrl(): Boolean {
        return isSupportDynamicDomain
    }

    override fun getMultiBaseUrlAdapter(): AbstractHttpMultiUrlSettingAdapter? {
        return domainAdapter
    }


    /**
     * 配置默认baseUrl
     * @param baseUrl String
     */
    override fun applyMainBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }


    /**
     * 添加全局参数
     * @param parameterName String
     * @param parameterValue Any
     */
    override fun applyGlobalParameter(parameterName: String, parameterValue: Any) {
        this.globalParameters[parameterName] = parameterValue
    }

    /**
     * 一次添加多个全局参数
     * @param parameterMap Map<String, Any>?
     */
    override fun applyGlobalParameters(parameterMap: Map<String, Any>?) {
        parameterMap?.let {
            this.globalParameters.putAll(parameterMap)
        }
    }

    /**
     * 添加全局Header
     * @param headerName String
     * @param headerValue String
     */
    override fun applyGlobalHeader(headerName: String, headerValue: Any) {
        globalHeaders[headerName] = headerValue
    }


    /**
     * 添加多个全局Header
     * @param map Map<String, String>
     */
    override fun applyGlobalHeaders(map: Map<String, Any>?) {
        map?.let {
            globalHeaders.putAll(map)
        }
    }


    /**
     * 配置是否，支持动态多域名
     * 动态设置`BaseUrl`的方案为：
     * @see    1、先在网络请求中，去拦截Header，取Header里面[AbstractHttpMultiUrlSettingAdapter.getAdapterHeaderKey]对应的`value`
     *         2、然后再通过`Value`去匹配、获取具体的动态域名
     * @param isSupportDynamicDomain Boolean，是否支持动态多域名
     * @param domainAdapter DomainAdapter 动态域名适配器
     */
    override fun applyMultiBaseUrlAdapter(isSupportDynamicDomain: Boolean, domainAdapter: AbstractHttpMultiUrlSettingAdapter) {
        this.isSupportDynamicDomain = isSupportDynamicDomain
        this.domainAdapter = domainAdapter
    }


}