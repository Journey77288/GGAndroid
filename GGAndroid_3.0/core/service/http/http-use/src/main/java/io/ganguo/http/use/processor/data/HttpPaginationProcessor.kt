package io.ganguo.http.use.processor.data

import io.ganguo.http2.core.use.response.HttpPaginationDTO
import io.ganguo.http2.core.use.response.paging.IPagingHandler
import io.ganguo.rx.transformer.interceptor.RxObservableInterceptor
import io.reactivex.Observable


/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : 符合甘果Api规范，分页数据处理类
 * </pre>
 */
class HttpPaginationProcessor<T>(private val iPageHandler: IPagingHandler) : RxObservableInterceptor<HttpPaginationDTO<List<T>, *>> {

    /**
     * observable interceptor Function
     * @param observable Observable<HttpPaginationDTO<List<T>, E>>
     * @return Observable<HttpPaginationDTO<List<T>, E>>
     */
    override fun applyInterceptor(observable: Observable<HttpPaginationDTO<List<T>, *>>): Observable<HttpPaginationDTO<List<T>, *>> {
        return observable
                .map {
                    var pagination = it.pagination
                    iPageHandler.pageHelper.page = pagination.page
                    iPageHandler.pageHelper.total = pagination.total
                    iPageHandler.pageHelper.lastPage = pagination.last
                    iPageHandler.pageHelper.pageSize = pagination.size
                    it
                }
    }
}