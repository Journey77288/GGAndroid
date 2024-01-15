package io.ganguo.http2.config.domain


/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/30
 *     desc   : 网络请求BaseUrl适配器，用于支持多BaseUrl
 * </pre>
 */
interface HttpMultiBaseUrlAdapter {
    /**
     * 取出BaseUrl
     * 1、通过[getAdapterHeaderKey]拿到[adapterHeaderValue]
     * 2、通过[adapterHeaderValue]去取出对应的BaseUrl
     * @param adapterHeaderValue String
     * @return String
     */
    fun getUrl(adapterHeaderValue: String): String

    /**
     * 多BaseUrl适配Key
     * @return String
     */
    fun getAdapterHeaderKey(): String

}