package io.ganguo.pay.wxpay.entity

import com.tencent.mm.opensdk.modelpay.PayReq
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Roger on 06/07/2017.
 */
@Serializable
data class WXPayEntity(var appid: String? = null,
                       var noncestr: String? = null,
                       @SerialName("package")
                       var packageX: String? = null,
                       var prepayid: String? = null,
                       var partnerid: String? = null,
                       var timestamp: String? = null,
                       var sign: String? = null) {
    fun toPayReq(): PayReq {
        val req = PayReq()
        req.appId = appid
        req.partnerId = partnerid
        req.prepayId = prepayid
        req.packageValue = packageX
        req.nonceStr = noncestr
        req.timeStamp = timestamp
        req.sign = sign
        return req
    }
}
