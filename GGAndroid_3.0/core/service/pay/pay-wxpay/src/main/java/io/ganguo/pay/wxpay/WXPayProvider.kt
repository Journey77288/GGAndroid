package io.ganguo.pay.wxpay

import android.app.Activity
import io.ganguo.pay.wxpay.entity.WXPayEntity
import java.lang.ref.WeakReference

/**
 * Created by Roger on 06/07/2017.
 */
class WXPayProvider(activity: Activity, private val wxPayEntity: WXPayEntity) : IWXPayProvider {
    private val ref: WeakReference<Activity> = WeakReference(activity)

    override fun newService(): WXPayService {
        return WXPayService(ref.get(), wxPayEntity)
    }
}
