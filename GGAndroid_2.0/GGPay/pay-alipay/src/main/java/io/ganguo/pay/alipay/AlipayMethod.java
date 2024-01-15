package io.ganguo.pay.alipay;

import android.app.Activity;

import io.ganguo.pay.core.GGPay;
import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.PayCallback;

/**
 * Created by Roger on 07/07/2017.
 */

public class AlipayMethod implements IAlipayMethod {
    @Override
    public void pay(Activity activity, PayCallback payCallback, String orderInfo) {
        GGPay.newService(new AlipayServiceProvider(activity, payCallback, orderInfo)).pay();
    }

    @Override
    public IPayService asPayService(Activity activity, PayCallback payCallback, String orderInfo) {
        return GGPay.newService(new AlipayServiceProvider(activity, payCallback, orderInfo));
    }
}
