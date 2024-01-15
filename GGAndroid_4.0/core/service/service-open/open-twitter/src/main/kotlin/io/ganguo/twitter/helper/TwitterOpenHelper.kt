package io.ganguo.twitter.helper

import android.app.Application
import androidx.fragment.app.FragmentActivity
import io.ganguo.factory.GGFactory
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.twitter.TwitterOAuthObservable
import io.ganguo.twitter.auth.TwitterOAuthMethod
import io.ganguo.twitter.entity.TwitterShareEntity
import io.ganguo.twitter.share.TwitterShareMethod

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/15
 *     desc   : 推特分享/登录 帮助类
 * </pre>
 */
object TwitterOpenHelper {

    /**
     * 初始化推特分享
     */
    fun initShare() {
        GGFactory.registerMethod(TwitterShareMethod())
    }

    /**
     * 初始化推特登录
     */
    fun initOauth(context: Application, apiKey: String, apiSecret: String, isDebug: Boolean) {
        GGFactory.registerMethod(TwitterOAuthMethod(context, apiKey, apiSecret, isDebug))
    }

    /**
     * 推特分享
     * 
     * @param activity FragmentActivity
     * @param data TwitterShareEntity
     * @return ShareResultObservable
     */
    fun share(activity: FragmentActivity, data: TwitterShareEntity): ShareResultObservable {
        return GGFactory
            .getMethod<TwitterShareMethod>(TwitterShareMethod::class.java)
            .share(activity, data)
    }

    /**
     * 推特登录
     *
     * @param activity FragmentActivity
     * @return TwitterOAuthObservable
     */
    fun oauth(activity: FragmentActivity): TwitterOAuthObservable {
        return GGFactory
            .getMethod<TwitterOAuthMethod>(TwitterOAuthMethod::class.java)
            .oauth(activity)
    }
}