package io.ganguo.pay.core

/**
 * Created by Roger on 05/07/2017.
 */

interface PayCallback<T : PayOrderInfo> {
    fun onPaySuccess(result: PayResult<T>)

    fun onPayError(result: PayResult<T>)

    fun onPayCancel(result: PayResult<T>)

}
