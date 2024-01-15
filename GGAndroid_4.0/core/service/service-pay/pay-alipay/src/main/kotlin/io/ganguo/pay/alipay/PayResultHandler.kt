package io.ganguo.pay.alipay

import android.os.Handler
import android.os.Message
import android.util.Log
import io.ganguo.factory.GGFactory
import io.ganguo.pay.core.annotation.PayStatus
import io.ganguo.pay.core.annotation.PayType

/**
 * Created by Roger on 05/07/17.
 * 支付结果回调处理
 */
internal class PayResultHandler : Handler() {

    /**
     * 处理支付结果回调
     * @param msg
     */
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if (msg.what != AliPayConstants.PayStatusCode.PAY_FLAG) {
            return
        }
        val payResult = AliPayResult(PayType.ALI_PAY)
        val aliPayData = AliPayOrderInfo(msg.obj.toString())
        val resultStatus = aliPayData.resultStatus

        payResult.result = aliPayData
        payResult.code = resultStatus
        payResult.message = aliPayData.memo
        payResult.status = transformStatus(payResult.code)

        GGFactory.asRxResultSend<AliPayResult>(AliPayMethod::class.java)
                ?.sendResult(payResult)

        Log.d(javaClass.simpleName, "handleMessage:payStatus: $resultStatus")
        Log.d(javaClass.simpleName, "handleMessage: " + msg.obj.toString())
    }


    /**
     * 判断支付状态
     * @param resultCode Int
     * @return PayStatus
     */
    @PayStatus
    private fun transformStatus(resultCode: Int): Int {
        return when (resultCode) {
            AliPayConstants.PayStatusCode.PAY_OK -> PayStatus.SUCCESS
            AliPayConstants.PayStatusCode.PAY_CANCEL -> PayStatus.CANCEL
            else -> PayStatus.FAILED
        }
    }

}
