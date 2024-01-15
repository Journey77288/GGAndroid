package io.ganguo.pay.wxpay

import io.ganguo.pay.core.*
import io.ganguo.pay.wxpay.entity.WXPayEntity
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/13
 *     desc   : 别名
 * </pre>
 * @see [AWXPayMethod] 微信支付方法基类
 * @see [AWXPayService] 微信支付服务基类
 * @see [IWXPayProvider] 微信支付服务[IPayProvider] 创建类
 * @see [WXPayResult] 微信支付结果类型
 * @see [WXPayResultObservable] 支付结果回调Observable
 */

typealias WXPayResult = PayResult<PayOrderInfo>

typealias WXPayResultObservable = Observable<WXPayResult>

typealias AWXPayService = PayService<WXPayResult, WXPayResultObservable>

typealias IWXPayProvider = IPayProvider<WXPayService>

typealias AWXPayMethod = PayMethod<WXPayEntity, WXPayResult, WXPayResultObservable, WXPayService, WXPayProvider>


