package io.ganguo.pay.wxpay

import android.app.Activity
import io.ganguo.pay.wxpay.entity.WXPayEntity

/**
 * Created by Roger on 07/07/2017.
 * 微信支付
 * 微信支付结果回调处理在[WXPayActivity]
 */
class WXPayMethod(weChatAppId: String) : AWXPayMethod() {

    init {
        WXConstants.WX_APP_ID = weChatAppId
        WXConstants.WX_SDK_IS_INIT = WXConstants.WX_APP_ID.isNotEmpty()
    }


    /**
     * WXPayService 创建方法
     * @param p 支付订单请求数据
     * @return
     */
    override fun newPayProvider(activity: Activity, param: WXPayEntity?): WXPayProvider {
        return WXPayProvider(activity, param!!)
    }
}
