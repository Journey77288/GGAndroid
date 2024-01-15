package io.ganguo.demo.viewmodel.item;

import android.databinding.ObservableField;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemPagerCardBinding;
import io.ganguo.demo.entity.ImageEntity;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * Created by leo on 16/7/31.
 * Item Pager-card
 */
public class ItemPagerCardVModel extends BaseViewModel<ViewInterface<ItemPagerCardBinding>> {
    private ImageEntity image;
    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> imageUrl = new ObservableField<>();

    public ItemPagerCardVModel(ImageEntity image) {
        this.image = image;
        this.name.set(image.getName());
        this.imageUrl.set(image.getImage());
    }

    @Override
    public void onViewAttached(View view) {

    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getImageUrl() {
        return imageUrl;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_pager_card;
    }
}
