package io.ganguo.sina.auth

import android.app.Activity
import android.content.Context
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.openapi.IWBAPI
import io.ganguo.sina.ISinaOAuthProvider
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 新浪微博服务生产类
 * </pre>
 */
class SinaOAuthProvider(activity: Activity, var api: IWBAPI?, var callBack: WbAuthListener?) : ISinaOAuthProvider {
    private var weakContext: WeakReference<Context> = WeakReference(activity)

    override fun newService(): SinaOAuthService {
        return SinaOAuthService(weakContext.get()!!, api, callBack)
    }
}