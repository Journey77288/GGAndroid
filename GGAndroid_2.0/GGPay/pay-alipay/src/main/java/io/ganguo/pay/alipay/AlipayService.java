package io.ganguo.pay.alipay;

import android.app.Activity;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.ganguo.pay.core.IPayService;
import io.ganguo.pay.core.PayCallback;

/**
 * Created by Roger on 05/07/2017.
 */

final class AlipayService implements IPayService {
    public static final int PAY_FLAG = 1;
    /*支付宝支付结果码*/
    public static final String PAY_OK = "9000";// 支付成功
    public static final String PAY_WAIT_CONFIRM = "8000";// 交易待确认
    public static final String PAY_NET_ERR = "6002";// 网络出错
    public static final String PAY_CANCEL = "6001";// 交易取消
    public static final String PAY_FAILED = "4000";// 交易失败

    private PayResultHandler payResultHandler;

    private PayCallback payCallback;
    private WeakReference<Activity> ref;
    private String orderInfo;
    private Future future;
    private boolean isRelease = false;

    AlipayService(Activity activity, PayCallback callback, String orderInfo) {
        this.ref = new WeakReference<Activity>(activity);
        this.payCallback = callback;
        this.payResultHandler = new PayResultHandler(payCallback, this);
        this.orderInfo = orderInfo;
    }

    /**
     * 发起支付，为避免内存泄露，pay 方法在支付回调返回后会自动释放资源，因此不能多次调用。
     */
    @Override
    public boolean pay() {
        if (isRelease) {
            throw new IllegalStateException("IPayService already release, you can not reuse a IPayService");
        }
        if (ref.get() == null) {
            return false;
        }
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 检查账户信息是否完整
//                if (!payTask.checkAccountIfExist()) {
//                    payResultHandler.sendMessage(msg);
//                }
                // 构造 PayTask 对象
                PayTask alipay = new PayTask(ref.get());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                payResultHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        ExecutorService executor = Executors.newSingleThreadExecutor();
        future = executor.submit(payRunnable);
        return true;
    }

    /**
     * 释放资源，避免内存泄露
     */
    @Override
    public void release() {
        synchronized (this) {
            if (future != null) {
                future.cancel(true);
            }
            if (payResultHandler != null) {
                payResultHandler.release();
                payResultHandler = null;
            }
            orderInfo = null;
            payCallback = null;
            ref.clear();
            isRelease = true;
        }
    }

    @Override
    public boolean isRelease() {
        return isRelease;
    }
}
