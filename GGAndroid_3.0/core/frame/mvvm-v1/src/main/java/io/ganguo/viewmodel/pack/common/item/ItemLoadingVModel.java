package io.ganguo.viewmodel.pack.common.item;

import android.view.View;

import androidx.databinding.ObservableField;
import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.ItemLoadingBinding;
import io.ganguo.viewmodel.core.BaseViewModel;

/**
 * Created by leo on 2018/11/28.
 * Loading Item
 */
public class ItemLoadingVModel extends BaseViewModel<ViewInterface<ItemLoadingBinding>> {
    public ObservableField<String> content;

    public ItemLoadingVModel(ObservableField<String> content) {
        this.content = content;
    }

    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_loading;
    }
}
