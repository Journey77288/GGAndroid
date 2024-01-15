package io.ganguo.pay.sample.viewmodel;

import android.Manifest;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import io.ganguo.pay.alipay.AlipayMethod;
import io.ganguo.pay.alipay.IAlipayMethod;
import io.ganguo.pay.core.GGPay;
import io.ganguo.pay.core.PayCallback;
import io.ganguo.pay.core.PayResult;
import io.ganguo.pay.sample.R;
import io.ganguo.pay.wxpay.IWXPayMethod;
import io.ganguo.pay.wxpay.WXPayData;
import io.ganguo.pay.wxpay.WXPayMethod;
import io.ganguo.utils.util.Permissions;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.item.ItemSampleVModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.reactivex.functions.Action;

/**
 * <p>
 * 支付SDK Demo
 * </p>
 * Created by leo on 2018/8/7.
 */
public class PaySampleViewModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> implements PayCallback {
    private static final String TAG = "MainActivity";
    private String orderInfo = "\"_input_charset\\u003d\\\"utf-8\\\"\\u0026it_b_pay\\u003d\\\"30m\\\"\\u0026notify_url\\u003d\\\"http://amii.dev.ganguo.hk/notify/alipay\\\"\\u0026out_trade_no\\u003d\\\"711910705235063151\\\"\\u0026partner\\u003d\\\"2088121767401254\\\"\\u0026payment_type\\u003d\\\"1\\\"\\u0026seller_id\\u003d\\\"2088121767401254\\\"\\u0026service\\u003d\\\"mobile.securitypay.pay\\\"\\u0026subject\\u003d\\\"Amii[极简主义]2017夏装新款露肩蕾丝心机上衣修身显瘦短袖T恤女\\\"\\u0026total_fee\\u003d\\\"0.01\\\"\\u0026sign\\u003d\\\"CPNggV3QyxylAxZ1gXqO96idy%2BVCHBTnpxQ%2BVxj5VSIkW0ezS1pafd%2BEi9ZiSqFRk6A%2FvxRRvks9ukSDXEj%2BL8v82sZ9Dkj6HqgE3Kz3oQvbvR6LFNnTRIb3Rk7qMaO9RFIFe%2BWyfM6VfVKtCEPdYVBDE%2FFG1grsUjyugHTZAEo%3D\\\"\\u0026sign_type\\u003d\\\"RSA\\\"\"";
    private WXPayData wxPayData = new WXPayData();

    /**
     * <p>
     * init header
     * </p>
     */
    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel.TitleItemViewModel("支付Demo"))
                .appendItemLeft(new HeaderItemViewModel.BackItemViewModel(getView().getActivity()))
                .build();
        ViewModelHelper.bind(container, this, headerViewModel);
    }

    @Override
    public void onViewAttached(View view) {
        registerGGPay();
        setupDebugWechatData();

        addViewModel(R.color.blue_translucent, "aliPay", () -> onAlipayClick());

        addViewModel(R.color.green_dark_translucent, "wechatPay", () -> onWXPayClick());

        getAdapter().notifyDataSetChanged();
        toggleEmptyView();

    }


    /**
     * <p>
     * add ItemViewModel
     * </p>
     *
     * @param color
     * @param action
     * @param text
     */
    protected void addViewModel(@ColorRes int color, String text, Action action) {
        getAdapter().add(ItemSampleVModel.onCrateItemViewModel(color, text, action));
    }


    /**
     * function:register pat
     */
    private void registerGGPay() {
        GGPay.registerPayMethod(new WXPayMethod("wxfd3587fd3c79db51"));
        GGPay.registerPayMethod(new AlipayMethod());
    }

    /**
     * function: set debug WeChat pay data
     */
    private void setupDebugWechatData() {
        wxPayData.setAppid("wxfd3587fd3c79db51");
        wxPayData.setPartnerid("1370698602");
        wxPayData.setPrepayid("wx2017070615544619ac664d860740979783");
        wxPayData.setNoncestr("595decc667443");
        wxPayData.setTimestamp("1499327686");
        wxPayData.setPackageX("Sign=WXPay");
        wxPayData.setSign("35AC2BF51AB5A7042E7C4E3A62D20844");
    }

    /**
     * function: 支付宝支付
     */
    private void onAlipayClick() {
        Permissions.requestPermission(getContext(), "需要取得相关权限，才能使用支付宝支付功能", new Permissions.onPermissionAdapterListener() {
            @Override
            public void onRequestSuccess(List<String> successPermissions) {
                IAlipayMethod iAlipayMethod = GGPay.getPayMethod(IAlipayMethod.class);
                iAlipayMethod.pay(getView().getActivity(), PaySampleViewModel.this, orderInfo);
            }
        }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * function: 微信支付
     */
    private void onWXPayClick() {
        Permissions.requestPermission(getContext(), "需要取得相关权限，才能使用微信支付功能", new Permissions.onPermissionAdapterListener() {
            @Override
            public void onRequestSuccess(List<String> successPermissions) {
                IWXPayMethod iwxPayMethod = GGPay.getPayMethod(IWXPayMethod.class);
                iwxPayMethod.pay(getView().getActivity(), PaySampleViewModel.this, wxPayData);
            }
        }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    @Override
    public void onPaySuccess(PayResult result) {
        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayError(PayResult result) {
        Toast.makeText(getContext(), "支付失败 " + result.code + ": " + result.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayCancel(PayResult result) {
        Toast.makeText(getContext(), "取消支付 " + result.code + ": " + result.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayNotFound(String payType) {
        if (payType != null && payType.equals("wxpay")) {
            Toast.makeText(getContext(), "请先安装微信后再发起支付", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GGPay.clear();
    }
}
