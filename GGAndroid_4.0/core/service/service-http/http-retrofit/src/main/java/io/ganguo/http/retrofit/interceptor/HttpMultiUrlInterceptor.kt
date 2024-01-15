package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import io.ganguo.http2.config.domain.HttpMultiBaseUrlAdapter
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/30
 *     desc   : 多BaseUrl处理拦截器，适用于需要处理多个服务器链接的情况
 * </pre>
 */
class HttpMultiUrlInterceptor : Interceptor {


    /**
     * 拦截配置Url
     * @param chain Chain
     * @return Response
     */
    override fun intercept(chain: Interceptor.Chain): Response = let {
        //判断项目是否支持多BaseUrl配置
        val isSupport = RetrofitService.get().isSupportMultiBaseUrl()
        val adapter = RetrofitService.get().getMultiUrlAdapter()
        if (!isSupport || adapter == null) {
            return chain.proceed(chain.request())
        }
        // 根据Header配置，判断是否需要需要拼接服务器访问路径，发起新的请求
        val olderRequest = chain.request()
        var url = getHttpUrl(olderRequest, adapter) ?: return chain.proceed(chain.request())
        val headerKey = adapter.getAdapterHeaderKey()
        url = createHttpUrlFromOldUrlPath(olderRequest.url, url.newBuilder())
        var builder = chain.request().newBuilder()
        builder.removeHeader(headerKey)
        var newRequest = builder.url(url).build()
        //创建请的请求链接
        chain.proceed(newRequest)
    }


    /**
     * 创建一个新的Http网络请求
     * @param builder Request.Builder
     * @return Response
     */
    private fun newRequest(olderRequest: Request, domainURL: HttpUrl, builder: Request.Builder) = let {
        //创建请的请求链接
        domainURL
                .newBuilder()
                .let {
                    //追加旧Url、Path默认端口配置
                    createHttpUrlFromOldUrlPath(olderRequest.url, it)
                }
                .let {
                    builder.url(it).build()
                }
    }


    /**
     * append Old Url Path to new HttpUrl
     * @param olderUrl HttpUrl
     * @param newHttpUrlBuilder Builder
     * @return HttpUrl
     */
    private fun createHttpUrlFromOldUrlPath(olderUrl: HttpUrl, newHttpUrlBuilder: HttpUrl.Builder) = let {
        olderUrl.pathSegments
                .forEach { path ->
                    newHttpUrlBuilder.addPathSegment(path)
                }
                .let {
                    newHttpUrlBuilder.build()
                }

    }


    /**
     * 获取Header中设置的`Domain`参数配置
     * @param request Request
     * @return String
     */
    private fun getHttpUrl(request: Request, adapter: HttpMultiBaseUrlAdapter): HttpUrl? = let {
        val adapterHeaderKey = adapter.getAdapterHeaderKey()
        if (adapterHeaderKey.isNullOrEmpty()) {
            return null
        } else {
            val adapterHeaderValue = getAdapterHeaderValue(request, adapterHeaderKey)
            adapter.getUrl(adapterHeaderValue).toHttpUrlOrNull()
        }
    }


    /**
     * 从Header中取出对应的域名配置Value
     * @param request Request
     * @param adapterHeaderKey String
     * @return String
     */
    private fun getAdapterHeaderValue(request: Request, adapterHeaderKey: String) = let {
        request.header(adapterHeaderKey).orEmpty()
    }


}