package io.ganguo.google.helper

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import io.ganguo.factory.GGFactory
import io.ganguo.google.GoogleOAuthObservable
import io.ganguo.google.auth.GoogleOAuthMethod
import io.ganguo.sample.sdk.ShareResultObservable

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/15
 *     desc   : 谷歌分享/登录 帮助类
 * </pre>
 */
object GoogleOpenHelper {

    /**
     * 初始化谷歌登录
     *
     * @param idToken String
     */
    fun initOauth(idToken: String) {
        GGFactory.registerMethod(GoogleOAuthMethod(idToken))
    }

    /**
     * 谷歌登录
     *
     * @return GoogleOAuthMethod
     * @return GoogleOAuthObservable
     */
    fun oauth(activity: FragmentActivity): GoogleOAuthObservable {
        return GGFactory
            .getMethod<GoogleOAuthMethod>(GoogleOAuthMethod::class.java)
            .oauth(activity)
    }
}