package io.ganguo.qq.auth

import android.app.Activity
import com.tencent.tauth.IUiListener
import io.ganguo.qq.IQQAuthProvider
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ登录Provider
 * </pre>
 */
class QQOAuthProvider(activity: Activity, var appId: String, var callback: IUiListener?) : IQQAuthProvider {
    private var weakActivity: WeakReference<Activity> = WeakReference(activity)

    override fun newService(): QQOAuthService {
        return QQOAuthService(weakActivity.get()!!, appId, callback)
    }
}