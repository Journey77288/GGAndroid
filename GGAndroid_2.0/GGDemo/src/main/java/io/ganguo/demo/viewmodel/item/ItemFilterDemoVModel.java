package io.ganguo.demo.viewmodel.item;

import android.databinding.ObservableField;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemFilterDemoBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.view.AdapterInterface;

/**
 * Created by leo on 2016/12/21.
 */
public class ItemFilterDemoVModel extends BaseViewModel<AdapterInterface<ItemFilterDemoBinding>> {
    private ObservableField<String> title = new ObservableField<>();

    public ItemFilterDemoVModel(String title) {
        this.title.set(title);
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(ObservableField<String> title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return title.get();
    }

    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_filter_demo;
    }
}
