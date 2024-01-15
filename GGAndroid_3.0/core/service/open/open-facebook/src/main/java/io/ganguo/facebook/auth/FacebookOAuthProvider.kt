package io.ganguo.facebook.auth

import android.app.Activity
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import io.ganguo.facebook.IFBOAuthProvider
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook登录服务创建类
 * </pre>
 */
class FacebookOAuthProvider(activity: Activity, var callback: FacebookCallback<LoginResult>) : IFBOAuthProvider {
    private var weakActivity = WeakReference<Activity>(activity)

    override fun newService(): FacebookOAuthService {
        return FacebookOAuthService(weakActivity.get()!!, callback)
    }
}