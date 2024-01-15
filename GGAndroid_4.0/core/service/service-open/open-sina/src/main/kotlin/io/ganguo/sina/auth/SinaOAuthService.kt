package io.ganguo.sina.auth

import android.app.Activity
import android.content.Context
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.openapi.IWBAPI
import io.ganguo.sina.ASinaOAuthService
import io.ganguo.sina.SinaHandler
import io.ganguo.sina.SinaOAuthObservable
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 新浪微博登录服务
 * </pre>
 */
class SinaOAuthService(private val context: Context, ssoHandler: IWBAPI?, private var callBack: WbAuthListener?) : ASinaOAuthService() {
    private var weakSsoHandler: WeakReference<IWBAPI?> = WeakReference<IWBAPI?>(ssoHandler)
    private var weakContext: WeakReference<Context> = WeakReference(context)


    /**
     * 启动新浪微博登录服务
     * @return SinaOAuthObservable
     */
    override fun startService(): SinaOAuthObservable {
        weakSsoHandler.get()?.authorize(context as Activity?, callBack)
        return resultObserver
    }


    /**
     * 检测SDK或者App是否有异常
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return SinaHandler.checkException(context = weakContext.get()!!)
    }


    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        weakSsoHandler.clear()
        weakContext.clear()
        callBack = null
    }

}