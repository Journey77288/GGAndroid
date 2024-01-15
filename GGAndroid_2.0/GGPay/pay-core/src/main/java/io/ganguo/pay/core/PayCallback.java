package io.ganguo.pay.core;

/**
 * Created by Roger on 05/07/2017.
 */

public interface PayCallback {
    void onPaySuccess(PayResult result);

    void onPayError(PayResult result);

    void onPayCancel(PayResult result);

    void onPayNotFound(String payType);
}
