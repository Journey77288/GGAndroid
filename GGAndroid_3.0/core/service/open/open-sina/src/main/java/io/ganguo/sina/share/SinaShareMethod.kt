package io.ganguo.sina.share

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.share.WbShareHandler
import io.ganguo.factory.result.IActivityResult
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.sina.ASinaShareMethod
import io.ganguo.sina.SinaHandler

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 新浪微博分享
 * </pre>
 */
class SinaShareMethod(context: Context, appKey: String, redirectUrl: String, sinaScope: String) : ASinaShareMethod(), IActivityResult, WbShareCallback {

    private var shareHandler: WbShareHandler? = null

    init {
        WbSdk.install(context, AuthInfo(context, appKey, redirectUrl, sinaScope))
    }

    /**
     * 创建服务提供者Provider
     * @param activity
     * @param shareParam 微博分享数据
     */
    override fun newShareProvider(activity: Activity, shareParam: SinaShareEntity): SinaShareProvider {
        shareHandler = WbShareHandler(activity)
        return SinaShareProvider(activity, shareParam, shareHandler)
    }

    /**
     * 微博分享失败
     */
    override fun onWbShareFail() {
        sendResult(OpenResult(OpenChannel.SINA, OpenStatus.FAILED, "sina share is fail!!"))
    }

    /**
     * 微博分享取消
     */
    override fun onWbShareCancel() {
        sendResult(OpenResult(OpenChannel.SINA, OpenStatus.CANCEL))
    }

    /**
     * 微博分享成功
     */
    override fun onWbShareSuccess() {
        sendResult(OpenResult(OpenChannel.SINA, OpenStatus.SUCCESS))
    }


    /**
     * 注册ActivityResult回调
     * @param requestCode 请求code
     * @param resultCode 返回值
     * @param data Intent
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (shareHandler == null) {
            return
        }
        shareHandler?.doResultIntent(data, this)
    }

    override fun release() {
        super.release()
        shareHandler = null
    }


}