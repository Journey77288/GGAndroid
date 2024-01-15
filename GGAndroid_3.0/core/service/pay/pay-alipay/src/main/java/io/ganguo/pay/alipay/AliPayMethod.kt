package io.ganguo.pay.alipay

import android.app.Activity

/**
 * Created by Roger on 07/07/2017.
 * 支付宝支付
 * 支付结果处理在[PayResultHandler]
 */
class AliPayMethod : AAliPayMethod() {

    /**
     * AliPayService 创建方法
     * @param param 支付订单请求数据
     * @return
     */
    override fun newPayProvider(activity: Activity, param: String?): AliPayProvider {
        return AliPayProvider(activity, param!!)
    }
}
