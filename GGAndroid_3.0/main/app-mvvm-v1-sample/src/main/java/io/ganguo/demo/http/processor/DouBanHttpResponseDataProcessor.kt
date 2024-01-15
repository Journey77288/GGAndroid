package io.ganguo.demo.http.processor

import io.ganguo.demo.http.response.DouBanHttpResponse
import io.ganguo.rx.transformer.transfer.RxObservableTransfer
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/03
 *     desc   : DouBan Response Data Handler
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class DouBanHttpResponseDataProcessor<T> : RxObservableTransfer<DouBanHttpResponse<T>, T> {
    /**
     * observable Transfer Function
     * @param observable Observable<HttpResponse<T>>
     * @return Observable<HttpResponse<T>>
     */
    override fun applyTransfer(observable: Observable<DouBanHttpResponse<T>>): Observable<T> {
        return observable
                .map {
                    it.data
                }
    }
}