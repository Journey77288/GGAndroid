package io.ganguo.qq.share.qq

import android.app.Activity
import com.tencent.tauth.IUiListener
import io.ganguo.qq.IQQShareProvider
import io.ganguo.qq.entity.QQShareEntity
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ分享服务Provider
 * </pre>
 */
class QQShareProvider(activity: Activity, var data: QQShareEntity, var callback: IUiListener?) : IQQShareProvider {
    private var weakActivity: WeakReference<Activity> = WeakReference(activity)

    override fun newService(): QQShareService {
        return QQShareService(weakActivity.get()!!, data, callback)
    }
}