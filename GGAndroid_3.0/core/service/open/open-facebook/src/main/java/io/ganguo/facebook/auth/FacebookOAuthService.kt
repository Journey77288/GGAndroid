package io.ganguo.facebook.auth

import android.app.Activity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.ganguo.facebook.AFBOAuthService
import io.ganguo.facebook.FBOAuthResultObservable
import io.ganguo.facebook.FacebookConstants
import io.ganguo.facebook.FacebookHandler
import java.lang.ref.WeakReference
import java.util.*

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook 登录服务
 * </pre>
 */
class FacebookOAuthService(activity: Activity, var callback: FacebookCallback<LoginResult>) : AFBOAuthService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)

    init {
        FacebookConstants.callbackManager = CallbackManager.Factory.create()

    }


    /**
     * 发起Facebook登录
     * @return
     */
    override fun startService(): FBOAuthResultObservable {
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken == null || accessToken.isExpired) {
            LoginManager
                    .getInstance()
                    .registerCallback(FacebookConstants.callbackManager, callback)
            LoginManager
                    .getInstance()
                    .logInWithReadPermissions(weakActivity!!.get(), Arrays.asList("public_profile", "user_friends"))
        }
        return resultObserver
    }

    /**
     * 检测Facebook SDK 配置是否正常
     * @return
     */
    override fun checkException(): Throwable? {
        return FacebookHandler.checkException()
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
        FacebookConstants.callbackManager = null
    }
}
