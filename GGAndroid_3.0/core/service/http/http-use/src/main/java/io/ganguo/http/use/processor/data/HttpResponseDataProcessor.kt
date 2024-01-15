package io.ganguo.http.use.processor.data

import io.ganguo.http2.core.use.response.HttpResponse
import io.ganguo.rx.transformer.transfer.RxObservableTransfer
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/03
 *     desc   : response data handler
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class HttpResponseDataProcessor<T> : RxObservableTransfer<HttpResponse<T>, T> {

    /**
     * observable Transfer Function
     * @param observable Observable<HttpResponse<T>>
     * @return Observable<HttpResponse<T>>
     */

    override fun applyTransfer(observable: Observable<HttpResponse<T>>): Observable<T> {
        return observable
                .map {
                    it.data
                }
    }
}