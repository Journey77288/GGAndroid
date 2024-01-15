package io.ganguo.rx.selector;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.ganguo.rx.ReadOnlyRxProperty;
import io.ganguo.rx.RxListProperty;
import io.ganguo.rx.RxProperty;

/**
 * 选择管理器
 * Created by Roger on 09/03/2017.
 */

public abstract class SelectManager<V extends SelectableView<?>> {
    private int maxCount = NO_LIMIT;
    private int minCount = DEFAULT_MIN;
    protected RxListProperty<V> items = new RxListProperty<V>(new ArrayList<V>());

    @SuppressWarnings("WeakerAccess")
    protected RxListProperty<V> selectedItems = new RxListProperty<V>(new ArrayList<V>());

    @SuppressWarnings("WeakerAccess")
    protected RxProperty<Boolean> selectableInternal = new RxProperty<>(true);// 是否可选中

    @SuppressWarnings("WeakerAccess")
    protected ReadOnlyRxProperty<Boolean> selectable = new ReadOnlyRxProperty<Boolean>(selectableInternal);


    /**
     * 不限制最大选中数量
     */
    @SuppressWarnings("WeakerAccess")
    public static final int NO_LIMIT = -1;

    /**
     * 默认最小选择数量
     */
    @SuppressWarnings("WeakerAccess")
    public static final int DEFAULT_MIN = 0;

    /**
     * 设置最大选中 item 数量
     */
    public void setMaxSelected(int maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * 设置最小选中 item 数量
     */
    @SuppressWarnings("WeakerAccess")
    public void setMinSelected(int minCount) {
        this.minCount = minCount;
    }

    /**
     * 添加 item 列表
     */
    public abstract void addAll(@NonNull List<V> items);

    /**
     * 添加单个 Item
     */
    public abstract void add(@NonNull V item);

    /**
     * 清除 Manager 中的所有 item
     */
    public abstract void clear();

    /**
     * 获取选择器中所有 Item
     */
    @NonNull
    public RxListProperty<V> getItems() {
        return items;
    }

    /**
     * 获取已选中的 Item 列表,可通过 getSelectedItems().asObservable()监听选中列表变更状态
     */
    @SuppressWarnings("WeakerAccess")
    public RxListProperty<V> getSelectedItems() {
        return selectedItems;
    }

    /**
     * 选中单个 item
     */
    public abstract void setSelected(@NonNull V item, boolean notify);

    /**
     * 选中多个 item
     */
    public abstract void setSelected(@NonNull List<V> item, boolean notify);

    /**
     * 选中所有 item
     */
    public abstract void selectAll();

    /**
     * 反选 item, 仅当不限制最大选择数量时有效
     */
    public abstract void reverseSelect();

    /**
     * 取消已选中的 item
     */
    public abstract void cancelSelected();

    /**
     * 取消 item 选中
     */
    public abstract void cancelItems(@NonNull List<V> itemList, boolean notify);

    public abstract void cancelItem(@NonNull V item, boolean notify);

    /**
     * 移除单个 item，并停止监听该 item 的选中事件
     */
    public abstract void remove(@NonNull V item);

    /**
     * 移除该列表下的 item，并停止监听选中事件
     */
    public abstract void removeAll(@NonNull List<V> items);

    /**
     * 是否已选中至少一个 Item
     */
    public abstract ReadOnlyRxProperty<Boolean> getIsSelectedProperty();

    @SuppressWarnings("WeakerAccess")
    public boolean isSelected() {
        final Boolean isSelected = getIsSelectedProperty().get();
        return isSelected != null && isSelected;
    }

    /**
     * 设置是否可选中, default is true
     */
    @SuppressWarnings("WeakerAccess")
    public void setSelectable(boolean selectable) {
        this.selectableInternal.set(selectable);
    }

    @SuppressWarnings("WeakerAccess")
    public ReadOnlyRxProperty<Boolean> getIsSelectableProperty() {
        return selectable;
    }

    @SuppressWarnings("WeakerAccess")
    public boolean isSelectable() {
        final Boolean isSelectable = getIsSelectableProperty().get();
        return isSelectable != null && isSelectable;
    }

    /**
     * 当前已选中数量是否大于或等于最大选择数量,若 Manager 最大数量为无限制，则始终返回 false
     */
    public abstract boolean isMaximal();

    /**
     * 当前已选中数量是否小于或等于最小选择数量
     */
    public abstract boolean isMinimal();

    /**
     * 获取最大可选择数量
     */
    @SuppressWarnings("WeakerAccess")
    public int getMaxSelected() {
        return maxCount;
    }

    /**
     * 获取最小可选择数量
     */
    @SuppressWarnings("WeakerAccess")
    public int getMinSelected() {
        return minCount;
    }

    /**
     * 销毁 Manager
     */
    public void destroy() {
        items.dispose();
        items.clear();
        selectedItems.dispose();
        selectedItems.clear();
        selectableInternal.dispose();
        selectable.dispose();
    }
}
