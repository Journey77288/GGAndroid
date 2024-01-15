package io.ganguo.http2.config.domain

import java.net.URI

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/30
 *     desc   : 域名适配器
 * </pre>
 */
abstract class AbstractHttpMultiUrlSettingAdapter : HttpMultiBaseUrlAdapter {
    private val urls: LinkedHashMap<String, String> by lazy {
        LinkedHashMap<String, String>()
    }
    private val urlParameters: LinkedHashMap<String, LinkedHashMap<String, Any>> by lazy {
        LinkedHashMap<String, LinkedHashMap<String, Any>>()
    }

    private val urlHeaders: LinkedHashMap<String, LinkedHashMap<String, Any>> by lazy {
        LinkedHashMap<String, LinkedHashMap<String, Any>>()
    }

    init {
        initMultiUrl()
        initMultiUrlGlobalParameters()
    }


    /**
     * 初始化动态域名配置
     * @return HashMap<String, String>
     */
    abstract fun initMultiUrl()

    /**
     * 初始化域名对应的全局参数
     */
    abstract fun initMultiUrlGlobalParameters()

    /**
     * 初始化域名对应的全局Header参数
     */
    abstract fun initMultiUrlGlobalHeaders()


    /**
     * 添加域名对应的全局参数
     * @param url String
     * @param parameterName String
     * @param parameterValue String
     */
    fun addGlobalParameter(url: String, parameterName: String, parameterValue: String) {
        getHost(url)
                ?.let {
                    getGlobalParameters(it)[parameterName] = parameterValue
                }
    }

    /**
     * 添加域名对应的多个全局参数
     * @param url String
     * @param parameters Map<String, Any>
     */
    fun addGlobalParameter(url: String, parameters: HashMap<String, Any>) {
        getHost(url)
                ?.let {
                    getGlobalParameters(it).putAll(parameters)
                }
    }


    /**
     * 添加域名对应的全局Header
     * @param url String
     * @param parameterName String
     * @param parameterValue String
     */
    fun addGlobalHeader(url: String, parameterName: String, parameterValue: String) {
        getHost(url)
                ?.let {
                    getGlobalHeaders(it)[parameterName] = parameterValue
                }
    }

    /**
     * 添加域名对应的多个全局Header
     * @param url String
     * @param parameters Map<String, Any>
     */
    fun addMultiUrlGlobalHeaders(url: String, parameters: HashMap<String, Any>) {
        getHost(url)
                ?.let {
                    getGlobalHeaders(it).putAll(parameters)
                }
    }


    private fun getHost(url: String): String? {
        return URI.create(url).host
    }

    /**
     * 获取url对应的全局参数
     * @param host String
     * @return Map<String, Any>
     */
    fun getGlobalParameters(host: String): LinkedHashMap<String, Any> {
        var parameters: LinkedHashMap<String, Any>? = urlParameters[host]
        if (parameters == null) {
            parameters = LinkedHashMap<String, Any>()
            urlParameters[host] = parameters
        }
        return parameters
    }


    /**
     * 获取url对应的全局参数
     * @param host String
     * @return Map<String, Any>
     */
    fun getGlobalHeaders(host: String): LinkedHashMap<String, Any> {
        var parameters: LinkedHashMap<String, Any>? = urlHeaders[host]
        if (parameters == null) {
            parameters = LinkedHashMap<String, Any>()
            urlHeaders[host] = parameters
        }
        return parameters
    }


    /**
     * 添加支持动态替换的BaseUrl.例如：adapterHeaderValue=xiaomi，baseUrlValue=http://www.xiaomi.com
     * @param adapterHeaderValue String 名称
     * @param baseUrlValue String 具体的`域名`
     */
    fun addUrl(adapterHeaderValue: String, baseUrlValue: String) {
        urls[adapterHeaderValue] = baseUrlValue
    }


    /**
     * 批量添加支持动态替换的BaseUrl，[Key==名称]，[value==域名]，例如：Key=小米,value=http://www.xiaomi.com
     * @param map Map<String, String>?
     */
    fun addUrls(map: Map<String, String>?) {
        map?.let {
            urls.toMutableMap().putAll(it)
        }
    }

    /**
     * 取出BaseUrl
     * 1、通过[getAdapterHeaderKey]拿到[adapterHeaderValue]
     * 2、通过[adapterHeaderValue]去取出对应的BaseUrl
     * @param adapterHeaderValue String
     * @return String
     */
    override fun getUrl(adapterHeaderValue: String): String {
        return urls[adapterHeaderValue].orEmpty()
    }
}