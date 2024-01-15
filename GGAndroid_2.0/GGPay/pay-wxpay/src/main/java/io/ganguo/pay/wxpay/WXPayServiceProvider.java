package io.ganguo.pay.wxpay;

import android.app.Activity;

import java.lang.ref.WeakReference;

import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.IProvider;
import io.ganguo.pay.core.PayCallback;

/**
 * Created by Roger on 06/07/2017.
 */

class WXPayServiceProvider implements IProvider {
    private WXPayData wxPayData;
    private PayCallback payCallback;
    private WeakReference<Activity> ref;

    WXPayServiceProvider(Activity activity, PayCallback payCallback, WXPayData wxPayData) {
        this.wxPayData = wxPayData;
        this.payCallback = payCallback;
        this.ref = new WeakReference<Activity>(activity);
    }

    @Override
    public IPayService newService() {
        return new WXPayService(ref.get(), payCallback, wxPayData);
    }
}
