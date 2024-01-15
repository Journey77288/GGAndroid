package io.ganguo.alipay.auth

import android.app.Activity
import io.ganguo.alipay.IAliPayOAuthProvider
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝登录Provider
 * </pre>
 */
class AliPayOAuthProvider(activity: Activity, var authInfo: String, var listener: AliPayOAuthListener) : IAliPayOAuthProvider {
    private var weakActivity: WeakReference<Activity> = WeakReference(activity)

    /**
     * 创建登录认证授权Service
     * @return AliPayOAuthService
     */
    override fun newService(): AliPayOAuthService {
        return AliPayOAuthService(weakActivity.get()!!, authInfo, listener)
    }
}