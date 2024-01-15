package io.ganguo.demo.viewmodel.item;

import android.databinding.ObservableField;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemQiniuImageBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * <p>
 * 七牛demo - item ViewModel
 * </p>
 * Created by leo on 2018/7/30.
 */
public class ItemQiNiuImageVModel extends BaseViewModel<ViewInterface<ItemQiniuImageBinding>> {
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableField<String> index = new ObservableField<>();

    public ItemQiNiuImageVModel(String imageUrl, String index) {
        this.imageUrl.set(imageUrl);
        this.index.set(index);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_qiniu_image;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
