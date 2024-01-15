package io.ganguo.demo.viewmodel.item;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ViewSelectFooterBinding;
import io.ganguo.rx.RxProperty;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * Created by Roger on 13/05/2017.
 */

public class SelectDemoFooterVModel extends BaseViewModel<ViewInterface<ViewSelectFooterBinding>> {
    private RxProperty<Boolean> isSelectAll;

    private Action selectAllAction; //全选 Action
    private Action reverseAction; //反选 Action

    public SelectDemoFooterVModel(Observable<Boolean> isSelectAll, Action selectAllAction, Action reverseAction) {
        this.isSelectAll = new RxProperty<>(isSelectAll);
        this.selectAllAction = selectAllAction;
        this.reverseAction = reverseAction;
    }

    @Override
    public void onViewAttached(View view) {

    }

    /**
     * 点击全选
     */
    public void onSelectAllClick(View view) {
        try {
            if (selectAllAction != null) {
                selectAllAction.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击反选
     */
    public void onReverseClick(View view) {
        try {
            if (reverseAction != null) {
                reverseAction.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RxProperty<Boolean> getIsSelectAll() {
        return isSelectAll;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.view_select_footer;
    }
}
