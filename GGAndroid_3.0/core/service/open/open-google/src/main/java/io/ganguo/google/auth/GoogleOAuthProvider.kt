package io.ganguo.google.auth

import android.app.Activity
import io.ganguo.google.IGoogleOAuthProvider

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : GoogleOAuthService创建类
 * </pre>
 */
class GoogleOAuthProvider(var activity: Activity) : IGoogleOAuthProvider {
    /**
     * 创建谷歌登录服务类
     * @return AGoogleOAuthService
     */
    override fun newService(): GoogleOAuthService {
        return GoogleOAuthService(activity)
    }
}