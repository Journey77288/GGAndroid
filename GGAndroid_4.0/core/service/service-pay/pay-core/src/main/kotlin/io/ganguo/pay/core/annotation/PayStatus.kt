package io.ganguo.pay.core.annotation

import androidx.annotation.IntDef
import io.ganguo.pay.core.annotation.PayStatus.Companion.CANCEL
import io.ganguo.pay.core.annotation.PayStatus.Companion.FAILED
import io.ganguo.pay.core.annotation.PayStatus.Companion.SUCCESS

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/13
 *     desc   : 支付状态
 * </pre>
 */
/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 支付结果状态
 * </pre>
 * @property SUCCESS 支付成功
 * @property CANCEL 支付取消
 * @property FAILED 支付失败
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(SUCCESS, CANCEL, FAILED)
annotation class PayStatus {

    companion object {
        const val SUCCESS: Int = 0
        const val CANCEL: Int = 2
        const val FAILED: Int = 3
    }
}