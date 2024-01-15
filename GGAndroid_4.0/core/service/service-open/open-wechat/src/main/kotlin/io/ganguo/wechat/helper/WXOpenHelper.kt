package io.ganguo.wechat.helper

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.fragment.app.FragmentActivity
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import io.ganguo.factory.GGFactory
import io.ganguo.sample.sdk.ShareResult
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult
import io.ganguo.wechat.WXOAuthObservable
import io.ganguo.wechat.WXOAuthResult
import io.ganguo.wechat.auth.WXOAuthMethod
import io.ganguo.wechat.share.WXShareEntity
import io.ganguo.wechat.share.WXShareMethod
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/14
 *     desc   : 微信分享/登录 帮助类
 * </pre>
 */
object WXOpenHelper {
    private const val TENCENT_PACKAGE = "com.tencent.mm"
    private const val TENCENT_SHARE_UI = "com.tencent.mm.ui.tools.ShareImgUI"
    private const val VIDEO_TYPE = "video/mp4"

    /**
     * 初始化微信分享
     *
     * @param appId String
     */
    fun initShare(appId: String) {
        GGFactory.registerMethod(WXShareMethod(appId))
    }

    /**
     * 初始化微信登录
     *
     * @param appId String
     */
    fun initOauth(appId: String) {
        GGFactory.registerMethod(WXOAuthMethod(appId))
    }

    /**
     * 微信分享
     *
     * @param activity FragmentActivity
     * @param data WXShareEntity
     * @return Observable<ShareResult>
     */
    fun share(activity: FragmentActivity, data: WXShareEntity): Observable<ShareResult> {
        return GGFactory
            .getMethod<WXShareMethod>(WXShareMethod::class.java)
            .share(activity, data)
    }

    /**
     * 微信分享视频文件
     * 扩展微信分享sdk的方法
     *
     * @param activity Activity
     * @param videoUri Uri
     */
    fun shareVideoFile(activity: Activity, videoUri: Uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            activity.grantUriPermission(TENCENT_PACKAGE, videoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, videoUri)
        intent.type = VIDEO_TYPE
        intent.component = ComponentName(TENCENT_PACKAGE, TENCENT_SHARE_UI)
        activity.startActivity(intent)
    }

    /**
     * 微信登录
     *
     * @param activity FragmentActivity
     * @return WXOAuthObservable
     */
    fun oauth(activity: FragmentActivity): WXOAuthObservable {
        return GGFactory
            .getMethod<WXOAuthMethod>(WXOAuthMethod::class.java)
            .oauth(activity)
    }

    /**
     * 微信sdk结果回调
     */
    fun onResult(resp: BaseResp) {
        if (isWXOAuth(resp.type)) {
            GGFactory.asRxResultSend<WXOAuthResult>(WXOAuthMethod::class.java)
                ?.sendResult(newOpenResult(resp))
        } else if (isWXShare(resp.type)) {
            GGFactory.asRxResultSend<ShareResult>(WXShareMethod::class.java)
                ?.sendResult(newOpenResult(resp))
        }
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
     * 是否是微信分享
     * @param type 事件类型
     * @return [Boolean]
     */
    private fun isWXShare(type: Int): Boolean {
        return type == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX
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
}