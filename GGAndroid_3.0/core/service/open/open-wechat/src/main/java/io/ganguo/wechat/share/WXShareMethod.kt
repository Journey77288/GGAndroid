package io.ganguo.wechat.share

import android.app.Activity
import io.ganguo.wechat.AWXShareMethod
import io.ganguo.wechat.WXConstants
import io.ganguo.wechat.WXHandler

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 微信分享
 * </pre>
 */
open class WXShareMethod(appId: String) : AWXShareMethod() {


    init {
        WXConstants.WX_APP_ID = appId
        WXConstants.WX_SDK_IS_INIT = WXConstants.WX_APP_ID.isNotEmpty()
    }

    /**
     * WXShareService 创建方法
     * @param shareParam 微信分享数据
     * @return
     */
    override fun newShareProvider(activity: Activity, shareParam: WXShareEntity): WXShareProvider {
        return WXShareProvider(activity, WXConstants.WX_APP_ID, shareParam)
    }


}