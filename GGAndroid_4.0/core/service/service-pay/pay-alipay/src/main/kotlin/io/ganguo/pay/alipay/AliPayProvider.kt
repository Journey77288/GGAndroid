package io.ganguo.pay.alipay

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * Created by Roger on 05/07/2017.
 */

class AliPayProvider(activity: Activity, private val orderInfo: String) : IAliPayProvider {
    private var ref: WeakReference<Activity> = WeakReference(activity)

    override fun newService(): AliPayService {
        return AliPayService(ref.get()!!, orderInfo)
    }
}
