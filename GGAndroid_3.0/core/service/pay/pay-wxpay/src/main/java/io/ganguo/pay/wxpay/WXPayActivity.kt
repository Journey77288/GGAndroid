package io.ganguo.pay.wxpay

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.ganguo.factory.GGFactory

import io.ganguo.pay.core.annotation.PayStatus
import io.ganguo.pay.core.annotation.PayType

/**
 * Created by Roger on 06/07/2017.
 * 微信支付回调处理
 */
open class WXPayActivity : Activity(), IWXAPIEventHandler {
    private var api: IWXAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this, WXConstants.WX_APP_ID)
        val handled = api!!.handleIntent(intent, this)
        if (!handled) {
            finish()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (api != null) {
            api!!.handleIntent(intent, this)
        }
    }

    override fun onReq(baseReq: BaseReq) {}


    /**
     * 微信支付回调结果处理
     * @param baseResp
     */
    override fun onResp(baseResp: BaseResp) {
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

        finish()
    }
}
