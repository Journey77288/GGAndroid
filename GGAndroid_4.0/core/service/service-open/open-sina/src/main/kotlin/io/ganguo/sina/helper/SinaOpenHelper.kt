package io.ganguo.sina.helper

import android.app.Application
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import io.ganguo.factory.GGFactory
import io.ganguo.sample.sdk.ShareResult
import io.ganguo.sina.SinaOAuthObservable
import io.ganguo.sina.auth.SinaOAuthMethod
import io.ganguo.sina.share.SinaShareEntity
import io.ganguo.sina.share.SinaShareMethod
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/14
 *     desc   : 新浪微博分享/登录 帮助类
 * </pre>
 */
object SinaOpenHelper {
    /**
     * 初始化新浪微博分享
     *
     * @param context Application
     * @param appKey String
     * @param redirectUrl String
     * @param scope String
     */
    fun initShare(context: Application, appKey: String, redirectUrl: String, scope: String) {
        GGFactory.registerMethod(SinaShareMethod(context, appKey, redirectUrl, scope))
    }

    /**
     * 初始化新浪微博登录
     *
     * @param context Application
     * @param appKey String
     * @param redirectUrl String
     * @param scope String
     */
    fun initOauth(context: Application, appKey: String, redirectUrl: String, scope: String) {
        GGFactory.registerMethod(SinaOAuthMethod(context, appKey, redirectUrl, scope))
    }

    /**
     * 新浪微博分享
     *
     * @param activity FragmentActivity
     * @param data SinaShareEntity
     * @return Observable<ShareResult>
     */
    fun share(activity: FragmentActivity, data: SinaShareEntity): Observable<ShareResult> {
        return GGFactory
            .getMethod<SinaShareMethod>(SinaShareMethod::class.java)
            .share(activity, data)
    }

    /**
     * 新浪微博登录
     *
     * @param activity FragmentActivity
     * @return SinaOAuthObservable
     */
    fun oauth(activity: FragmentActivity): SinaOAuthObservable {
        return GGFactory
            .getMethod<SinaOAuthMethod>(SinaOAuthMethod::class.java)
            .oauth(activity)
    }
}