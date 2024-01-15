package io.ganguo.twitter.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.twitter.sdk.android.core.*
import io.ganguo.factory.result.IActivityResult
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.twitter.ATwitterOAthMethod
import io.ganguo.twitter.TwitterConstants

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : 推特登录
 * </pre>
 */
class TwitterOAuthMethod(context: Context, consumerKey: String, consumerSecret: String, isDebug: Boolean) : ATwitterOAthMethod(), IActivityResult {

    init {
        initTwitter(context, consumerKey, consumerSecret, isDebug)
    }


    /**
     * Twitter SDK init
     *
     * @param context 上下文
     * @param consumerKey app key
     * @param consumerSecret app key secret
     * @param isDebug 是否是debug模式
     */
    private fun initTwitter(context: Context, consumerKey: String, consumerSecret: String, isDebug: Boolean) {
        val config = TwitterConfig.Builder(context)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(consumerKey, consumerSecret))//手动使用代码添加key,secret.
                .debug(isDebug)//打开或者关闭debug模式
                .build()
        Twitter.initialize(config)
        TwitterConstants.TWITTER_SDK_IS_INIT = true
    }


    /**
     * TwitterOAuthService 创建方法
     * @param activity activity引用
     * @return
     */
    override fun newOAuthProvider(activity: FragmentActivity, p: Any?): TwitterOAuthProvider {
        return TwitterOAuthProvider(activity, newOAuthCallback())
    }

    /**
     * 创建登录认证回调
     * @return
     */
    private fun newOAuthCallback(): Callback<TwitterSession> {
        return object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                sendResult(OpenResult(OpenChannel.TWITTER, OpenStatus.SUCCESS, result = result?.data))
            }

            override fun failure(exception: TwitterException?) {
                sendResult(OpenResult(OpenChannel.TWITTER, OpenStatus.FAILED, exception?.message))
            }
        }
    }


    /**
     * 注册登录结果Activity回调
     * @param requestCode 请求码
     * @param resultCode 返回码
     * @param data 返回结果
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity?) {
        if (requestCode != TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
            return
        }
        service.authClient?.onActivityResult(requestCode, resultCode, data)
    }


}
