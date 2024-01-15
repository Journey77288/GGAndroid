package io.ganguo.facebook.helper

import android.app.Application
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import io.ganguo.facebook.FBOAuthResultObservable
import io.ganguo.facebook.FBShareResultObservable
import io.ganguo.facebook.auth.FacebookOAuthMethod
import io.ganguo.facebook.entity.FacebookShareEntity
import io.ganguo.facebook.share.FacebookShareMethod
import io.ganguo.factory.GGFactory

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/14
 *     desc   : Facebook分享/登录 帮助类
 * </pre>
 */
object FacebookOpenHelper {

    /**
     * 初始化Facebook分享
     *
     * @param context Application
     * @param appId String
     */
    fun initShare(context: Application, appId: String) {
        GGFactory.registerMethod(FacebookShareMethod(context, appId))
    }

    /**
     * 初始化Facebook登录
     *
     * @param context Application
     * @param appId String
     */
    fun initOauth(context: Application, appId: String) {
        GGFactory.registerMethod(FacebookOAuthMethod(context, appId))
    }

    /**
     * Facebook分享
     *
     * @param activity FragmentActivity
     * @param data FacebookShareEntity
     * @return FBShareResultObservable
     */
    fun share(activity: FragmentActivity, data: FacebookShareEntity): FBShareResultObservable {
        return GGFactory
            .getMethod<FacebookShareMethod>(FacebookShareMethod::class.java)
            .share(activity, data)
    }

    /**
     * Facebook登录
     *
     * @param activity FragmentActivity
     * @return FBOAuthResultObservable
     */
    fun oauth(activity: FragmentActivity): FBOAuthResultObservable {
        return GGFactory
            .getMethod<FacebookOAuthMethod>(FacebookOAuthMethod::class.java)
            .oauth(activity)
    }
}