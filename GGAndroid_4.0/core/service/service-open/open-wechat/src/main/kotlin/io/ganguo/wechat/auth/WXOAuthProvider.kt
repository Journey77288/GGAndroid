package io.ganguo.wechat.auth

import android.content.Context
import io.ganguo.sample.sdk.oauth.OAuthProvider
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 微信服务提供者
 * </pre>
 */
class WXOAuthProvider(context: Context, var appId: String) : OAuthProvider<WXOAuthService> {
    private var weakContext: WeakReference<Context> = WeakReference(context)

    override fun newService(): WXOAuthService {
        return WXOAuthService(weakContext.get()!!, appId)
    }
}