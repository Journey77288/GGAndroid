package io.ganguo.demo.viewmodel.activity;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivityRxDemoBinding;
import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxCommand;
import io.ganguo.rx.RxProperty;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.Numbers;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Roger on 7/26/16.
 */
public class RxDemoVModel extends BaseViewModel<ActivityInterface<ActivityRxDemoBinding>> {
    private RxProperty<String> input = new RxProperty<String>("1");

    private RxCommand<View.OnClickListener> clickCommand;

    /**
     * Init RxProperty and RxCommand in constructor,
     * otherwise RxProperty will subscribe multi-times when viewModel attach multi-times.
     */
    public RxDemoVModel() {
        // subscribe text change
        subscribeInput();
        onClickCommand();
    }

    /**
     * function: lazy init clickCommand
     */
    protected void onClickCommand() {
        clickCommand = new RxCommand<View.OnClickListener>(
                input.map(s -> Strings.isNotEmpty(s) && s.length() < 4),
                v -> ToastHelper.showMessage("Submit quantity")
        );
    }

    /**
     * function: 订阅输入事件
     */
    private void subscribeInput() {
        input.compose(RxVMLifecycle.<String>doWhenAttach(this))
                .compose(RxVMLifecycle.<String>bindViewModel(this))
                .subscribe(s -> {
                    // 限制最小数量1
                    Logger.d("call: " + "input onChange1: " + s);
                    if (Strings.isEmpty(s) || Numbers.toInt(s) == 0) {
                        input.set("1");
                        return;
                    }
                    // 限制个位数前输入数字0
                    if (s.length() > 1 && s.charAt(0) == '0') {
                        input.set(s.substring(1, s.length()));
                        return;
                    }
                }, RxActions.printThrowable());
    }


    @Override
    public void onViewAttached(View view) {

    }

    public RxProperty<String> getInput() {
        return input;
    }

    public RxCommand<View.OnClickListener> getClickCommand() {
        return clickCommand;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_rx_demo;
    }
}
