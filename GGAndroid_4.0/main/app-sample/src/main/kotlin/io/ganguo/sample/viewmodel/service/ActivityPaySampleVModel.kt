package io.ganguo.sample.viewmodel.service

import android.view.View
import android.view.ViewGroup
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.factory.GGFactory
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.pay.alipay.helper.AlipayPayHelper
import io.ganguo.pay.core.annotation.PayStatus
import io.ganguo.pay.core.exception.PayServiceException
import io.ganguo.pay.wxpay.entity.WXPayEntity
import io.ganguo.pay.wxpay.helper.WXPayHelper
import io.ganguo.rxjava.printThrowable
import io.ganguo.sample.R
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Pay Sample
 * </pre>
 */
class ActivityPaySampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    private val orderInfo = "\"_input_charset\\u003d\\\"utf-8\\\"\\u0026it_b_pay\\u003d\\\"30m\\\"\\u0026notify_url\\u003d\\\"http://amii.dev.ganguo.hk/notify/alipay\\\"\\u0026out_trade_no\\u003d\\\"711910705235063151\\\"\\u0026partner\\u003d\\\"2088121767401254\\\"\\u0026payment_type\\u003d\\\"1\\\"\\u0026seller_id\\u003d\\\"2088121767401254\\\"\\u0026service\\u003d\\\"mobile.securitypay.pay\\\"\\u0026subject\\u003d\\\"Amii[极简主义]2017夏装新款短袖T恤\\\"\\u0026total_fee\\u003d\\\"0.01\\\"\\u0026sign\\u003d\\\"CPNggV3QyxylAxZ1gXqO96idy%2BVCHBTnpxQ%2BVxj5VSIkW0ezS1pafd%2BEi9ZiSqFRk6A%2FvxRRvks9ukSDXEj%2BL8v82sZ9Dkj6HqgE3Kz3oQvbvR6LFNnTRIb3Rk7qMaO9RFIFe%2BWyfM6VfVKtCEPdYVBDE%2FFG1grsUjyugHTZAEo%3D\\\"\\u0026sign_type\\u003d\\\"RSA\\\"\""
    private val wxPayEntity = WXPayEntity(
            appid = "wxfd3587fd3c79db51",
            partnerid = "1370698602",
            prepayid = "wx2017070615544619ac664d860740979783",
            noncestr = "595decc667443",
            timestamp = "1499327686",
            packageX = "Sign=WXPay",
            sign = "35AC2BF51AB5A7042E7C4E3A62D20844"
    )

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_pay_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            addAll(
                listOf(
                    ItemMenuVModel.create(getString(R.string.str_wechat_pay)) {
                        payViaWeChat()
                    },
                    ItemMenuVModel.create(getString(R.string.str_alipay_pay)) {
                        payViaAliPay()
                    }
                )
            )
        }
    }

    /**
     * WeChat pay
     */
    private fun payViaWeChat() {
        wechatPay()
    }

    private fun wechatPay() {
        WXPayHelper
                .pay(viewIF.activity, wxPayEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { onPayResult(it.status) }
                .doOnError {
                    if (it is PayServiceException) {
                        Logger.e("payServiceException: type=${it.payType}:errorCode=${it.errorCode}:errorMsg:${it.errorMsg}", )
                    }
                    Toasts.show(it.message.orEmpty())
                }
                .subscribe(Functions.emptyConsumer(), printThrowable("--wechatPay--"))
                .addTo(lifecycleComposite)
    }

    /**
     * AliPay pay
     */
    private fun payViaAliPay() {
        alipay()
    }

    private fun alipay() {
        AlipayPayHelper
                .pay(viewIF.activity, orderInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { onPayResult(it.status) }
                .subscribe(Functions.emptyConsumer(), printThrowable("--alipay--"))
                .addTo(lifecycleComposite)
    }

    /**
     * pay result callback
     */
    private fun onPayResult(status: Int) {
        when (status) {
            PayStatus.SUCCESS -> Toasts.show(R.string.str_pay_success)
            PayStatus.CANCEL -> Toasts.show(R.string.str_pay_cancel)
        }
    }

    override fun onDestroy() {
        GGFactory.clearService()
        super.onDestroy()
    }
}