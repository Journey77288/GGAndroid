package io.ganguo.sina.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.common.UiError
import com.sina.weibo.sdk.openapi.IWBAPI
import com.sina.weibo.sdk.openapi.WBAPIFactory
import io.ganguo.factory.result.IActivityResult
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sina.ASinaOAuthMethod

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 新浪微博登录
 * </pre>
 */
class SinaOAuthMethod(context: Context, appKey: String, redirectUrl: String, sinaScope: String) : ASinaOAuthMethod(),
    IActivityResult {
    private var mApi: IWBAPI? = null

    init {
        mApi = WBAPIFactory.createWBAPI(context)
        val authInfo = AuthInfo(context, appKey, redirectUrl, sinaScope)
        mApi?.registerApp(context, authInfo, null)
    }


    /**
     * 创建服务提供者Provider
     * @param activity
     * @param p
     */
    override fun newOAuthProvider(activity: FragmentActivity, p: Any?): SinaOAuthProvider {
        mApi = WBAPIFactory.createWBAPI(activity)
        return SinaOAuthProvider(activity, mApi, object : WbAuthListener {
            /**
             * 登录成功
             * @param p0 Oauth2AccessToken
             */
            override fun onComplete(p0: Oauth2AccessToken?) {
                sendResult(OpenResult(OpenChannel.SINA, OpenStatus.SUCCESS, result = p0))
            }

            /**
             * 登录失败
             * @param p0 UiError
             */
            override fun onError(p0: UiError?) {
                sendResult(OpenResult(OpenChannel.SINA, OpenStatus.FAILED, message = p0?.errorMessage))
            }

            /**
             * 取消登录
             */
            override fun onCancel() {
                sendResult(OpenResult(OpenChannel.SINA, OpenStatus.CANCEL))
            }
        })
    }

    /**
     * 注册ActivityResult回调 （必须在对应Activity中调用，否则不会有回调事件）
     * @param requestCode 请求code
     * @param resultCode 返回值
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity?) {
        if (isRelease) {
            return
        }
        activity?.let {
            mApi?.authorizeCallback(activity, requestCode, resultCode, data)
        }
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        mApi = null
    }


}
