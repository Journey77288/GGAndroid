package io.ganguo.pay.alipay

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/06
 *     desc   : 支付宝相关常量
 * </pre>
 */
class AliPayConstants {


    /**
     * 支付状态码
     * @property [PAY_FLAG]
     * @property [PAY_OK] 支付成功
     * @property [PAY_WAIT_CONFIRM] 交易待确认
     * @property [PAY_NET_ERR] 网络出错
     * @property [PAY_CANCEL] 交易被取消
     * @property [PAY_FAILED] 支付失败
     */
    object PayStatusCode {
        const val PAY_FLAG = 1
        /*支付宝支付结果码*/
        const val PAY_OK = 9000// 支付成功
        const val PAY_WAIT_CONFIRM = 8000// 交易待确认
        const val PAY_NET_ERR = 6002// 网络出错
        const val PAY_CANCEL = 6001// 交易取消
        const val PAY_FAILED = 4000// 交易失败
    }
}