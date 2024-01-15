package io.ganguo.demo.viewmodel.item;

import android.databinding.ObservableField;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemOneListBinding;
import io.ganguo.demo.entity.one.ContentOneEntity;
import io.ganguo.library.ui.adapter.v7.callback.IDiffComparator;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * <p>
 * 《一个》 - 数据ViewModel
 * </p>
 * Created by leo on 2018/7/30.
 */

public class ItemOneListVModel extends BaseViewModel<ViewInterface<ItemOneListBinding>> implements IDiffComparator<ItemOneListVModel> {
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>();
    private ContentOneEntity oneEntity;

    public ItemOneListVModel(ContentOneEntity oneEntity) {
        this.oneEntity = oneEntity;
        this.imageUrl.set(oneEntity.getImgUrl());
        this.content.set(oneEntity.getForward());
    }


    @Override
    public int getItemLayoutId() {
        return R.layout.item_one_list;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public ContentOneEntity getOneEntity() {
        return oneEntity;
    }

    @Override
    public boolean isDataEquals(ItemOneListVModel vModel) {
        return oneEntity.equals(vModel.getOneEntity());
    }

    @Override
    public ItemOneListVModel getDiffCompareObject() {
        return this;
    }
}
