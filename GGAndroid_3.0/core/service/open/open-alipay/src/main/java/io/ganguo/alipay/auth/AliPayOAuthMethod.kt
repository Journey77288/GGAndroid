package io.ganguo.alipay.auth

import android.app.Activity
import io.ganguo.alipay.AAliPayOAuthMethod
import io.ganguo.alipay.AliPayHandler
import io.ganguo.alipay.AliPayOAuthObservable
import io.ganguo.alipay.entity.AuthResult
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝登录
 * </pre>
 */
class AliPayOAuthMethod : AAliPayOAuthMethod(), AliPayOAuthListener {

    /***
     * 该方法在支付宝授权sdk下，不使用，请使用[oAuth(activity: Activity, p: String?)]
     * @param activity Activity
     * @return AliPayOAuthObservable
     */
    @Deprecated("支付宝授权必须传参，所以该方法弃用", ReplaceWith("super.oAuth(activity)", "io.ganguo.open.sdk.oauth.OAuthMethod"))
    override fun oAuth(activity: Activity): AliPayOAuthObservable {
        return super.oAuth(activity)
    }

    /**
     * 支付授权调起服务创建
     * @param activity Activity
     * @param p String? 服务端下发加密的授权商户信息
     * @return AliPayOAuthProvider
     */
    override fun newOAuthProvider(activity: Activity, p: String?): AliPayOAuthProvider {
        return AliPayOAuthProvider(activity, p.orEmpty(), this)
    }


    /***
     * 支付宝授权成功
     * @param result AuthResult
     */
    override fun onAliPayOAuthSuccess(result: AuthResult) {
        sendResult(OpenResult(OpenChannel.ALI_PAY, OpenStatus.SUCCESS, result = result))
    }

    /***
     * 支付宝授权失败
     * @param result AuthResult
     */
    override fun onAliPayOnAuthFailed(result: AuthResult?, message: String) {
        sendResult(OpenResult(OpenChannel.ALI_PAY, OpenStatus.FAILED, message, result = result))
    }

    /***
     * 支付宝授权取消
     */
    override fun onAliPayOnAuthCancel() {
        sendResult(OpenResult(OpenChannel.ALI_PAY, OpenStatus.CANCEL))
    }

}