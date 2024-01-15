package io.ganguo.sina.share

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.common.UiError
import com.sina.weibo.sdk.openapi.IWBAPI
import com.sina.weibo.sdk.openapi.WBAPIFactory
import com.sina.weibo.sdk.share.WbShareCallback
import io.ganguo.factory.result.IActivityResult
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.sina.ASinaShareMethod

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 新浪微博分享
 * </pre>
 */
class SinaShareMethod(context: Context, appKey: String, redirectUrl: String, sinaScope: String) : ASinaShareMethod(), IActivityResult {
    private var mApi: IWBAPI ?= null

    init {
        mApi = WBAPIFactory.createWBAPI(context)
        val authInfo = AuthInfo(context, appKey, redirectUrl, sinaScope)
        mApi?.registerApp(context, authInfo, null)
    }

    /**
     * 创建服务提供者Provider
     * @param activity
     * @param shareParam 微博分享数据
     */
    override fun newShareProvider(activity: FragmentActivity, shareParam: SinaShareEntity): SinaShareProvider {
        mApi = WBAPIFactory.createWBAPI(activity)
        return SinaShareProvider(activity, shareParam, mApi)
    }


    /**
     * 注册ActivityResult回调
     * @param requestCode 请求code
     * @param resultCode 返回值
     * @param data Intent
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity?) {
        if (mApi == null) {
            return
        }
        mApi?.doResultIntent(data, object : WbShareCallback {
            /**
             * 分享成功
             */
            override fun onComplete() {
                sendResult(OpenResult(OpenChannel.SINA, OpenStatus.SUCCESS))
            }

            /**
             * 分享失败
             * @param p0 UiError
             */
            override fun onError(p0: UiError?) {
                sendResult(OpenResult(OpenChannel.SINA, OpenStatus.FAILED, "sina share is fail!!"))
            }

            /**
             * 分享取消
             */
            override fun onCancel() {
                sendResult(OpenResult(OpenChannel.SINA, OpenStatus.CANCEL))
            }
        })
    }

    override fun release() {
        super.release()
        mApi = null;
    }
}
