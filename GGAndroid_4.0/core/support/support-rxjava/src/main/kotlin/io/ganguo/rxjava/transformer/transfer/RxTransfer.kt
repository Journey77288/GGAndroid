package io.ganguo.rxjava.transformer.transfer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : Observable Transfer warp
 * </pre>
 */
open class RxTransfer<T : Any, R : Any> internal constructor(override var transfer: RxObservableTransfer<T, R>) :
    RxTransformerTransfer<T, R> {
}