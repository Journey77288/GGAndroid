package io.ganguo.pay.alipay.helper

import androidx.fragment.app.FragmentActivity
import io.ganguo.factory.GGFactory
import io.ganguo.pay.alipay.AliPayMethod
import io.ganguo.pay.alipay.AliPayResultObservable

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/14
 *     desc   : 支付宝支付 帮助类
 * </pre>
 */
object AlipayPayHelper {

    /**
     * 初始化
     */
    fun init() {
        GGFactory.registerMethod(AliPayMethod())
    }

    /**
     * 支付宝支付
     *
     * @param activity FragmentActivity
     * @param info String
     * @return AliPayResultObservable
     */
    fun pay(activity: FragmentActivity, info: String): AliPayResultObservable {
        return GGFactory
            .getMethod<AliPayMethod>(AliPayMethod::class.java)
            .pay(activity, info)
    }
}