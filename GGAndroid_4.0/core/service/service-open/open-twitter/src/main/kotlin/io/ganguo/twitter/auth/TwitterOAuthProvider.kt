package io.ganguo.twitter.auth

import android.app.Activity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.TwitterSession
import io.ganguo.twitter.ITwitterOAthProvider

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Twitter登录服务创建类
 * </pre>
 */
class TwitterOAuthProvider(var activity: Activity, var callback: Callback<TwitterSession>) : ITwitterOAthProvider {

    override fun newService(): TwitterOAuthService {
        return TwitterOAuthService(activity, callback)
    }

}