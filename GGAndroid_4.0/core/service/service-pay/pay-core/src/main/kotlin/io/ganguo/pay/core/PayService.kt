package io.ganguo.pay.core

import io.ganguo.factory.service.ResultEmitterService

/**
 * Created by Roger on 05/07/2017.
 * 支付Service
 */
abstract class PayService<Result : PayResult<*>, ResultObservable> : ResultEmitterService<Result, ResultObservable>() {

    /**
     * 调起支付
     * @return ResultObservable
     */
    fun pay(): ResultObservable {
        return sendErrorOrStartService()
    }
}
