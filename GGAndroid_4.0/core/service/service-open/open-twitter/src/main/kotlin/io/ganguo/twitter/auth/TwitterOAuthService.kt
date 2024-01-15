package io.ganguo.twitter.auth

import android.app.Activity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import io.ganguo.twitter.ATwitterOAthService
import io.ganguo.twitter.TwitterHandler
import io.ganguo.twitter.TwitterOAuthObservable
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Twitter登录服务
 * </pre>
 */
class TwitterOAuthService(activity: Activity, private var callback: Callback<TwitterSession>) : ATwitterOAthService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)
    var authClient: TwitterAuthClient? = TwitterAuthClient()


    /**
     * Twitter登录
     * @return TwitterOAuthObserver
     */
    override fun startService(): TwitterOAuthObservable {
        authClient?.authorize(weakActivity?.get(), callback)
        return resultObserver
    }

    /**
     * 检测推特SDK配置是否正常
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return TwitterHandler.checkException()
    }


    /**
     * 释放资源
     */
    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
    }

}