package io.ganguo.pay.wxpay;

import android.app.Activity;

import io.ganguo.pay.core.GGPay;
import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.PayCallback;

/**
 * Created by Roger on 07/07/2017.
 */

public class WXPayMethod implements IWXPayMethod {

    public WXPayMethod(String wechatAppId) {
        if (wechatAppId == null || wechatAppId.equals("")) {
            throw new IllegalArgumentException("No wechat app id registered in GGPay");
        }
        WXConstants.setWeChatAppId(wechatAppId);
    }

    @Override
    public void pay(Activity activity, PayCallback payCallback, WXPayData wxPayData) {
        GGPay.newService(new WXPayServiceProvider(activity, payCallback, wxPayData)).pay();
    }

    @Override
    public IPayService asPayService(Activity activity, PayCallback payCallback, WXPayData wxPayData) {
        return GGPay.newService(new WXPayServiceProvider(activity, payCallback, wxPayData));
    }
}
