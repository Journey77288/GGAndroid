package io.ganguo.pay.alipay

import android.app.Activity
import android.os.Message
import com.alipay.sdk.app.PayTask
import java.lang.ref.WeakReference

/**
 * Created by Roger on 05/07/2017.
 * 支付宝支付服务
 * @property  activity Activity引用
 * @property orderInfo 订单信息
 * @property payCallback 支付回调
 */
class AliPayService(activity: Activity, private var orderInfo: String?) : AAliPayService() {
    private var payResultHandler: PayResultHandler? = null
    private val ref: WeakReference<Activity> = WeakReference(activity)

    init {
        this.payResultHandler = PayResultHandler()
    }


    /**
     * 发起支付，为避免内存泄露，pay 方法在支付回调返回后会自动释放资源，因此不能多次调用。
     * @return [AliPayResultObservable]
     */
    override fun startService(): AliPayResultObservable {
        Thread {
            // 检查账户信息是否完整
            //                if (!payTask.checkAccountIfExist()) {
            //                    payResultHandler.sendMessage(msg);
            //                }
            // 构造 PayTask 对象
            val alipay = PayTask(ref.get())
            val result = alipay.payV2(orderInfo, true)
            val msg = Message()
            msg.what = 1
            msg.obj = result
            payResultHandler!!.sendMessage(msg)
        }.start()
        return resultObserver
    }


    /**
     * 支付SDK异常检测
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        //todo: do nothing，本地如果没有安装支付宝，则会调起SDK内置H5版本进行支付，一般不需要做异常判断处理
        return null
    }

    /**
     * 释放资源，避免内存泄露
     */
    @Synchronized
    override fun release() {
        if (isRelease) {
            return
        }
        payResultHandler = null
        orderInfo = null
        ref.clear()
        isRelease = true
    }

}
