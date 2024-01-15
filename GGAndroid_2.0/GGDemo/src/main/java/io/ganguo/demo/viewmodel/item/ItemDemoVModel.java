package io.ganguo.demo.viewmodel.item;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemDemoBinding;
import io.ganguo.library.ui.adapter.v7.callback.IDiffComparator;
import io.ganguo.utils.callback.common.Action1;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.view.AdapterInterface;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.utils.common.ToastHelper;
import io.reactivex.functions.Action;

/**
 * Created by Roger on 6/4/16.
 */
public class ItemDemoVModel<T> extends BaseViewModel<ViewInterface<ItemDemoBinding>> implements IDiffComparator<ItemDemoVModel> {

    private String content;
    private String btnText;
    private Action1<ItemDemoVModel> clickAction;
    private T data;

    @Override
    public void onViewAttached(View view) {

    }


    public View.OnClickListener onClick() {
        return v -> {
            if (clickAction != null) {
                clickAction.call(ItemDemoVModel.this);
            }
        };
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_demo;
    }


    @Override
    public boolean isDataEquals(ItemDemoVModel itemDemoVModel) {
        return data.equals(itemDemoVModel.getData());
    }

    @Override
    public ItemDemoVModel getDiffCompareObject() {
        return this;
    }


    public String getContent() {
        return content;
    }

    public ItemDemoVModel<T> setContent(String content) {
        this.content = content;
        return this;
    }

    public String getBtnText() {
        return btnText;
    }

    public ItemDemoVModel<T> setBtnText(String btnText) {
        this.btnText = btnText;
        return this;
    }

    public Action1<ItemDemoVModel> getClickAction() {
        return clickAction;
    }

    public ItemDemoVModel<T> setClickAction(Action1<ItemDemoVModel> clickAction) {
        this.clickAction = clickAction;
        return this;
    }

    public T getData() {
        return data;
    }

    public ItemDemoVModel<T> setData(T data) {
        this.data = data;
        return this;
    }
}
