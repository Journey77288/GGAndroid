package io.ganguo.qq.share.zone

import android.app.Activity
import com.tencent.tauth.IUiListener
import io.ganguo.qq.IQQZoneShareProvider
import io.ganguo.qq.entity.QQZoneShareEntity

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : QQ空间分享服务创建类
 * </pre>
 */
class QQZoneShareProvider(var activity: Activity, var shareEntity: QQZoneShareEntity, private var callback: IUiListener?) : IQQZoneShareProvider {
    override fun newService(): QQZoneShareService {
        return QQZoneShareService(activity, shareEntity, callback)
    }
}