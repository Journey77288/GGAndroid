package io.ganguo.library.ui.adapter.v7.callback;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

import io.ganguo.utils.common.ClassHelper;

/**
 * <p>
 * DiffUtil Callback
 * </p>
 * Created by leo on 2018/8/23.
 */
public class AdapterDiffCallback<T> extends DiffUtil.Callback {
    private List<T> oldData = new ArrayList<>();
    private List<T> newData = new ArrayList<>();

    public AdapterDiffCallback(List<T> oldData, List<T> newData) {
        this.oldData.addAll(oldData);
        this.newData.addAll(newData);
    }

    public List<T> getOldData() {
        return oldData;
    }

    public List<T> getNewData() {
        return newData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldData.get(oldItemPosition);
        T newItem = newData.get(newItemPosition);
        boolean isClassSame = ClassHelper.isEquals(oldItem.getClass(), newItem.getClass());
        boolean isComparator = (oldItem instanceof IDiffComparator) && (newItem instanceof IDiffComparator);
        return isClassSame && isComparator && oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        IDiffComparator oldItem = (IDiffComparator) oldData.get(oldItemPosition);
        IDiffComparator newItem = (IDiffComparator) newData.get(newItemPosition);
        return oldItem.isDataEquals(newItem.getDiffCompareObject());
    }

}
