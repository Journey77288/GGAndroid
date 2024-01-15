package io.ganguo.rx.transformer

import io.ganguo.rx.transformer.interceptor.RxInterceptor
import io.ganguo.rx.transformer.interceptor.RxObservableInterceptor
import io.ganguo.rx.transformer.processor.ErrorLoggerProcessor
import io.ganguo.rx.transformer.processor.ErrorToastProcessor
import io.ganguo.rx.transformer.transfer.RxObservableTransfer
import io.ganguo.rx.transformer.transfer.RxTransfer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/04
 *     desc   : RxJava Transformer
 * </pre>
 */
object RxTransformer {


    /**
     * warp transfer to RxInterceptor
     * @param transfer IObservableInterceptor
     * @return IRxInterceptor<T>
     */
    @JvmStatic
    fun <T> warp(transfer: RxObservableInterceptor<T>): RxInterceptor<T> {
        return RxInterceptor(transfer)
    }


    /**
     * warp transfer to RxTransformer
     * @param transfer IObservableTransfer
     * @return IRxTransformer<T>
     */
    @JvmStatic
    fun <T, R> warp(transfer: RxObservableTransfer<T, R>): RxTransfer<T, R> {
        return RxTransfer(transfer)
    }


    /**
     * 发生错误时，打印出Log日志，根据需求在使用时添加
     * @return
     */
    @JvmStatic
    fun <T> errorLoggerProcessor(): RxInterceptor<T> {
        return warp(ErrorLoggerProcessor())
    }


    /**
     * 发生错误时，弹窗Toast提示，根据需求在使用时添加
     * @return
     */
    @JvmStatic
    fun <T> errorToastProcessor(): RxInterceptor<T> {
        return warp(ErrorToastProcessor())
    }
}