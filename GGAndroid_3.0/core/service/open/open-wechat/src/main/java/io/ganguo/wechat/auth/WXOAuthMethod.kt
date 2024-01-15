package io.ganguo.wechat.auth

import android.app.Activity
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.open.sdk.oauth.OAuthMethod
import io.ganguo.open.sdk.oauth.OAuthProvider
import io.ganguo.open.sdk.oauth.OAuthService
import io.ganguo.wechat.*
import io.ganguo.wechat.AWXAuthMethod
import io.reactivex.Observable


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 微信登录
 * </pre>
 */
open class WXOAuthMethod(appId: String) : AWXAuthMethod() {

    init {
        WXConstants.WX_APP_ID = appId
        WXConstants.WX_SDK_IS_INIT = WXConstants.WX_APP_ID.isNotEmpty()
    }



    /**
     * 创建服务提供者Provider
     * @param activity
     * @return [WXOAuthProvider]
     */
    override fun newOAuthProvider(activity: Activity, p: Any?): WXOAuthProvider {
        return WXOAuthProvider(activity, WXConstants.WX_APP_ID)
    }

}