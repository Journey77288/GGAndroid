package io.ganguo.pay.wxpay.helper

import androidx.fragment.app.FragmentActivity
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseResp
import io.ganguo.factory.GGFactory
import io.ganguo.pay.core.annotation.PayStatus
import io.ganguo.pay.core.annotation.PayType
import io.ganguo.pay.wxpay.WXConstants
import io.ganguo.pay.wxpay.WXPayMethod
import io.ganguo.pay.wxpay.WXPayResult
import io.ganguo.pay.wxpay.WXPayResultObservable
import io.ganguo.pay.wxpay.entity.WXPayEntity

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/14
 *     desc   : 微信支付 帮助类
 * </pre>
 */
object WXPayHelper {

    /**
     * 初始化微信支付
     *
     * @param appId String
     */
    fun init(appId: String) {
        GGFactory.registerMethod(WXPayMethod(appId))
    }

    /**
     * 微信支付
     *
     * @param activity FragmentActivity
     * @param data WXPayEntity
     * @return WXPayResultObservable
     */
    fun pay(activity: FragmentActivity, data: WXPayEntity): WXPayResultObservable {
        return GGFactory
            .getMethod<WXPayMethod>(WXPayMethod::class.java)
            .pay(activity, data)
    }

    /**
     * 微信支付结果回调
     */
    fun onResult(baseResp: BaseResp) {
        val payResult = WXPayResult(PayType.WX_PAY)
        if (baseResp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
            val errCode = baseResp.errCode
            payResult.code = errCode
            payResult.message = baseResp.errStr
            payResult.status = when (errCode) {
                BaseResp.ErrCode.ERR_OK -> PayStatus.SUCCESS
                BaseResp.ErrCode.ERR_COMM -> PayStatus.FAILED
                BaseResp.ErrCode.ERR_USER_CANCEL -> PayStatus.CANCEL
                else -> PayStatus.FAILED
            }
        } else {
            payResult.status = PayStatus.FAILED
            payResult.code = WXConstants.ErrorCode.WX_PAY_NO_RESPONSE_CODE
            payResult.message = "wxpay no response"
        }

        //发送结果回调
        GGFactory
            .asRxResultSend<WXPayResult>(WXPayMethod::class.java)
            ?.sendResult(payResult)
    }
}