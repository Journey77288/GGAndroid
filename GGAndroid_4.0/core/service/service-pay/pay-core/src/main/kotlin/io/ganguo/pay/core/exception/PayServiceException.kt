package io.ganguo.pay.core.exception

import io.ganguo.factory.exception.ServiceException
import io.ganguo.pay.core.annotation.PayType

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/06
 *     desc   : 支付SDK调用异常
 * </pre>
 */
class PayServiceException(@PayType var payType: String, errorCode: Int, errorMsg: String, cause: Throwable? = null)
    : ServiceException(errorCode, errorMsg, cause) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as PayServiceException

        if (payType != other.payType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + payType.hashCode()
        return result
    }

    override fun toString(): String {
        return "PayServiceException()"
    }


}