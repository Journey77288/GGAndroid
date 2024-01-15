package io.ganguo.wechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.ganguo.wechat.helper.WXOpenHelper


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
        WXOpenHelper.onResult(resp)
        finish()
    }

    override fun onDestroy() {
        iwxapi = null
        super.onDestroy()
    }


}
