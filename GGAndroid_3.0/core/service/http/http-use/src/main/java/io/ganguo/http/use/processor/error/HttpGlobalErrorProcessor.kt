package io.ganguo.http.use.processor.error

import android.accounts.NetworkErrorException
import io.ganguo.http2.error.Errors
import io.ganguo.rx.transformer.interceptor.RxObservableInterceptor
import io.reactivex.Observable
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : Global common error handler
 *            : 常见错误处理
 * </pre>
 */
class HttpGlobalErrorProcessor<T> : RxObservableInterceptor<T> {

    /**
     * observable Transfer Function
     * @param observable Observable<T>
     * @return Observable<T>
     */
    override fun applyInterceptor(observable: Observable<T>): Observable<T> {
        return observable
                .onErrorResumeNext { throwable: Throwable ->
                    var error = when (throwable) {
                        is UnknownHostException,
                        is SocketTimeoutException,
                        is NetworkErrorException -> {
                            Errors.ConnectFailedException(msg = throwable.message.orEmpty(), throwable = throwable)
                        }
                        else -> {
                            throwable
                        }
                    }
                    Observable.error(error)
                }
    }
}
