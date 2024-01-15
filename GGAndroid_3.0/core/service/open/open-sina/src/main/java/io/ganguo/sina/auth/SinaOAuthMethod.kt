package io.ganguo.sina.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler
import io.ganguo.factory.result.IActivityResult
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.sina.ASinaOAuthMethod
import io.ganguo.sina.SinaHandler

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 新浪微博登录
 * </pre>
 */
class SinaOAuthMethod(context: Context, appKey: String, redirectUrl: String, sinaScope: String) : ASinaOAuthMethod(), IActivityResult, WbAuthListener {

    private var ssoHandler: SsoHandler? = null

    init {
        WbSdk.install(context, AuthInfo(context, appKey, redirectUrl, sinaScope))
    }


    /**
     * 创建服务提供者Provider
     * @param activity
     * @param p
     */
    override fun newOAuthProvider(activity: Activity, p: Any?): SinaOAuthProvider {
        ssoHandler = SsoHandler(activity)
        return SinaOAuthProvider(activity, ssoHandler, this)
    }

    /**
     * 微博登录 成功
     * @param p0
     */
    override fun onSuccess(p0: Oauth2AccessToken?) {
        sendResult(OpenResult(OpenChannel.SINA, OpenStatus.SUCCESS, result = p0))
    }

    /**
     * 微博登录 失败
     * @param p0
     */
    override fun onFailure(p0: WbConnectErrorMessage?) {
        sendResult(OpenResult(OpenChannel.SINA, OpenStatus.FAILED, message = p0?.errorMessage))
    }

    /**
     * 微博登录 取消
     * @param p0
     */
    override fun cancel() {
        sendResult(OpenResult(OpenChannel.SINA, OpenStatus.CANCEL))
    }

    /**
     * 注册ActivityResult回调 （必须在对应Activity中调用，否则不会有回调事件）
     * @param requestCode 请求code
     * @param resultCode 返回值
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (isRelease) {
            return
        }
        ssoHandler?.authorizeCallBack(requestCode, resultCode, data)
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        ssoHandler = null
    }


}