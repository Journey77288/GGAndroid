package io.ganguo.pay.core.annotation

import androidx.annotation.StringDef
import io.ganguo.pay.core.annotation.PayType.Companion.ALI_PAY
import io.ganguo.pay.core.annotation.PayType.Companion.WX_PAY

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/13
 *     desc   : 支付类型
 * </pre>
 * @property WX_PAY 微信支付
 * @property ALI_PAY 支付宝支付
 */
@Retention(AnnotationRetention.RUNTIME)
@StringDef(WX_PAY, ALI_PAY)
annotation class PayType {
    companion object {
        const val WX_PAY: String = "wx_pay"
        const val ALI_PAY: String = "ali_pay"
    }
}