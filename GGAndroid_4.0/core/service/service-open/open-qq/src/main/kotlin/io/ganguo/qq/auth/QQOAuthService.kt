package io.ganguo.qq.auth

import android.app.Activity
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import io.ganguo.qq.AQQOAuthService
import io.ganguo.qq.QQConstants
import io.ganguo.qq.QQHandler
import io.ganguo.qq.QQOAuthObservable
import io.reactivex.rxjava3.core.Observable
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ登录服务
 * </pre>
 */
class QQOAuthService(activity: Activity, appId: String, var callback: IUiListener?) : AQQOAuthService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)
    private var tencent: Tencent?

    init {
        tencent = Tencent.createInstance(appId, weakActivity!!.get())
    }


    /**
     * 登录
     * @return [Observable]
     */
    override fun startService(): QQOAuthObservable {
        if (!tencent!!.isSessionValid) {
            tencent?.login(weakActivity!!.get(), QQConstants.ALL, callback)
        }
        return resultObserver
    }


    /**
     * QQ SDK异常检测
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return QQHandler.checkException(weakActivity?.get()!!)
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        tencent?.releaseResource()
        weakActivity?.clear()
        weakActivity = null
        tencent = null
        callback = null
    }

}