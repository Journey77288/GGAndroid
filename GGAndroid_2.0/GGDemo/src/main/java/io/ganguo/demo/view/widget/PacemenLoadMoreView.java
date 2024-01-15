package io.ganguo.demo.view.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemPacemenLoadMoreBinding;
import io.ganguo.demo.viewmodel.item.ItemPacemenLoadMoreVModel;
import io.ganguo.library.ui.view.loading.ILoadMoreViewInterface;
import io.ganguo.vmodel.ViewModelHelper;

/**
 * <p>
 * 自定义LoadMore Loading样式
 * </p>
 * Created by leo on 2018/9/14.
 */
public class PacemenLoadMoreView implements ILoadMoreViewInterface<ItemPacemenLoadMoreBinding> {
    private ItemPacemenLoadMoreVModel loadMoreVModel;
    private ItemPacemenLoadMoreBinding loadMoreBinding;


    @Override
    public void initView(Context context, ViewGroup parentView) {
        loadMoreBinding = ItemPacemenLoadMoreBinding.inflate(LayoutInflater.from(context), parentView, false);
        loadMoreVModel = new ItemPacemenLoadMoreVModel();
        ViewModelHelper.bind(loadMoreBinding, loadMoreVModel);
    }

    @Override
    public ItemPacemenLoadMoreBinding getViewBinding() {
        return loadMoreBinding;
    }


    @Override
    public void onStartLoading() {
        if (loadMoreVModel != null) {
            loadMoreVModel.startAnim();
        }
    }

    @Override
    public void onStopLoading() {
        if (loadMoreVModel != null) {
            loadMoreVModel.stopAnim();
        }
    }

    @Override
    public void onDestroy() {
        if (loadMoreVModel != null) {
            loadMoreVModel.onDestroy();
            loadMoreVModel = null;
        }
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_pacemen_load_more;
    }
}
