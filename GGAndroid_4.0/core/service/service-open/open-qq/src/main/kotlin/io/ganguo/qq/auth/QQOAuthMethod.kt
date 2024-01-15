package io.ganguo.qq.auth

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import io.ganguo.factory.result.IActivityResult
import io.ganguo.qq.AQQAuthMethod
import io.ganguo.qq.QQConstants
import io.ganguo.qq.QQHandler
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ登录
 * </pre>
 */
class QQOAuthMethod(appId: String) : AQQAuthMethod(), IActivityResult, IUiListener {

    init {
        QQConstants.QQ_APP_ID = appId
        QQConstants.QQ_SDK_IS_INIT = QQConstants.QQ_APP_ID.isNotEmpty()
    }

    /**
     * 创建服务提供者Provider
     * @param activity
     * @return [QQOAuthProvider]
     */
    override fun newOAuthProvider(activity: FragmentActivity, p: Any?): QQOAuthProvider {
        return QQOAuthProvider(activity, QQConstants.QQ_APP_ID, this)
    }


    /**
     * QQ登录 成功
     * @param p0
     */
    override fun onComplete(p0: Any?) {
        val userEntity = QQHandler.asQQUserEntity(p0)
        if (userEntity == null) {
            var message = "${this.javaClass.simpleName} QQ User data is empty!!!"
            sendResult(OpenResult(OpenChannel.QQ, OpenStatus.FAILED, message))
        } else {
            sendResult(OpenResult(OpenChannel.QQ, OpenStatus.SUCCESS, result = userEntity))
        }
    }


    /**
     * QQ登录 取消
     * @return
     */
    override fun onCancel() {
        sendResult(OpenResult(OpenChannel.QQ, OpenStatus.CANCEL))
    }

    /**
     * QQ登录 失败
     * @param p0
     */
    override fun onError(p0: UiError?) {
        sendResult(OpenResult(OpenChannel.QQ, OpenStatus.FAILED, message = p0?.errorMessage))
    }

    /**
     * 注册ActivityResult回调 （必须在对应Activity中调用，否则不会有回调事件）
     * @param requestCode 请求code
     * @param resultCode 返回值
     */
    override fun registerActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity?) {
        Tencent.onActivityResultData(requestCode, resultCode, data, null)
    }

}
