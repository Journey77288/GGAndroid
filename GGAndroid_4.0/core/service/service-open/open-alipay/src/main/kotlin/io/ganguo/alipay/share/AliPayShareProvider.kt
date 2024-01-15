package io.ganguo.alipay.share

import android.app.Activity
import io.ganguo.alipay.IAliPayShareProvider
import io.ganguo.alipay.entity.AliPayShareEntity
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝支付服务创建类
 * </pre>
 */
class AliPayShareProvider(activity: Activity, var appId: String, var shareEntity: AliPayShareEntity) : IAliPayShareProvider {
    private var weakActivity = WeakReference<Activity>(activity)

    /**
     * 创建支付服务
     * @return
     */
    override fun newService(): AliPayShareService {
        return AliPayShareService(weakActivity.get()!!, appId, shareEntity)
    }
}