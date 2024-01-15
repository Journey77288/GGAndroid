package io.ganguo.pay.alipay;

import android.app.Activity;

import java.lang.ref.WeakReference;

import io.ganguo.pay.core.PayCallback;
import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.IProvider;

/**
 * Created by Roger on 05/07/2017.
 */

class AlipayServiceProvider implements IProvider {
    private String orderInfo;
    private PayCallback payCallback;
    private WeakReference<Activity> ref;

    AlipayServiceProvider(Activity activity, PayCallback payCallback, String orderInfo) {
        this.ref = new WeakReference<Activity>(activity);
        this.payCallback = payCallback;
        this.orderInfo = orderInfo;
    }

    @Override
    public IPayService newService() {
        return new AlipayService(ref.get(), payCallback, orderInfo);
    }
}
