@file:Suppress("UNCHECKED_CAST")

package io.ganguo.http2.config

import io.ganguo.http2.config.domain.AbstractHttpMultiUrlSettingAdapter

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : Http Common Parameter Config
 * </pre>
 */
interface HttpGlobalConfig {


    /**
     * 获取Http主域名（默认域名）
     * @return String
     */
    fun getMainBaseUrl(): String

    /**
     * 获取全局Header配置
     * @return Map<String, Any>
     */
    fun getGlobalHeaders(): Map<String, Any>


    /**
     * 获取全局参数配置
     * @return HashMap<String,Any>
     */
    fun getGlobalParameters(): Map<String, Any>

    /**
     * 是否支持多BaseUrl
     * @return Boolean
     */
    fun isSupportMultiBaseUrl(): Boolean

    /**
     * 获取BaseUrl适配器
     * @return AbstractBaseUrlAdapter?
     */
    fun getMultiBaseUrlAdapter(): AbstractHttpMultiUrlSettingAdapter?

    /**
     * 设置Http请求主域名（默认域名）
     * @param baseUrl String
     */
    fun applyMainBaseUrl(baseUrl: String)

    /**
     * 添加全局参数
     * @param parameterName String
     * @param parameterValue String
     */
    fun applyGlobalParameter(parameterName: String, parameterValue: Any)

    /**
     * 添加多个全局参数
     * @param parameterMap Map<String, Any>?
     */
    fun applyGlobalParameters(parameterMap: Map<String, Any>?)


    /**
     * 添加全局Header
     * @param headerName String
     * @param headerValue String
     */
    fun applyGlobalHeader(headerName: String, headerValue: Any)

    /**
     * 添加多个全局Header
     * @param map Map<String, String>?
     */
    fun applyGlobalHeaders(map: Map<String, Any>?)

    /**
     * 设置多BaseUrl适配器
     * @param isSupportDynamicDomain Boolean
     * @param domainAdapter AbstractBaseUrlAdapter
     */
    fun applyMultiBaseUrlAdapter(isSupportDynamicDomain: Boolean, domainAdapter: AbstractHttpMultiUrlSettingAdapter)


}