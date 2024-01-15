package io.ganguo.pay.alipay;

import android.app.Activity;

import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.PayCallback;

/**
 * Created by Roger on 07/07/2017.
 */

public interface IAlipayMethod {
    void pay(Activity activity, PayCallback payCallback, String orderInfo);

    IPayService asPayService(Activity activity, PayCallback payCallback, String orderInfo);
}
