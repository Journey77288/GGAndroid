package io.ganguo.wechat.auth

import androidx.fragment.app.FragmentActivity
import io.ganguo.wechat.*
import io.ganguo.wechat.AWXAuthMethod


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
    override fun newOAuthProvider(activity: FragmentActivity, p: Any?): WXOAuthProvider {
        return WXOAuthProvider(activity.applicationContext, WXConstants.WX_APP_ID)
    }

}
