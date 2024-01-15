@file:JvmName("OkHttpClientExtension")

package io.ganguo.http.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/17
 *   @desc   : OkHttpClient.Builder Extension Func
 * </pre>
 */


/**
 * 添加Http请求拦截器
 * @receiver OkHttpClient.Builder
 * @param interceptors Array<out Interceptor>
 * @return OkHttpClient.Builder
 */
fun OkHttpClient.Builder.addInterceptors(vararg interceptors: Interceptor) = let {
    interceptors.forEach {
        this.addInterceptor(it)
    }
    this
}