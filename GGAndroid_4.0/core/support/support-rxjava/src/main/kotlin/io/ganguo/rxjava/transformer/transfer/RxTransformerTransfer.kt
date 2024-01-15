package io.ganguo.rxjava.transformer.transfer

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableSource
import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeSource
import io.reactivex.rxjava3.core.MaybeTransformer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.core.SingleTransformer
import org.reactivestreams.Publisher

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/27
 *     desc   : RxJava Transfer interface
 * </pre>
 */
interface RxTransformerTransfer<T : Any, R : Any> :
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