package io.ganguo.demo.viewmodel.activity;

import android.databinding.ObservableField;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.bean.ConstantsBus;
import io.ganguo.demo.databinding.ActivityRxbusDemoBinding;
import io.ganguo.rx.bus.RxBus;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.common.UIHelper;
import io.ganguo.utils.util.Strings;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;

/**
 * RxBus Demo
 * Created by leo on 2018/8/15.
 */
public class RxBusDemoVModel extends BaseViewModel<ActivityInterface<ActivityRxbusDemoBinding>> {
    public ObservableField<String> input = new ObservableField<>();

    @Override
    public void onViewAttached(View view) {
    }


    /**
     * function: send RxBus click
     *
     * @return
     */
    public View.OnClickListener onSendClick() {
        return v -> {
            if (Strings.isEmpty(input.get())) {
                ToastHelper.showMessage( "请输入要发送的内容！");
                return;
            }
            // TODO: 2018/8/15 在这里发送需要传递的内容/Object，在需要接收到的地方接收即可，Demo在MainVModel中接收
            RxBus.getDefault().send(input.get(), ConstantsBus.BUS_DEMO_KEY);
            UIHelper.hideKeyboard(getRootView());
            getView().getActivity().finish();
        };
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_rxbus_demo;
    }
}
