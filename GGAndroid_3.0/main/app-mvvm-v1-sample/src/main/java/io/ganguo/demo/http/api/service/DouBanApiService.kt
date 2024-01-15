package io.ganguo.demo.http.api.service

import io.ganguo.demo.entity.one.MovieEntity
import io.ganguo.demo.http.domain.DomainConstant
import io.ganguo.demo.http.domain.DomainConstant.Domain.DomainKey
import io.ganguo.demo.http.response.DouBanHttpResponse
import io.ganguo.http.retrofit.interceptor.HttpMultiUrlInterceptor
import io.ganguo.http2.config.domain.AbstractHttpMultiUrlSettingAdapter
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * 豆瓣开源api
 * created by leo on 2018/7/30.
 */
interface DouBanApiService {

    /**
     * 使用默认BaseUrl的调用接口
     * @param apiKey String
     * @return Observable<DouBanHttpResponse<List<MovieEntity>>>
     */
    @GET(API_DOU_BAN_LIST)
    fun getDouBanMovies(): Observable<DouBanHttpResponse<List<MovieEntity>>>

    /**
     *  指定默认BaseUrl以外的域名（也就是支持多域名），在发起网络请求阶段，去配置域名
     *  1、`需要单独指定域名，@Headers必须配置，格式为@Headers(adapterHeaderKey:adapterHeaderValue")`
     *  2、`具体实现参考[HttpMultiUrlInterceptor] 和 [AbstractHttpMultiUrlSettingAdapter]内部实现`
     * @return Observable<String>
     */
    @Headers("${DomainConstant.Domain.DOMAIN_HEADER_KEY}:${DomainKey.GANK}")
    @GET("/api/v2/banners")
    fun getGankBanners(): Observable<Any>

    companion object {
        const val API_DOU_BAN_LIST = "/v2/movie/top250"
    }
}
