package io.ganguo.vmodel.adapter.callback;

import java.util.List;

import io.ganguo.library.ui.adapter.v7.callback.AdapterDiffCallback;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.R;

/**
 * <p>
 * ViewModel DiffCallback
 * </p>
 * Created by leo on 2018/8/30.
 */
public class ViewModelDiffCallback<T extends BaseViewModel> extends AdapterDiffCallback<T> {
    public ViewModelDiffCallback(List<T> oldData, List<T> newData) {
        super(oldData, newData);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        boolean isSame = super.areItemsTheSame(oldItemPosition, newItemPosition);
        BaseViewModel oldViewModel = getOldData().get(oldItemPosition);
        BaseViewModel newViewModel = getNewData().get(newItemPosition);
        boolean isSameViewModel = oldViewModel.getItemLayoutId() == newViewModel.getItemLayoutId();
        return isSame && isSameViewModel;
    }
}
