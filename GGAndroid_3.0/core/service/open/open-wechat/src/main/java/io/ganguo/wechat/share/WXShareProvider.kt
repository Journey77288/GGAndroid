package io.ganguo.wechat.share

import android.app.Activity
import io.ganguo.wechat.IWXShareProvider
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 微信分享服务
 * </pre>
 */
class WXShareProvider(activity: Activity, var appId: String, var shareData: WXShareEntity) : IWXShareProvider {
    private val weakActivity: WeakReference<Activity> = WeakReference(activity)

    override fun newService(): WXShareService {
        return WXShareService(weakActivity.get()!!, appId, shareData)
    }
}