package io.ganguo.alipay.helper

import androidx.fragment.app.FragmentActivity
import com.alipay.share.sdk.openapi.BaseResp
import io.ganguo.alipay.AliPayOAuthObservable
import io.ganguo.alipay.AliPayShareResult
import io.ganguo.alipay.AliPayShareResultObservable
import io.ganguo.alipay.auth.AliPayOAuthMethod
import io.ganguo.alipay.entity.AliPayShareEntity
import io.ganguo.alipay.share.AliPayShareMethod
import io.ganguo.factory.GGFactory
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/14
 *     desc   : 支付宝分享/登录 帮助类
 * </pre>
 */
object AlipayOpenHelper {

    /**
     * 初始化支付宝分享
     *
     * @param appKey String
     */
    fun initShare(appKey: String) {
        GGFactory.registerMethod(AliPayShareMethod(appKey))
    }

    /**
     * 初始化支付宝登录
     */
    fun initOauth() {
        GGFactory.registerMethod(AliPayOAuthMethod())
    }

    /**
     * 支付宝分享
     *
     * @param activity FragmentActivity
     * @param data AliPayShareEntity
     * @return AliPayShareResultObservable
     */
    fun share(activity: FragmentActivity, data: AliPayShareEntity): AliPayShareResultObservable {
        return GGFactory
            .getMethod<AliPayShareMethod>(AliPayShareMethod::class.java)
            .share(activity, data)
    }

    /**
     * 支付宝登录
     *
     * @param activity FragmentActivity
     * @param param String? 服务端接口返回的加密认证商户信息
     * @return AliPayOAuthObservable
     */
    fun oauth(activity: FragmentActivity, param: String?): AliPayOAuthObservable {
       return GGFactory
           .getMethod<AliPayOAuthMethod>(AliPayOAuthMethod::class.java)
           .oauth(activity, param)
    }

    /**
     * 分享结果回调
     *
     * @param baseResp BaseResp
     */
    fun onResult(baseResp: BaseResp) {
        var result = when (baseResp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                AliPayShareResult(OpenChannel.ALI_PAY, OpenStatus.SUCCESS)
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                AliPayShareResult(OpenChannel.ALI_PAY, OpenStatus.CANCEL)
            }
            else -> {
                AliPayShareResult(OpenChannel.ALI_PAY, OpenStatus.FAILED, message = baseResp.errStr, result = baseResp)
            }
        }
        GGFactory
            .asRxResultSend<AliPayShareResult>(AliPayShareMethod::class.java)
            ?.sendResult(result)
    }
}