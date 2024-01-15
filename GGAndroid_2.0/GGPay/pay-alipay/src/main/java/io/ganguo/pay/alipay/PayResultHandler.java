package io.ganguo.pay.alipay;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

import io.ganguo.pay.core.GGPay;
import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.PayCallback;
import io.ganguo.pay.core.PayResult;

/**
 * Created by Roger on 05/07/17.
 */
final class PayResultHandler extends Handler {
    private PayCallback mCallback;
    private WeakReference<IPayService> ref;

    PayResultHandler(PayCallback payCallback, IPayService IPayService) {
        this.mCallback = payCallback;
        this.ref = new WeakReference<IPayService>(IPayService);
    }

    /**
     * 处理支付结果回调
     */
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case AlipayService.PAY_FLAG:
                PayResult payResult = new PayResult();
                @SuppressWarnings("unchecked")
                PayData aliPayData = new PayData(msg.obj.toString());
                String resultStatus = aliPayData.getResultStatus();
                payResult.type = "alipay";
                payResult.result = aliPayData.getResult();
                payResult.code = resultStatus;
                payResult.message = aliPayData.getMemo();
                Log.e("TAG", "handleMessage:payStatus: " + resultStatus);
                Log.e("TAG", "handleMessage: " + msg.obj.toString());
                if (mCallback == null) {
                    final IPayService service = ref.get();
                    if (service != null) {
                        GGPay.remove(service);
                    }
                    return;
                }
                if (isEquals(resultStatus, AlipayService.PAY_OK)) {
                    mCallback.onPaySuccess(payResult);
                } else if (isEquals(resultStatus, AlipayService.PAY_CANCEL)) {
                    mCallback.onPayCancel(payResult);
                } else {
                    mCallback.onPayError(payResult);
                }
                mCallback = null;
                final IPayService service = ref.get();
                if (service != null) {
                    GGPay.remove(service);
                }
        }
    }

    private boolean isEquals(String s, String target) {
        return s != null && target != null && s.equals(target);
    }

    void release() {
        mCallback = null;
    }
}
