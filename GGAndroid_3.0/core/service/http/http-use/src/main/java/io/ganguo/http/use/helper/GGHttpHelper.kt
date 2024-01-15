package io.ganguo.http.use.helper

import io.ganguo.http.use.processor.data.HttpPaginationProcessor
import io.ganguo.http.use.processor.data.HttpResponseDataProcessor
import io.ganguo.http.use.processor.error.HttpResponseErrorProcessor
import io.ganguo.http2.core.use.response.HttpPaginationDTO
import io.ganguo.http2.core.use.response.HttpResponse
import io.ganguo.http2.core.use.response.paging.IPagingHandler
import io.ganguo.rx.transformer.RxTransformer
import io.ganguo.rx.transformer.interceptor.RxInterceptor
import io.ganguo.rx.transformer.transfer.RxTransformerTransfer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/03
 *     desc   : Response data handler
 * </pre>
 */
class GGHttpHelper private constructor() {


    companion object {
        /**
         * 过滤接口约定的错误，并在代码中处理。例如:Token过期
         * @return IRxInterceptor<T>
         */
        fun <T> errorProcessor(): RxInterceptor<HttpResponse<T>> {
            return RxTransformer.warp(HttpResponseErrorProcessor())
        }

        /**
         * 通过操作符，对数据进行转换，获取[HttpResponse]data字段数据
         * `如果data字段有可能出现null的情况，就不要用这个函数，自己通过filter操作符判断处理`
         * @return IRxTransfer<T,R>
         */
        fun <T> dataProcessor(): RxTransformerTransfer<HttpResponse<T>, T> {
            return RxTransformer.warp(HttpResponseDataProcessor())
        }

        /**
         * Page分页处理，配合RxJava用
         * @param iPageHandler IPagingHandler
         * @return RxTransformerInterceptor
         */
        @JvmStatic
        fun <T> pagingProcessor(iPageHandler: IPagingHandler): RxInterceptor<HttpPaginationDTO<List<T>, *>> {
            return RxTransformer.warp(HttpPaginationProcessor(iPageHandler))
        }

    }
}