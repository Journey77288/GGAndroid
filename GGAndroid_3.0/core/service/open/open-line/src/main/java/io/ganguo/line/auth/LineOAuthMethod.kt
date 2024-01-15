package io.ganguo.line.auth

import android.app.Activity
import android.content.Intent
import com.linecorp.linesdk.LineApiResponseCode
import com.linecorp.linesdk.auth.LineLoginApi
import io.ganguo.factory.result.IActivityResult
import io.ganguo.line.ALineOAuthMethod
import io.ganguo.line.LineConstants
import io.ganguo.line.LineHandler
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : line登录方法
 * </pre>
 */
class LineOAuthMethod(appChannelId: String) : ALineOAuthMethod(), IActivityResult {

    init {
        LineConstants.LINE_CHANNEL_ID = appChannelId
    }


    /**
     * 创建服务提供者Provider
     * @param activity
     * @return [LineOAuthProvider]
     */
    override fun newOAuthProvider(activity: Activity, p: Any?): LineOAuthProvider {
        return LineOAuthProvider(activity, LineConstants.LINE_CHANNEL_ID)
    }

    /**
     * 注册ActivityResult回调
     * @param requestCode 请求码
     * @param resultCode 返回码
     * @param data 数据
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != LineConstants.LINE_LOGIN_REQUEST) {
            return
        }
        val result = LineLoginApi.getLoginResultFromIntent(data)
        when (result.responseCode) {
            LineApiResponseCode.SUCCESS -> {
                sendResult(OpenResult(OpenChannel.LINE, OpenStatus.SUCCESS, result = result))
            }
            LineApiResponseCode.CANCEL -> {
                sendResult(OpenResult(OpenChannel.LINE, OpenStatus.CANCEL))
            }
            else -> {
                sendResult(OpenResult(OpenChannel.LINE, OpenStatus.FAILED, message = result.errorData.message))
            }
        }
    }



}