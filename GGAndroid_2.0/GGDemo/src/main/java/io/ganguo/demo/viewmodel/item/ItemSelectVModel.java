package io.ganguo.demo.viewmodel.item;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemSelectBinding;
import io.ganguo.rx.selector.SelectHelper;
import io.ganguo.rx.selector.SelectableView;
import io.ganguo.rx.selector.SimpleSelectHelper;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.reactivex.functions.Consumer;

/**
 * Created by Roger on 12/05/2017.
 */

public class ItemSelectVModel extends BaseViewModel<ViewInterface<ItemSelectBinding>> implements SelectableView<ItemSelectVModel>, SelectHelper.OnSelectListener {
    private SelectHelper<ItemSelectVModel> selectHelper;
    private Consumer<ItemSelectVModel> deleteAction;

    public ItemSelectVModel(Consumer<ItemSelectVModel> deleteAction) {
        this.selectHelper = new SimpleSelectHelper<ItemSelectVModel>(this, false);
        this.selectHelper.setOnSelectListener(this);
        this.deleteAction = deleteAction;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public void onDeleteClick(View view) {
        try {
            if (deleteAction != null) {
                deleteAction.accept(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCheckClick(View view) {
        selectHelper.toggleSelect();
    }

    @Override
    public SelectHelper<ItemSelectVModel> getSelectHelper() {
        return selectHelper;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_select;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onUnselected() {
    }
}
