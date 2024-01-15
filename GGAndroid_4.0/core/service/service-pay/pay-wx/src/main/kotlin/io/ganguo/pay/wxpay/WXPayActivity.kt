package io.ganguo.pay.wxpay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.ganguo.pay.wxpay.helper.WXPayHelper

/**
 * Created by Roger on 06/07/2017.
 * 微信支付回调处理
 */
open class WXPayActivity : Activity(), IWXAPIEventHandler {
    private var api: IWXAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this, WXConstants.WX_APP_ID)
        val handled = api!!.handleIntent(intent, this)
        if (!handled) {
            finish()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (api != null) {
            api!!.handleIntent(intent, this)
        }
    }

    override fun onReq(baseReq: BaseReq) {}


    /**
     * 微信支付回调结果处理
     * @param baseResp
     */
    override fun onResp(baseResp: BaseResp) {
        WXPayHelper.onResult(baseResp)
        finish()
    }
}
