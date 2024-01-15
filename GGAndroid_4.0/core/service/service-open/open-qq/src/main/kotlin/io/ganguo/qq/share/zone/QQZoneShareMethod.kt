package io.ganguo.qq.share.zone

import androidx.fragment.app.FragmentActivity
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import io.ganguo.qq.AQQZoneShareMethod
import io.ganguo.qq.QQConstants
import io.ganguo.qq.entity.QQZoneShareEntity
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   :
 * </pre>
 */
class QQZoneShareMethod(appId: String) : AQQZoneShareMethod(), IUiListener {

    init {
        QQConstants.QQ_APP_ID = appId
    }

    /**
     * 创建服务提供者Provider
     * @param activity
     * @return [QQZoneShareProvider]
     */
    override fun newShareProvider(activity: FragmentActivity, shareParam: QQZoneShareEntity): QQZoneShareProvider {
        return QQZoneShareProvider(activity, shareParam, this)
    }

    /**
     * 分享完成
     * @param p0
     */
    override fun onComplete(p0: Any?) {
        sendResult(OpenResult(OpenChannel.QQ_ZONE, OpenStatus.SUCCESS))
    }

    /**
     * 分享取消
     * @param p0
     */
    override fun onCancel() {
        sendResult(OpenResult(OpenChannel.QQ_ZONE, OpenStatus.CANCEL))
    }

    /**
     * 分享出错
     * @param p0
     */
    override fun onError(p0: UiError?) {
        sendResult(OpenResult(OpenChannel.QQ_ZONE, OpenStatus.FAILED, message = p0?.errorMessage))
    }

}
