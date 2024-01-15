package io.ganguo.demo.viewmodel.item;

import android.databinding.ObservableField;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemBannerChildBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.view.AdapterInterface;

/**
 * Created by leo on 2017/4/27.
 * 广告轮播 - ItemViewModel
 */
public class ItemBannerChildVModel extends BaseViewModel<AdapterInterface<ItemBannerChildBinding>> {
    private ObservableField<String> imageUrl = new ObservableField<>();

    @Override
    public int getItemLayoutId() {
        return R.layout.item_banner_child;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public ItemBannerChildVModel setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
        return this;
    }

    public ObservableField<String> getImageUrl() {
        return imageUrl;
    }
}
