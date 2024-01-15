package io.ganguo.wechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.ganguo.factory.GGFactory
import io.ganguo.open.sdk.ShareResult
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.wechat.auth.WXOAuthMethod
import io.ganguo.wechat.share.WXShareMethod


/**
 * <pre>
 * author : zoyen
 * time   : 2018/7/10
 * desc   : 微信分享、认证回调Activity
 */
open class WeChatActivity : Activity(), IWXAPIEventHandler {
    private var iwxapi: IWXAPI? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iwxapi = WXAPIFactory.createWXAPI(this, WXConstants.WX_APP_ID)
        val handled = iwxapi!!.handleIntent(intent, this)
        if (!handled) {
            finish()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (iwxapi != null) {
            iwxapi!!.handleIntent(intent, this)
        }
    }

    override fun onReq(baseReq: BaseReq) {

    }

    /**
     * 微信sdk结果回调函数
     */
    override fun onResp(resp: BaseResp) {
        Log.d("onResp", "WeChat Open Sdk onResp errCode==${resp.errCode},type==${resp.type}")
        if (isWXOAuth(resp.type)) {
            GGFactory.asRxResultSend<WXOAuthResult>(WXOAuthMethod::class.java)
                    ?.sendResult(newOpenResult(resp))
        } else if (isWXShare(resp.type)) {
            GGFactory.asRxResultSend<ShareResult>(WXShareMethod::class.java)
                    ?.sendResult(newOpenResult(resp))
        }
        finish()
    }


    /**
     * 是否是微信分享
     * @param type 事件类型
     * @return [Boolean]
     */
    private fun isWXShare(type: Int): Boolean {
        return type == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX
    }

    /**
     * 是否是微信登录
     * @param type 事件类型
     * @return [Boolean]
     */
    private fun isWXOAuth(type: Int): Boolean {
        return type == ConstantsAPI.COMMAND_SENDAUTH
    }


    /**
     * 根据微信SDK结果，封装[OpenResult]返回
     * @param resp 微信sdk回调结果
     * @return [OpenResult]
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T> newOpenResult(resp: BaseResp): OpenResult<T> {
        var result = OpenResult<T>(OpenChannel.WE_CHAT, asOpenStatus(resp.errCode))
        if (isWXOAuth(resp.type)) {
            result.result = (resp as SendAuth.Resp).code as T
        }
        if (result.status == OpenStatus.FAILED) {
            result.message = "WeChat Open Sdk handler failed!!! errCode==${resp.errCode}"
        }
        return result
    }

    /**
     * 判断回调状态
     * @return [Int]
     */
    @OpenStatus
    private fun asOpenStatus(code: Int): Int {
        return when (code) {
            BaseResp.ErrCode.ERR_OK -> OpenStatus.SUCCESS
            BaseResp.ErrCode.ERR_USER_CANCEL -> OpenStatus.CANCEL
            else -> OpenStatus.FAILED
        }
    }

    override fun onDestroy() {
        iwxapi = null
        super.onDestroy()
    }


}
