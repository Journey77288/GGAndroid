package io.ganguo.rxjava.transformer

import io.ganguo.rxjava.transformer.interceptor.RxInterceptor
import io.ganguo.rxjava.transformer.interceptor.RxObservableInterceptor
import io.ganguo.rxjava.transformer.processor.ErrorLoggerProcessor
import io.ganguo.rxjava.transformer.processor.ErrorToastProcessor
import io.ganguo.rxjava.transformer.transfer.RxObservableTransfer
import io.ganguo.rxjava.transformer.transfer.RxTransfer

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
    fun <T : Any> warp(transfer: RxObservableInterceptor<T>): RxInterceptor<T> {
        return RxInterceptor(transfer)
    }


    /**
     * warp transfer to RxTransformer
     * @param transfer IObservableTransfer
     * @return IRxTransformer<T>
     */
    @JvmStatic
    fun <T : Any, R : Any> warp(transfer: RxObservableTransfer<T, R>): RxTransfer<T, R> {
        return RxTransfer(transfer)
    }


    /**
     * 发生错误时，打印出Log日志，根据需求在使用时添加
     * @return
     */
    @JvmStatic
    fun <T : Any> errorLoggerProcessor(): RxInterceptor<T> {
        return warp(ErrorLoggerProcessor())
    }


    /**
     * 发生错误时，弹窗Toast提示，根据需求在使用时添加
     * @return
     */
    @JvmStatic
    fun <T : Any> errorToastProcessor(): RxInterceptor<T> {
        return warp(ErrorToastProcessor())
    }
}