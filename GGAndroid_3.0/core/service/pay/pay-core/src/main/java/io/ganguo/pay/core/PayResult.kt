package io.ganguo.pay.core

import io.ganguo.pay.core.annotation.PayStatus
import io.ganguo.pay.core.annotation.PayType


/**
 * Created by Roger on 05/07/2017.
 * 支付结果回调
 * @property type 支付类型
 */
data class PayResult<T : PayOrderInfo>(
        @PayType
        @get:PayType
        @setparam:PayType
        var type: String,
        var message: String? = "",
        var code: Int = 0,
        var result: T? = null,
        @PayStatus
        @setparam:PayStatus
        @get:PayStatus
        var status: Int = PayStatus.SUCCESS
)
