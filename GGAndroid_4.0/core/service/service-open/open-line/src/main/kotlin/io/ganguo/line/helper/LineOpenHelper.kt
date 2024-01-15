package io.ganguo.line.helper

import androidx.fragment.app.FragmentActivity
import io.ganguo.factory.GGFactory
import io.ganguo.line.LineOAuthObservable
import io.ganguo.line.auth.LineOAuthMethod
import io.ganguo.line.share.LineShareEntity
import io.ganguo.line.share.LineShareMethod
import io.ganguo.sample.sdk.ShareResultObservable

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/15
 *     desc   : Line分享/登录 帮助类
 * </pre>
 */
object LineOpenHelper {

    /**
     * 初始化Line分享
     */
    fun initShare() {
        GGFactory.registerMethod(LineShareMethod())
    }

    /**
     * 初始化Line分享
     *
     * @param channelId String
     */
    fun initOauth(channelId: String) {
        GGFactory.registerMethod(LineOAuthMethod(channelId))
    }

    /**
     * Line分享
     *
     * @param activity FragmentActivity
     * @param data LineShareEntity
     * @return ShareResultObservable
     */
    fun share(activity: FragmentActivity, data: LineShareEntity): ShareResultObservable {
        return GGFactory
            .getMethod<LineShareMethod>(LineShareMethod::class.java)
            .share(activity, data)
    }

    /**
     * Line登录
     *
     * @param activity FragmentActivity
     * @return LineOAuthObservable
     */
    fun oauth(activity: FragmentActivity): LineOAuthObservable {
        return GGFactory
            .getMethod<LineOAuthMethod>(LineOAuthMethod::class.java)
            .oauth(activity)
    }
}