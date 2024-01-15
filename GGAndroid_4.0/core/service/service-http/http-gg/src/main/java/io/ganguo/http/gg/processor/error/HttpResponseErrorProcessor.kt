package io.ganguo.http.gg.processor.error

import io.ganguo.http2.bean.HttpConstant
import io.ganguo.http.gg.response.HttpResponse
import io.ganguo.http.use.HttpUse
import io.ganguo.http.use.processor.error.HttpGlobalErrorProcessor
import io.ganguo.http2.error.Errors
import io.ganguo.rxjava.transformer.RxTransformer
import io.ganguo.rxjava.transformer.interceptor.RxObservableInterceptor
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : HttpResponse 错误数据过滤处理
 * </pre>
 *
 * 备注:
 *     1、此处只处理甘果API规范，常见的API错误。
 *     2、如有其他新增错误类型，可以自己再写一个，加上新增类型，此处已经处理过的错误，可以不用处理。同时添加此Processor和新增Processor即可。
 */
open class HttpResponseErrorProcessor<T> : RxObservableInterceptor<HttpResponse<T>> {

    /**
     * observable Transfer Function
     * @param observable [Observable]
     * @return [Observable]
     */
    override fun applyInterceptor(observable: Observable<HttpResponse<T>>): Observable<HttpResponse<T>> {
        return observable
                .compose(RxTransformer.warp<HttpResponse<T>>(HttpGlobalErrorProcessor()))
                .compose(RxTransformer.errorLoggerProcessor<HttpResponse<T>>())
                .flatMap {
                    onResponseErrorInterceptor(it)
                }


    }

    /**
     * Interceptor Error
     * @param response [HttpResponse]
     * @return [Observable]
     */
    private fun onResponseErrorInterceptor(response: HttpResponse<T>): Observable<HttpResponse<T>> {
        if (response.status == HttpConstant.Status.SUCCESS) {
            return Observable.just(response)
        }
        return if (response.code == HttpConstant.Code.TOKEN_INVALID) {
            HttpUse.get().config.getAuthorizationFailHandle()?.invoke()
            Errors.AuthorizationException(response.code, response.message)
        } else {
            Errors.ApiResponseException(response.code, response.message)
        }.let {
            Observable.error(it)
        }
    }
}