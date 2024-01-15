package io.ganguo.demo.http.processor

import io.ganguo.demo.http.processor.DouBanHttpResponseErrorProcessor.Companion.Status.SUCCESS
import io.ganguo.demo.http.response.DouBanHttpResponse
import io.ganguo.http.use.processor.error.HttpGlobalErrorProcessor
import io.ganguo.http2.error.Errors
import io.ganguo.rx.transformer.RxTransformer
import io.ganguo.rx.transformer.interceptor.RxObservableInterceptor
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/03
 *     desc   : DouBan HttpResponse 错误数据过滤处理
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class DouBanHttpResponseErrorProcessor<T> : RxObservableInterceptor<DouBanHttpResponse<T>> {

    /**
     * use the observable interceptor (对Observable进行拦截过滤)
     * @param observable Observable
     * @return Observable<T>
     */
    override fun applyInterceptor(observable: Observable<DouBanHttpResponse<T>>): Observable<DouBanHttpResponse<T>> {
        return observable
                .compose(RxTransformer.warp<DouBanHttpResponse<T>>(HttpGlobalErrorProcessor()))
                .compose(RxTransformer.errorLoggerProcessor<DouBanHttpResponse<T>>())
                .flatMap {
                    onResponseErrorInterceptor(it)
                }
    }


    /**
     * Interceptor Error
     * @param response HttpResponse<T>
     * @return Observable<HttpResponse<T>>
     */
    private fun onResponseErrorInterceptor(response: DouBanHttpResponse<T>): Observable<DouBanHttpResponse<T>> {
        if (response.code == SUCCESS) {
            return Observable.just(response)
        }
        return Observable.error(Errors.ApiResponseException(response.code, response.msg.orEmpty()))

    }


    companion object {

        /**
         * @property SUCCESS 为0时，代表请求成功
         */
        object Status {
            const val SUCCESS = 0
        }
    }


}