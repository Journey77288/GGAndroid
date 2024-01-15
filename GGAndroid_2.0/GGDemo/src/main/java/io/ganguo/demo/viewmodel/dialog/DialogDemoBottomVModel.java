package io.ganguo.demo.viewmodel.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.demo.R;
import io.ganguo.viewmodel.common.ImageViewModel;
import io.ganguo.viewmodel.common.TextViewModel;
import io.ganguo.viewmodel.common.dialog.DialogBottomVModel;
import io.ganguo.viewmodel.databinding.DialogGgBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * <p>
 * 底部弹出Demo
 * </p>
 * Created by leo on 2018/9/30.
 */
public class DialogDemoBottomVModel extends DialogBottomVModel<DialogGgBinding> {

    @Override
    public void initContent(ViewGroup container) {
        super.initContent(container);
        ViewModelHelper.bind(container, this, getTitleVModel());
        ViewModelHelper.bind(container, this, getContentVModel());
        ViewModelHelper.bind(container, this, getFooterVModel());
    }

    @Override
    public void onViewAttached(View view) {
        setDialogBgRes(R.color.white);
    }


    /**
     * function: Header ViewModel
     *
     * @return
     */
    protected BaseViewModel getContentVModel() {
        return new TextViewModel
                .Builder()
                .content("我是内容部分")
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_20)
                .paddingTopRes(R.dimen.dp_20)
                .gravity(Gravity.CENTER)
                .textColorRes(R.color.black)
                .build();
    }

    /**
     * function: Header ViewModel
     *
     * @return
     */
    protected BaseViewModel getTitleVModel() {
        return new TextViewModel
                .Builder()
                .content("普通底部Dialog")
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .textColorRes(R.color.black)
                .build();
    }


    /**
     * function: Footer ViewModel
     *
     * @return
     */
    protected BaseViewModel getFooterVModel() {
        return new TextViewModel
                .Builder()
                .content("取消")
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .backgroundRes(R.color.blue_light_translucent)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .onClickListener(v -> getView().getDialog().dismiss())
                .textColorRes(R.color.black)
                .build();
    }

}
