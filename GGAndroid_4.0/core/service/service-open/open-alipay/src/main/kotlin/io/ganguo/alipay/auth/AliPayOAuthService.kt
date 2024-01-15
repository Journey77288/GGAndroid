package io.ganguo.alipay.auth

import android.app.Activity
import com.alipay.sdk.app.AuthTask
import io.ganguo.alipay.AAliPayOAuthService
import io.ganguo.alipay.AliPayConstants
import io.ganguo.alipay.AliPayHandler
import io.ganguo.alipay.AliPayOAuthObservable
import io.ganguo.alipay.entity.AuthResult
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝登录服务
 * </pre>
 * @property authInfo 服务端返回授权登录加密信息
 * @property listener 授权自定义接口回调，[AliPayOAuthMethod]中作具体的结果发送处理
 */
class AliPayOAuthService(activity: Activity, private var authInfo: String, private var listener: AliPayOAuthListener?) : AAliPayOAuthService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)


    /**
     * 调起支付宝授权服务
     * @return [AliPayOAuthObservable]
     */
    override fun startService(): AliPayOAuthObservable {
        Thread {
            AuthTask(weakActivity?.get())
                    .let {
                        it.authV2(authInfo, true)
                    }.let {
                        AuthResult(it, true)
                    }.let {
                        handlerOAuthResult(it)
                    }
        }.start()
        return resultObserver
    }


    /**
     * 支付宝SDK异常检测
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return AliPayHandler.checkException(weakActivity?.get()!!)
    }

    /**
     * 处理授权结果
     * @param authResult 授权结果
     */
    private fun handlerOAuthResult(authResult: AuthResult) {
        val resultStatus = authResult.resultStatus
        val resultCode = authResult.resultCode
        if (resultStatus == AliPayConstants.Status.AUTH_STATUS_SUCCESS && resultCode == AliPayConstants.ResultCode.AUTH_RESULT_CODE_SUCCESS) {
            listener?.onAliPayOAuthSuccess(authResult)
        } else if (resultStatus == AliPayConstants.Status.AUTH_STATUS_CANCEL) {
            listener?.onAliPayOnAuthCancel()
        } else if (resultStatus == AliPayConstants.Status.AUTH_STATUS_NETWORK_ERROR) {
            listener?.onAliPayOnAuthFailed(authResult, "network error")
        } else {
            listener?.onAliPayOnAuthFailed(authResult, "system exception")
        }
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
    }


}