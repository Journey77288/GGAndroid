package io.ganguo.qq.share.qq

import androidx.fragment.app.FragmentActivity
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import io.ganguo.qq.AQQShareMethod
import io.ganguo.qq.QQConstants
import io.ganguo.qq.entity.QQShareEntity
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ分享
 * </pre>
 */
class QQShareMethod(appId: String) : AQQShareMethod(), IUiListener {

    init {
        QQConstants.QQ_APP_ID = appId
        QQConstants.QQ_SDK_IS_INIT = QQConstants.QQ_APP_ID.isNotEmpty()
    }

    /**
     * 创建服务提供者Provider
     * @param activity
     * @return [QQShareProvider]
     */
    override fun newShareProvider(activity: FragmentActivity, shareParam: QQShareEntity): QQShareProvider {
        return QQShareProvider(activity, shareParam, this)
    }


    /**
     * 分享完成
     * @param p0
     */
    override fun onComplete(p0: Any?) {
        sendResult(OpenResult(OpenChannel.QQ, OpenStatus.SUCCESS))
    }

    /**
     * 分享取消
     * @param p0
     */
    override fun onCancel() {
        sendResult(OpenResult(OpenChannel.QQ, OpenStatus.CANCEL))
    }

    /**
     * 分享出错
     * @param p0
     */
    override fun onError(p0: UiError?) {
        sendResult(OpenResult(OpenChannel.QQ, OpenStatus.FAILED, message = p0?.errorMessage))
    }


}
