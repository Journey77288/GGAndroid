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
 *     desc   : 多BaseUrl处理拦截器
 * </pre>
 */
class HttpMultiUrlInterceptor : HttpAddedBuilderInterceptor {

    /**
     * 判断处理域名
     * @param builder Request.Builder
     * @param chain Interceptor.Chain
     */
    override fun sendRequest(chain: Interceptor.Chain, builder: Request.Builder): Response {
        //匿名函数，调用旧请求
        val sendOldRequest: () -> Response = {
            super.sendRequest(chain, builder)
        }
        //匿名函数，发起新请求
        val sendNewRequest: (String, Request) -> Response = { headerKey: String, newRequest: Request ->
            builder.removeHeader(headerKey)
            chain.proceed(newRequest)
        }
        //判断项目是否支持多BaseUrl配置
        val isSupport = RetrofitService.get().isSupportMultiBaseUrl()
        val adapter = RetrofitService.get().getMultiUrlAdapter()
        if (!isSupport || adapter == null) {
            return sendOldRequest.invoke()
        }

        // 根据Header配置，判断是否需要需要拼接服务器访问路径，发起新的请求
        val olderRequest = chain.request()
        return newHttpUrl(olderRequest, adapter).let {
            if (it != null) {
                val headerKey = adapter.getAdapterHeaderKey()
                val newRequest = newRequest(olderRequest, it, builder)
                sendNewRequest.invoke(headerKey, newRequest)
            } else {
                sendOldRequest.invoke()
            }
        }
    }


    /**
     * 创建一个新的Http网络请求
     * @param builder Request.Builder
     * @return Response
     */
    private fun newRequest(olderRequest: Request, domainURL: HttpUrl, builder: Request.Builder): Request {
        //创建请的请求链接
        return domainURL
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
    private fun createHttpUrlFromOldUrlPath(olderUrl: HttpUrl, newHttpUrlBuilder: HttpUrl.Builder): HttpUrl {
        return olderUrl.pathSegments
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
    private fun newHttpUrl(request: Request, adapter: HttpMultiBaseUrlAdapter): HttpUrl? {
        val adapterHeaderKey = adapter.getAdapterHeaderKey()
        val adapterHeaderValue = getAdapterHeaderValue(request, adapterHeaderKey)
        return adapter
                .getUrl(adapterHeaderValue)
                .toHttpUrlOrNull()
    }


    /**
     * 从Header中取出对应的域名配置Value
     * @param request Request
     * @param adapterHeaderKey String
     * @return String
     */
    private fun getAdapterHeaderValue(request: Request, adapterHeaderKey: String): String {
        return request.header(adapterHeaderKey).orEmpty()
    }

}