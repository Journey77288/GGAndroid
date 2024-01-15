package io.ganguo.http.gg.processor.data

import io.ganguo.http.gg.response.HttpResponse
import io.ganguo.rxjava.transformer.transfer.RxObservableTransfer
import io.reactivex.rxjava3.core.Observable


/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/03
 *     desc   : response data handler
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class HttpResponseDataProcessor<T : Any> : RxObservableTransfer<HttpResponse<T>, T> {

    /**
     * observable Transfer Function
     * @param observable Observable<HttpResponse<T>>
     * @return Observable<HttpResponse<T>>
     */
    override fun applyTransfer(observable: Observable<HttpResponse<T>>): Observable<T> {
        return observable
                .map {
                    it.data!!
                }
    }
}