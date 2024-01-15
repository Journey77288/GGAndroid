package io.ganguo.demo.http.helper

import io.ganguo.demo.http.processor.DouBanHttpResponseDataProcessor
import io.ganguo.demo.http.processor.DouBanHttpResponseErrorProcessor
import io.ganguo.demo.http.response.DouBanHttpResponse
import io.ganguo.rx.transformer.RxTransformer
import io.ganguo.rx.transformer.interceptor.RxInterceptor
import io.ganguo.rx.transformer.transfer.RxTransformerTransfer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/03
 *     desc   : Response Transformer Handler
 * </pre>
 */
object DouBanHttpHelper {


    /**
     * Response Transformer Handler, filter api error
     * @return IRxInterceptor<T>
     */
    fun <T> errorProcessor(): RxInterceptor<DouBanHttpResponse<T>> {
        return RxTransformer.warp(DouBanHttpResponseErrorProcessor())
    }

    /**
     * 通过操作符，对数据进行转换，获取[[io.ganguo.demo.http.response.DouBanHttpResponse]]data字段数据
     * @return RxTransformerTransfer<DouBanHttpResponse<*>, R>
     */
    fun <T> dataProcessor(): RxTransformerTransfer<DouBanHttpResponse<T>, T> {
        return RxTransformer.warp(DouBanHttpResponseDataProcessor())
    }
}