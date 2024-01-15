package io.ganguo.pay.core

import android.app.Activity
import io.ganguo.factory.method.Method

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/13
 *     desc   : 支付方法
 * </pre>
 */
abstract class PayMethod<Param,
        Result : PayResult<out PayOrderInfo>,
        ResultObservable,
        Service : PayService<Result, ResultObservable>,
        Provider : IPayProvider<Service>> : Method<Result, ResultObservable, Service, Provider>() {

    /**
     * 发起支付
     *
     * @param activity Activity引用
     * @param param 支付订单数据
     */
    fun pay(activity: Activity, param: Param?): ResultObservable {
        return newPayProvider(activity, param).let {
            asService(activity, it).pay()
        }
    }

    /**
     * create pay Provider
     * @param activity Activity
     * @param param Param?
     */
    abstract fun newPayProvider(activity: Activity, param: Param?): Provider
}