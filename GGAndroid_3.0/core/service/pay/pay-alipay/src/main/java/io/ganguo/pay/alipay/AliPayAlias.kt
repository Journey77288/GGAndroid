package io.ganguo.pay.alipay

import io.ganguo.pay.core.IPayProvider
import io.ganguo.pay.core.PayMethod
import io.ganguo.pay.core.PayResult
import io.ganguo.pay.core.PayService
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/13
 *     desc   : 别名
 * </pre>
 * @see [AAliPayMethod] 支付宝支付方法基类
 * @see [AAliPayService] 支付宝支付服务基类
 * @see [IAliPayProvider] 支付宝支付服务[IPayProvider] 创建类
 * @see [AliPayResult] 支付宝支付结果回调类型
 * @see [AliPayResultObservable] 支付结果回调Observable类型
 */


typealias AliPayResult = PayResult<AliPayOrderInfo>

typealias AliPayResultObservable = Observable<AliPayResult>

typealias AAliPayService = PayService<AliPayResult, AliPayResultObservable>

typealias IAliPayProvider = IPayProvider<AliPayService>

typealias AAliPayMethod = PayMethod<String, AliPayResult, AliPayResultObservable, AliPayService, AliPayProvider>
