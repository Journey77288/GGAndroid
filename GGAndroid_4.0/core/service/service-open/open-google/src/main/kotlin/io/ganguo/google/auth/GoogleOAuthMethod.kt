package io.ganguo.google.auth

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import io.ganguo.factory.result.IActivityResult
import io.ganguo.google.AGoogleOAuthMethod
import io.ganguo.google.GoogleConstants
import io.ganguo.google.IGoogleOAuthProvider
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : Google认证登录
 * </pre>
 */
class GoogleOAuthMethod(idToken: String) : AGoogleOAuthMethod(), IActivityResult {

    init {
        GoogleConstants.GOOGLE_CLIENT_ID_TOKEN = idToken
    }

    /**
     * 取消登录认证服务
     * @param activity
     */
    fun oAuthCancel(activity: Activity) {
        GoogleOAuthProvider(activity).run {
            asService(activity, this).oAuthCancel()
            release()
        }
    }

    /**
     * 创建服务提供者Provider
     * @param activity
     * @return [IGoogleOAuthProvider]
     */
    override fun newOAuthProvider(activity: FragmentActivity, p: Any?): IGoogleOAuthProvider {
        return GoogleOAuthProvider(activity)
    }

    /**
     * ActivityResult
     * @param requestCode 请求码
     * @return [Int]
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity?) {
        if (requestCode != GoogleConstants.GOOGLE_REQUEST_AUTH_CODE) {
            return
        }
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        //取消登录认证
        if (task.isCanceled) {
            sendResult(OpenResult(OpenChannel.GOOGLE, OpenStatus.CANCEL))
            return
        }
        try {
            val account = task.getResult(ApiException::class.java)
            //登录认证成功
            sendResult(OpenResult(OpenChannel.GOOGLE, OpenStatus.SUCCESS, result = account))
        } catch (e: ApiException) {
            e.printStackTrace()
            //登录认证失败
            sendResult(OpenResult(OpenChannel.GOOGLE, OpenStatus.FAILED, message = e.message))
        }
    }

}
