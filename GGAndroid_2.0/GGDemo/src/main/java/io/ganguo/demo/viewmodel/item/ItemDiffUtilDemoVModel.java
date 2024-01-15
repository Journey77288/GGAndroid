package io.ganguo.demo.viewmodel.item;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemDiffUtilDemoBinding;
import io.ganguo.demo.entity.CommonDemoEntity;
import io.ganguo.library.ui.adapter.v7.callback.IDiffComparator;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.view.AdapterInterface;
import io.reactivex.functions.Consumer;

/**
 * <p>
 * DiffUtil Demo Item ViewModel
 * 注：IDiffComparator<B> B传你想要获取比较的数据引用对象类型即可，可以是Entity，也可以是ViewModel，也可以是其他类型参数。
 * </p>
 * Created by leo on 2018/8/27.
 */
public class ItemDiffUtilDemoVModel extends BaseViewModel<AdapterInterface<ItemDiffUtilDemoBinding>> implements IDiffComparator<ItemDiffUtilDemoVModel> {
    public String text;
    private CommonDemoEntity entity;
    private Consumer<ItemDiffUtilDemoVModel> removeConsumer;

    public ItemDiffUtilDemoVModel(CommonDemoEntity entity) {
        this.entity = entity;
        this.text = entity.getText();
    }

    public CommonDemoEntity getEntity() {
        return entity;
    }

    @Override
    public boolean isDataEquals(ItemDiffUtilDemoVModel vModel) {
        boolean isEquals = entity.equals(vModel.getEntity());
        return isEquals;
    }

    @Override
    public ItemDiffUtilDemoVModel getDiffCompareObject() {
        return this;
    }

    public ItemDiffUtilDemoVModel setRemoveConsumer(Consumer<ItemDiffUtilDemoVModel> removeConsumer) {
        this.removeConsumer = removeConsumer;
        return this;
    }

    @Override
    public void onViewAttached(View view) {

    }


    /**
     * function:删除Item ViewModel
     *
     * @return
     */
    public View.OnClickListener onRemoveClick() {
        return v -> {
            if (removeConsumer != null) {
                try {
                    removeConsumer.accept(ItemDiffUtilDemoVModel.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_diff_util_demo;
    }
}
