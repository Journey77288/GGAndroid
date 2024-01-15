package io.ganguo.sina.share

import android.app.Activity
import com.sina.weibo.sdk.share.WbShareHandler
import io.ganguo.sina.ISinaShareProvider
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 分享服务提供类
 * </pre>
 */
class SinaShareProvider(activity: Activity, var shareEntity: SinaShareEntity, var shareHandler: WbShareHandler?) : ISinaShareProvider {
    private var weak: WeakReference<Activity> = WeakReference(activity)

    override fun newService(): SinaShareService {
        return SinaShareService(weak.get()!!, shareEntity, shareHandler)
    }
}