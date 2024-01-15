package io.ganguo.pay.sample

import io.ganguo.factory.GGFactory
import io.ganguo.core.context.BaseApp
import io.ganguo.pay.alipay.AliPayMethod
import io.ganguo.pay.wxpay.WXPayMethod

/**
 * <pre>
 * author : leo
 * time   : 2018/11/01
 * desc   : Open Sample AppContext
 * </pre>
 */

class AppContext : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        GGFactory.registerMethod(WXPayMethod("wxfd3587fd3c79db51"))
        GGFactory.registerMethod(AliPayMethod())
    }
}
