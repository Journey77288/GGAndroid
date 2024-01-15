package io.ganguo.pay.wxpay

import android.app.Activity
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.ganguo.pay.core.annotation.PayType
import io.ganguo.pay.core.exception.PayServiceException
import io.ganguo.pay.wxpay.entity.WXPayEntity
import java.lang.ref.WeakReference

/**
 * Created by Roger on 06/07/2017.
 * 微信支付服务
 * @property wxPayEntity 微信支付订单数据
 */
class WXPayService(activity: Activity?, private var wxPayEntity: WXPayEntity?) : AWXPayService() {
    private val ref: WeakReference<Activity>
    private val wxApi: IWXAPI

    init {
        if (activity == null) {
            throw NullPointerException("activity can not be null")
        }
        this.ref = WeakReference(activity)
        wxApi = WXAPIFactory.createWXAPI(ref.get(), WXConstants.WX_APP_ID)
    }

    /**
     * 发起支付，为避免内存泄露，pay 方法在支付回调返回后会自动释放资源，因此不能多次调用。
     * @return
     */
    override fun startService(): WXPayResultObservable {
        if (ref.get() == null) {
            return resultObserver
        }
        // 检查是否安装微信
        if (!wxApi.isWXAppInstalled) {
            wxApi.detach()
            return resultObserver
        }
        wxApi.registerApp(WXConstants.WX_APP_ID)
        wxApi.sendReq(wxPayEntity!!.toPayReq())
        wxApi.detach()
        return resultObserver
    }

    /**
     * 检测微信支付SDK配置是否正确
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        var payServiceException: PayServiceException? = null
        //appId为空时，抛异常
        if (!WXConstants.WX_SDK_IS_INIT) {
            val msg = "Please initialize WeChat sdk in Application!!!"
            val errorCode = WXConstants.ErrorCode.WX_PAY_SDK_NOT_INIT
            payServiceException = PayServiceException(PayType.WX_PAY, errorCode, msg)
        }
        //app未安装时，抛运行时异常
        else if (!wxApi.isWXAppInstalled) {
            val msg = "Please install the weChat first!!!"
            val errorCode = WXConstants.ErrorCode.WX_PAY_APP_NOT_INSTALLED
            payServiceException = PayServiceException(PayType.WX_PAY, errorCode, msg)
        }
        return payServiceException
    }


    /**
     * 资源释放。
     */
    override fun release() {
        super.release()
        wxPayEntity = null
        ref.clear()
    }


}
