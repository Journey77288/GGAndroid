package io.ganguo.facebook.auth

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import io.ganguo.facebook.AFBOAuthMethod
import io.ganguo.facebook.FacebookConstants
import io.ganguo.factory.result.IActivityResult
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook登录授权
 * </pre>
 */
@Suppress("DEPRECATION")
class FacebookOAuthMethod(context: Application, appId: String) : AFBOAuthMethod(), FacebookCallback<LoginResult>, IActivityResult {

    init {
        FacebookSdk.setApplicationId(appId)
        FacebookSdk.sdkInitialize(context)
        AppEventsLogger.activateApp(context)
    }

    /**
     * FacebookOAuthService 创建方法
     * @param activity activity引用
     * @return
     */
    override fun newOAuthProvider(activity: FragmentActivity, p: Any?): FacebookOAuthProvider {
        return FacebookOAuthProvider(activity, this)
    }

    /**
     * 注册ActivityResult回调
     * @param requestCode 请求code
     * @param resultCode 返回值
     * @param data Intent
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity?) {
        FacebookConstants.callbackManager?.onActivityResult(requestCode, resultCode, data)
    }


    /**
     * 授权成功
     * @param t
     */
    override fun onSuccess(t: LoginResult) {
        sendResult(OpenResult(OpenChannel.FACE_BOOK, OpenStatus.SUCCESS, result = t))
    }

    /**
     * 授权失败
     */
    override fun onError(error: FacebookException) {
        sendResult(OpenResult(OpenChannel.FACE_BOOK, OpenStatus.FAILED, message = error.message))
    }


    /**
     * 授权取消
     */
    override fun onCancel() {
        sendResult(OpenResult(OpenChannel.FACE_BOOK, OpenStatus.CANCEL))
    }

}
