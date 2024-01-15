package io.ganguo.alipay.share

import android.app.Activity
import io.ganguo.alipay.AAliPayShareMethod
import io.ganguo.alipay.AliPayConstants
import io.ganguo.alipay.entity.AliPayShareEntity

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝分享
 * </pre>
 */
class AliPayShareMethod(appId: String) : AAliPayShareMethod() {

    init {
        AliPayConstants.ALI_PAY_APP_ID = appId
        AliPayConstants.ALI_PAY_SDK_IS_INIT = AliPayConstants.ALI_PAY_APP_ID.isNotEmpty()
    }

    /**
     * 支付分享调起服务创建
     * @param activity Activity
     * @param p String? 服务端下发加密的授权商户信息
     * @return AliPayShareProvider
     */
    override fun newShareProvider(activity: Activity, shareParam: AliPayShareEntity): AliPayShareProvider {
        return AliPayShareProvider(activity, AliPayConstants.ALI_PAY_APP_ID, shareParam)
    }
}