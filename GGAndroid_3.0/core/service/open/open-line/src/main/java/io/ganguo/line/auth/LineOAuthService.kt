package io.ganguo.line.auth

import android.app.Activity
import com.linecorp.linesdk.auth.LineLoginApi
import io.ganguo.line.ALineOAuthService
import io.ganguo.line.LineConstants
import io.ganguo.line.LineHandler
import io.ganguo.line.LineOAuthObservable
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : line登录服务
 * </pre>
 */
class LineOAuthService(activity: Activity, var appChannelId: String) : ALineOAuthService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)



    /**
     * 检测是否有SDK或App是否有异常
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return LineHandler.checkException(weakActivity?.get()!!)
    }

    /**
     * 启动 line 登录
     * @return LineOAuthObservable
     */
    override fun startService(): LineOAuthObservable {
        val intent = LineLoginApi.getLoginIntent(weakActivity!!.get()!!, appChannelId)
        weakActivity!!.get()!!.startActivityForResult(intent, LineConstants.LINE_LOGIN_REQUEST)
        return resultObserver
    }


    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
    }
}