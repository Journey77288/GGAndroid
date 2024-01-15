package io.ganguo.alipay.auth

import io.ganguo.alipay.entity.AuthResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝支付结果回调
 * </pre>
 * @see [onAliPayOAuthSuccess] 登录授权成功
 * @see [onAliPayOnAuthFailed] 登录授权失败
 * @see [onAliPayOnAuthCancel] 登录授权取消
 */
interface AliPayOAuthListener {
    fun onAliPayOAuthSuccess(result: AuthResult)

    fun onAliPayOnAuthFailed(result: AuthResult?, message: String)

    fun onAliPayOnAuthCancel()
}