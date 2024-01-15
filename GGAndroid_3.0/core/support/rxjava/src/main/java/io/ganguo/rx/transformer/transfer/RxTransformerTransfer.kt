package io.ganguo.rx.transformer.transfer

import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/27
 *     desc   : RxJava Transfer interface
 * </pre>
 */
interface RxTransformerTransfer<T, R> :
        ObservableTransformer<T, R>,
        SingleTransformer<T, R>,
        FlowableTransformer<T, R>,
        MaybeTransformer<T, R>,
        CompletableTransformer {
    var transfer: RxObservableTransfer<T, R>


    override fun apply(upstream: Observable<T>): ObservableSource<R> {
        return transfer.applyTransfer(upstream)
    }

    override fun apply(upstream: Single<T>): SingleSource<R> {
        return transfer.applyTransfer(upstream.toObservable())
                .firstOrError()
    }

    override fun apply(upstream: Flowable<T>): Publisher<R> {
        return transfer
                .applyTransfer(upstream.toObservable())
                .toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<R> {
        return transfer
                .applyTransfer(upstream.toObservable())
                .firstElement()
    }

    override fun apply(upstream: Completable): CompletableSource {
        return transfer.applyTransfer(upstream
                .toObservable())
                .ignoreElements()
    }


}