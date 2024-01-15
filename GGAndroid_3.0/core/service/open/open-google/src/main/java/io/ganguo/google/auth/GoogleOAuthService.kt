package io.ganguo.google.auth

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.ganguo.google.AGoogleOAuthService
import io.ganguo.google.GoogleConstants
import io.ganguo.google.GoogleHandler
import io.ganguo.google.GoogleOAuthObservable
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : 谷歌认证服务
 * </pre>
 */
class GoogleOAuthService(activity: Activity) : AGoogleOAuthService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)
    private var googleClient: GoogleSignInClient

    init {
        googleClient = GoogleSignIn.getClient(weakActivity!!.get()!!, newSignInOptions())
    }


    /**
     * 调起Google登录服务
     * @return GoogleOAuthObservable
     */
    override fun startService(): GoogleOAuthObservable {
        val signInIntent = googleClient.signInIntent
        weakActivity!!.get()?.startActivityForResult(signInIntent, GoogleConstants.GOOGLE_REQUEST_AUTH_CODE)
        return resultObserver
    }

    /**
     * 检测Google服务是否正常
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return GoogleHandler.checkException(weakActivity?.get()!!)
    }

    /**
     * Google取消认证状态
     *
     * @param activity
     * @return
     */
    fun oAuthCancel() {
        val client = GoogleSignIn.getClient(weakActivity!!.get()!!, newSignInOptions())
        client.asGoogleApiClient().clearDefaultAccountAndReconnect()
        client.signOut()
    }


    /**
     * create sign in options
     *
     * @return
     */
    private fun newSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
    }

    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
    }
}