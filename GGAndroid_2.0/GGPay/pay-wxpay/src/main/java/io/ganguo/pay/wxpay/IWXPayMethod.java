package io.ganguo.pay.wxpay;

import android.app.Activity;

import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.PayCallback;

/**
 * Created by Roger on 07/07/2017.
 */

public interface IWXPayMethod {
    void pay(Activity activity, PayCallback payCallback, WXPayData wxPayData);

    IPayService asPayService(Activity activity, PayCallback payCallback, WXPayData wxPayData);
}
