package io.ganguo.demo.viewmodel.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.demo.R;
import io.ganguo.viewmodel.common.TextViewModel;
import io.ganguo.viewmodel.common.dialog.DialogRecyclerBottomVModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * <p>
 * 支持RecyclerView的底部弹窗 Demo
 * </p>
 * Created by leo on 2018/9/30.
 */
public class DialogDemoBtmRecyclerVModel extends DialogRecyclerBottomVModel {
    @Override
    public void initHeader(ViewGroup container) {
        ViewModelHelper.bind(container, this, getTitleVModel());
    }

    @Override
    public void initFooter(ViewGroup container) {
        ViewModelHelper.bind(container, this, getFooterVModel());
    }

    @Override
    public void onViewAttached(View view) {
        setDialogBgRes(R.color.white);
        getAdapter().add(getTextVModel());
        getAdapter().add(getTextVModel());
        getAdapter().add(getTextVModel());
        getAdapter().add(getTextVModel());
        getAdapter().add(getTextVModel());
        getAdapter().add(getTextVModel());
        getAdapter().notifyDataSetChanged();

    }

    /**
     * function: item ViewModel
     *
     * @return
     */
    protected BaseViewModel getTextVModel() {
        return new TextViewModel
                .Builder()
                .content("test demo")
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
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
                .content("支持RecyclerView的底部弹弹窗")
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
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .backgroundRes(R.color.blue_light_translucent)
                .onClickListener(v -> getView().getDialog().dismiss())
                .textColorRes(R.color.black)
                .build();
    }
}
