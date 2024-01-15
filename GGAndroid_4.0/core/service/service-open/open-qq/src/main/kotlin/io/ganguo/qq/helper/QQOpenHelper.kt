package io.ganguo.qq.helper

import androidx.fragment.app.FragmentActivity
import io.ganguo.factory.GGFactory
import io.ganguo.qq.QQOAuthObservable
import io.ganguo.qq.auth.QQOAuthMethod
import io.ganguo.qq.entity.QQShareEntity
import io.ganguo.qq.entity.QQZoneShareEntity
import io.ganguo.qq.share.qq.QQShareMethod
import io.ganguo.qq.share.zone.QQZoneShareMethod
import io.ganguo.sample.sdk.ShareResult
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/14
 *     desc   : QQ分享/登录 帮助类
 * </pre>
 */
object QQOpenHelper {

    /**
     * 初始化QQ分享
     *
     * @param appId String
     */
    fun initShare(appId: String) {
        GGFactory.registerMethod(QQShareMethod(appId))
        GGFactory.registerMethod(QQZoneShareMethod(appId))
    }

    /**
     * 初始化QQ登录
     *
     * @param appId String
     */
    fun initOauth(appId: String) {
        GGFactory.registerMethod(QQOAuthMethod(appId))
    }

    /**
     * 分享给好友
     *
     * @param activity FragmentActivity
     * @param data QQShareEntity
     * @return Observable<ShareResult>
     */
    fun shareFriend(activity: FragmentActivity, data: QQShareEntity): Observable<ShareResult> {
        return GGFactory
            .getMethod<QQShareMethod>(QQShareMethod::class.java)
            .share(activity, data)
    }

    /**
     * 分享到朋友圈
     *
     * @param activity FragmentActivity
     * @param data QQZoneShareEntity
     * @return Observable<ShareResult>
     */
    fun shareZone(activity: FragmentActivity, data: QQZoneShareEntity): Observable<ShareResult> {
        return GGFactory
            .getMethod<QQZoneShareMethod>(QQZoneShareMethod::class.java)
            .share(activity, data)
    }

    /**
     * QQ登录
     *
     * @param activity FragmentActivity
     * @return QQOAuthObservable
     */
    fun oauth(activity: FragmentActivity): QQOAuthObservable {
        return GGFactory
            .getMethod<QQOAuthMethod>(QQOAuthMethod::class.java)
            .oauth(activity)
    }
}