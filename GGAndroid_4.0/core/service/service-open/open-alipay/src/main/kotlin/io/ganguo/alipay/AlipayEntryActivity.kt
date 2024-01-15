package io.ganguo.alipay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alipay.share.sdk.openapi.*
import io.ganguo.alipay.helper.AlipayOpenHelper
import io.ganguo.alipay.share.AliPayShareMethod


/**
 * <pre>
 *     author : demo 写法
 *     time   : 2019/10/30
 *     desc   : 支付宝分享服务
 * </pre>
 * 当使用[AliPayShareMethod]的时候, 要获得支付宝的回调，要在app module下新建一个apshare package
 * @property aliPayAPI 支付宝回调处理类
 */
open class AlipayEntryActivity : Activity(), IAPAPIEventHandler {
    private var aliPayAPI: IAPApi = APAPIFactory.createZFBApi(application, AliPayConstants.ALI_PAY_APP_ID, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aliPayAPI.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        aliPayAPI.handleIntent(intent, this)
    }

    override fun onReq(baseReq: BaseReq) {

    }

    /**
     * 分享结果回调信息处理
     * @param baseResp 分享结果数据
     */
    override fun onResp(baseResp: BaseResp) {
        AlipayOpenHelper.onResult(baseResp)
        finish()
    }
}

