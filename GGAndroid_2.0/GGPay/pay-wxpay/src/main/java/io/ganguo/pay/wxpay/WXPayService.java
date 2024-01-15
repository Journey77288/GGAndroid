package io.ganguo.pay.wxpay;

import android.app.Activity;
import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.ref.WeakReference;

import io.ganguo.pay.core.GGPay;
import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.PayCallback;
import io.ganguo.pay.core.PayResult;

/**
 * Created by Roger on 06/07/2017.
 */

final class WXPayService implements IPayService {
    private static final String TAG = "WXPayService";

    private WXPayData wxPayData;
    private PayCallback payCallback;
    private WeakReference<Activity> ref;
    private Application.ActivityLifecycleCallbacks lifecycleCallbacks;
    private boolean isReleased = false;

    WXPayService(Activity activity, PayCallback payCallback, WXPayData wxPayData) {
        if (payCallback == null) {
            throw new NullPointerException("pay callback can not be null");
        }
        if (activity == null) {
            throw new NullPointerException("activity can not be null");
        }

        this.wxPayData = wxPayData;
        this.payCallback = payCallback;
        this.ref = new WeakReference<Activity>(activity);
        lifecycleCallbacks = createLifecycleCallbacks();
        activity.getApplication().registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    private Application.ActivityLifecycleCallbacks createLifecycleCallbacks() {
        return new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
//                Log.i(TAG, "destroyed activity name: " + activity.getClass().getSimpleName());

                if (!isReleased && activity instanceof IWXAPIEventHandler) {
                    String resultCode = activity.getIntent().getStringExtra(WXConstants.WXPAY_RESULT);
                    PayResult payResult = activity.getIntent().getParcelableExtra(WXConstants.WXPAY_EXTRA);
                    if (isEquals(resultCode, WXConstants.CODE_SUCCESS)) {
                        payCallback.onPaySuccess(payResult);
                    } else if (isEquals(resultCode, WXConstants.CODE_FAIL)) {
                        payCallback.onPayError(payResult);
                    } else if (isEquals(resultCode, WXConstants.CODE_CANCEL)) {
                        payCallback.onPayCancel(payResult);
                    } else if (isEquals(resultCode, WXConstants.CODE_NO_RESPONSE)) {
                        payCallback.onPayError(payResult);
                    }
                }
                GGPay.remove(WXPayService.this);
            }
        };
    }

    /**
     * 发起支付，为避免内存泄露，pay 方法在支付回调返回后会自动释放资源，因此不能多次调用。
     */
    @Override
    public boolean pay() {
        if (isReleased) {
            throw new IllegalStateException("IPayService already release, you can not reuse a IPayService");
        }
        if (ref.get() == null) {
            return false;
        }
        final IWXAPI wxApi = WXAPIFactory.createWXAPI(ref.get(), WXConstants.getWeChatAppId());
        // 检查是否安装微信
        if (!wxApi.isWXAppInstalled()) {
            wxApi.detach();
            payCallback.onPayNotFound("wxpay");
            return false;
        }
        wxApi.registerApp(WXConstants.getWeChatAppId());

        wxApi.sendReq(wxPayData.toPayReq());
        wxApi.detach();
        return true;
    }

    @Override
    public void release() {
        synchronized (this) {
            wxPayData = null;
            payCallback = null;
            if (ref.get() != null) {
                ref.get().getApplication().unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
            }
            lifecycleCallbacks = null;
            ref.clear();
            isReleased = true;
        }
    }

    @Override
    public boolean isRelease() {
        return isReleased;
    }

    private boolean isEquals(String s, String target) {
        return s != null && target != null && s.equals(target);
    }
}
