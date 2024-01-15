package io.ganguo.wechat.auth

import android.content.Context
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.ganguo.wechat.AWXOAuthService
import io.ganguo.wechat.WXConstants
import io.ganguo.wechat.WXHandler
import io.ganguo.wechat.WXOAuthObservable
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 微信登录
 * </pre>
 */
class WXOAuthService internal constructor(context: Context, appId: String) : AWXOAuthService() {
    private var weakContext: WeakReference<Context>? = WeakReference(context)
    private var iWXapi: IWXAPI? = null

    init {
        iWXapi = WXAPIFactory.createWXAPI(context, appId, true)
    }


    /**
     * 调起微信登录
     * @return WXOAuthObservable
     */
    override fun startService(): WXOAuthObservable {
        val req = SendAuth.Req()
        req.scope = WXConstants.WE_CHAT_SCOPE
        req.state = WXConstants.WE_CHAT_SDK_DEMO_TEST
        this.iWXapi!!.sendReq(req)
        return resultObserver
    }


    /**
     * SDK异常检测
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return WXHandler.checkException(weakContext?.get()!!)
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        weakContext?.clear()
        weakContext = null
        iWXapi?.detach()
        iWXapi = null
    }

}