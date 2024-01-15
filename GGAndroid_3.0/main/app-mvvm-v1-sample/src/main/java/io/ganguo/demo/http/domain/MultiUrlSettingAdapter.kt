package io.ganguo.demo.http.domain

import io.ganguo.demo.BuildConfig
import io.ganguo.demo.http.domain.DomainConstant.Domain.DomainKey
import io.ganguo.demo.http.domain.DomainConstant.Domain.DomainURL
import io.ganguo.http2.bean.HttpHeader
import io.ganguo.http2.config.domain.AbstractHttpMultiUrlSettingAdapter

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/30
 *     desc   : 动态域名适配器
 * </pre>
 */
class MultiUrlSettingAdapter : AbstractHttpMultiUrlSettingAdapter() {


    /**
     * 添加出默认BaseUrl以外的接口配置
     */
    override fun initMultiUrl() {
        addUrl(DomainKey.GANGUO, DomainURL.GANGUO)
        addUrl(DomainKey.GANK, DomainURL.GANK)
        addUrl(DomainKey.DOUBAN, DomainURL.DOUBAN)
    }

    /**
     * 针对域名，配置全局参数
     */
    override fun initMultiUrlGlobalParameters() {
        addGlobalParameter(DomainURL.DOUBAN, "apikey", "0df993c66c0c636e29ecbb5344252a4a")
    }

    /**
     * 针对域名，配置全局Header参数
     */
    override fun initMultiUrlGlobalHeaders() {
        addGlobalHeader(DomainURL.DOUBAN, HttpHeader.HeaderKey.CHANNEL, BuildConfig.FLAVOR)
        addGlobalHeader(DomainURL.DOUBAN, HttpHeader.HeaderKey.VERSION, BuildConfig.VERSION_NAME)
        addGlobalHeader(DomainURL.DOUBAN, HttpHeader.HeaderKey.FROM, HttpHeader.HeaderValue.ANDROID)
    }

    /**
     * 多BaseUrl适配Key
     * @return String
     */
    override fun getAdapterHeaderKey(): String {
        return DomainConstant.Domain.DOMAIN_HEADER_KEY
    }


}