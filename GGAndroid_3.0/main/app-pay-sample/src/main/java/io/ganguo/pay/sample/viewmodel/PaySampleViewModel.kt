package io.ganguo.pay.sample.viewmodel

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import io.ganguo.factory.GGFactory
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.log.core.Logger
import io.ganguo.pay.alipay.AliPayMethod
import io.ganguo.pay.core.PayResult
import io.ganguo.pay.core.annotation.PayStatus
import io.ganguo.pay.core.exception.PayServiceException
import io.ganguo.pay.sample.R
import io.ganguo.pay.wxpay.WXPayMethod
import io.ganguo.pay.wxpay.entity.WXPayEntity
import io.ganguo.rx.RxActions
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.pack.common.item.ItemSampleVModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.internal.functions.Functions
import io.reactivex.rxkotlin.addTo

/**
 *
 *
 * 支付SDK Demo
 *
 * Created by leo on 2018/8/7.
 */
class PaySampleViewModel : HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>>() {
    private val orderInfo = "\"_input_charset\\u003d\\\"utf-8\\\"\\u0026it_b_pay\\u003d\\\"30m\\\"\\u0026notify_url\\u003d\\\"http://amii.dev.ganguo.hk/notify/alipay\\\"\\u0026out_trade_no\\u003d\\\"711910705235063151\\\"\\u0026partner\\u003d\\\"2088121767401254\\\"\\u0026payment_type\\u003d\\\"1\\\"\\u0026seller_id\\u003d\\\"2088121767401254\\\"\\u0026service\\u003d\\\"mobile.securitypay.pay\\\"\\u0026subject\\u003d\\\"Amii[极简主义]2017夏装新款露肩蕾丝心机上衣修身显瘦短袖T恤女\\\"\\u0026total_fee\\u003d\\\"0.01\\\"\\u0026sign\\u003d\\\"CPNggV3QyxylAxZ1gXqO96idy%2BVCHBTnpxQ%2BVxj5VSIkW0ezS1pafd%2BEi9ZiSqFRk6A%2FvxRRvks9ukSDXEj%2BL8v82sZ9Dkj6HqgE3Kz3oQvbvR6LFNnTRIb3Rk7qMaO9RFIFe%2BWyfM6VfVKtCEPdYVBDE%2FFG1grsUjyugHTZAEo%3D\\\"\\u0026sign_type\\u003d\\\"RSA\\\"\""
    private val wxPayEntity = WXPayEntity()

    /**
     *
     *
     * init header
     *
     */
    override fun initHeader(container: ViewGroup) {
        super.initHeader(container)
        val headerViewModel = HeaderViewModel.Builder()
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel("支付Demo"))
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.activity as Activity?))
                .build()
        ViewModelHelper.bind(container, this, headerViewModel)
    }

    override fun onViewAttached(view: View) {
        setupDebugWechatData()

        addViewModel(R.color.blue_translucent, "aliPay", Action { onAliPayAction() })

        addViewModel(R.color.green_dark_translucent, "wechatPay", Action { onWXPayAction() })

        adapter.notifyDataSetChanged()
        toggleEmptyView()

    }


    /**
     *
     *
     * add ItemViewModel
     *
     *
     * @param color
     * @param action
     * @param text
     */
    private fun addViewModel(@ColorRes color: Int, text: String, action: Action) {
        adapter.add(ItemSampleVModel.newItemViewModel(color, text, action))
    }


    /**
     * function: set debug WeChat pay data
     */
    private fun setupDebugWechatData() {
        wxPayEntity.appid = "wxfd3587fd3c79db51"
        wxPayEntity.partnerid = "1370698602"
        wxPayEntity.prepayid = "wx2017070615544619ac664d860740979783"
        wxPayEntity.noncestr = "595decc667443"
        wxPayEntity.timestamp = "1499327686"
        wxPayEntity.packageX = "Sign=WXPay"
        wxPayEntity.sign = "35AC2BF51AB5A7042E7C4E3A62D20844"
    }

    /**
     *  支付宝支付
     */
    private fun onAliPayAction() {
        viewInterface.activity
                .runWithPermissions(Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE) {
                    if (it.isAllGranted(it.permissions)) {
                        aliPay()
                    } else {
                        ToastHelper.showMessage("需要取得相关权限，才能使用支付宝支付功能")
                    }
                }
    }


    /**
     * 支付宝支付
     */
    private fun aliPay() {
        GGFactory
                .getMethod<AliPayMethod>(AliPayMethod::class.java)
                .pay(viewInterface.activity, orderInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerPayResult(it)
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--aliPay--"))
                .addTo(lifecycleComposite)
    }

    /**
     *  微信支付
     */
    private fun onWXPayAction() {
        viewInterface.activity
                .runWithPermissions(Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE) {
                    if (it.isAllGranted(it.permissions)) {
                        wxPay()
                    } else {
                        ToastHelper.showMessage("需要取得相关权限，才能使用微信支付功能")
                    }
                }

    }


    /**
     *  微信支付
     */
    private fun wxPay() {
        GGFactory.getMethod<WXPayMethod>(WXPayMethod::class.java)
                .pay(viewInterface.activity, wxPayEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    handlerPayResult(it)
                }
                .doOnError {
                    if (it is PayServiceException) {
                        Logger.e("PayServiceException:type=${it.payType}:errorCode=${it.errorCode}:errorMsg=${it.errorMsg}")
                    }
                    ToastHelper.showMessage("WXPayMethod:${it.message}")
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--aliPay--"))
                .addTo(lifecycleComposite)
    }

    /**
     *  处理支付结果回调
     *  @param result 支付结果
     */
    private fun handlerPayResult(result: PayResult<*>) {
        var status = when {
            result.status == PayStatus.SUCCESS -> "支付成功"
            result.status == PayStatus.CANCEL -> "支付取消"
            else -> "支付失败"
        }
        ToastHelper.showMessage(status)
        Logger.e("${result.type} pay result：${result}")
    }


    override fun onDestroy() {
        super.onDestroy()
        GGFactory.clearService()
    }
}
