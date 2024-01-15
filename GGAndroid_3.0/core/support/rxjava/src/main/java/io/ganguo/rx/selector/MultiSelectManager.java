package io.ganguo.rx.selector;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.ganguo.rx.Ignore;
import io.ganguo.rx.Provider;
import io.ganguo.rx.ReadOnlyRxProperty;
import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxProperty;
import io.ganguo.rx.RxTransformer;
import io.ganguo.utils.util.Collections;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * 多选管理器
 * Created by Roger on 12/01/2017.
 */
public class MultiSelectManager<V extends SelectableView<?>> extends SelectManager<V> {

    private RxProperty<Boolean> isAllSelectedInternal; //是否全选中;
    private ReadOnlyRxProperty<Boolean> isSelected;// 是否已选中至少一个 item;
    private ReadOnlyRxProperty<Integer> onMaxSelectedEvent;
    private ReadOnlyRxProperty<Boolean> isAllSelected;

    private HashMap<V, Disposable> clickObservableList = new HashMap<>();
    private HashMap<V, Disposable> removeObservableList = new HashMap<>();

    private boolean autoSelect = false; // 超出最大可选中数目时是否自动取消选中已选中的第一个 item, 并选中新的 item

    public MultiSelectManager() {
        isAllSelectedInternal = new RxProperty<Boolean>(
                Observable.combineLatest(selectedItems, items, new BiFunction<List<V>, List<V>, Boolean>() {
                    @Override
                    public Boolean apply(List<V> selectedItems, List<V> items) {
                        return Collections.isNotEmpty(items) && Collections.isNotEmpty(selectedItems) && selectedItems.size() == items.size();
                    }
                }));

        isAllSelected = new ReadOnlyRxProperty<>(isAllSelectedInternal);

        isSelected = new ReadOnlyRxProperty<Boolean>(selectedItems.map(new Function<List<V>, Boolean>() {
            @Override
            public Boolean apply(List<V> selectedItems) {
                return Collections.isNotEmpty(selectedItems);
            }
        }));

        onMaxSelectedEvent = new ReadOnlyRxProperty<>(new Provider<Integer>()
                .source(selectedItems.filter(new Predicate<List<V>>() {
                    @Override
                    public boolean test(List<V> vs) throws Exception {
                        return isMaximal();
                    }
                })
                        .map(new Function<List<V>, Integer>() {
                            @Override
                            public Integer apply(List<V> vs) throws Exception {
                                return getMaxSelected();
                            }
                        }))
                .defaultValue(getMaxSelected())
                .mode(Provider.NONE));
    }

    @Override
    public void setMaxSelected(int maxCount) {
        if (maxCount == 0) {
            throw new IllegalArgumentException("max selected count must large than 0 or no limit");
        }
        super.setMaxSelected(maxCount);
        updateSelectedState();
    }

    @Override
    public void addAll(@NonNull List<V> items) {
        this.items.addAll(items);
        for (V v : items) {
            addObserveClickEvent(v);
            addObserveRemoveSelfEvent(v);
        }
        addSelected(items);
    }

    @Override
    public void add(@NonNull V item) {
        items.add(item);
        addObserveClickEvent(item);
        addObserveClickEvent(item);
        if (item.getSelectHelper().isSelected()) {
            if (isMaximal()) {
                if (autoSelect) {
                    final List<V> items = selectedItems.get();
                    if (Collections.isNotEmpty(items)) {
                        cancelItem(items.get(0), false);
                        selectedItems.add(item);
                    }
                } else {
                    item.getSelectHelper().getSelected().set(false);
                }
            } else {
                selectedItems.add(item);
            }
        }
    }

    private void addSelected(final List<V> itemList) {
        if (Collections.isNotEmpty(itemList)) {
            Observable.fromIterable(itemList)
                    .filter(new Predicate<V>() {
                        @Override
                        public boolean test(V selectView) throws Exception {
                            return selectView.getSelectHelper().isSelected();
                        }
                    })
                    .toList()
                    .subscribe(new Consumer<List<V>>() {
                        @Override
                        public void accept(List<V> selectViews) {
                            List<V> toAddItems = new ArrayList<>();
                            for (V item : selectViews) {
                                if (isMaximal()) {
                                    if (autoSelect) {
                                        final List<V> items = selectedItems.get();
                                        if (Collections.isNotEmpty(items)) {
                                            cancelItem(items.get(0), false);
                                            toAddItems.add(item);
                                        }
                                    } else {
                                        item.getSelectHelper().getSelected().set(false);
                                    }
                                } else {
                                    toAddItems.add(item);
                                }
                            }
                            selectedItems.addAll(toAddItems);
                        }
                    });
        }
    }

    /**
     * 监听 Item 点击事件，并处理多选逻辑
     */
    private void addObserveClickEvent(final V selectableView) {
        if (!clickObservableList.containsKey(selectableView)) {
            Disposable disposable = selectableView.getSelectHelper().getToggleEvent()
                    .subscribeOn(Schedulers.io())
                    .compose(RxTransformer.toIgnore())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Ignore>() {
                        @Override
                        public void accept(Ignore ignore) {
                            if (!isSelectable()) {
                                return;
                            }
                            boolean isSelected = selectableView.getSelectHelper().isSelected();
                            if (isSelected) {
                                if (!isMinimal()) {
                                    selectableView.getSelectHelper().getSelected().set(false);
                                    selectedItems.remove(selectableView);
                                    selectableView.getSelectHelper().onUnselected();
                                }
                            } else {
                                if (isMaximal()) {
                                    if (autoSelect) {
                                        final List<V> items = selectedItems.get();
                                        if (Collections.isNotEmpty(items)) {
                                            cancelItem(items.get(0), false);
                                            selectableView.getSelectHelper().getSelected().set(true);
                                            selectedItems.add(selectableView);
                                            selectableView.getSelectHelper().onSelected();
                                        }
                                    } else {
                                        // isMaximal do nothing
                                    }
                                } else {
                                    selectableView.getSelectHelper().getSelected().set(true);
                                    selectedItems.add(selectableView);
                                    selectableView.getSelectHelper().onSelected();
                                }
                            }
                        }
                    }, RxActions.printThrowable("addObserveClickEvent"));
            clickObservableList.put(selectableView, disposable);
        }
    }

    public void updateSelectedState() {
        if (getMaxSelected() != NO_LIMIT) {
            if (selectedItems.size() > getMaxSelected()) {
                final List<V> items = selectedItems.get();
                if (Collections.isNotEmpty(items)) {
                    List<V> toCancelItems = items.subList(0, selectedItems.size() - getMaxSelected());
                    cancelItems(toCancelItems, true);
                }
            }
        }
    }

    /**
     * 监听 Item 删除事件
     */
    private void addObserveRemoveSelfEvent(final V selectableView) {
        if (!removeObservableList.containsKey(selectableView)) {
            Disposable disposable = selectableView.getSelectHelper().getRemoveEvent()
                    .subscribeOn(Schedulers.io())
                    .compose(RxTransformer.toIgnore())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Ignore>() {
                        @Override
                        public void accept(Ignore ignore) {
                            remove(selectableView);
                        }
                    }, RxActions.printThrowable("addObserveRemoveSelfEvent"));
            removeObservableList.put(selectableView, disposable);
        }
    }

    @Override
    public void cancelSelected() {
        final List<V> items = selectedItems.get();
        if (Collections.isNotEmpty(items)) {
            cancelItems(items, true);
        }
    }

    @Override
    public void cancelItem(@NonNull V item, boolean notify) {
        if (isSelectable() && Collections.isNotEmpty(items.get())) {
            if (items.containsObject(item)) {
                if (!item.getSelectHelper().isSelected()) {
                    // already unselected do nothing
                } else {
                    if (!isMinimal()) {
                        item.getSelectHelper().getSelected().set(false);
                        item.getSelectHelper().onUnselected();
                        if (notify) {
                            selectedItems.remove(item);
                        } else {
                            final List<V> items = selectedItems.get();
                            if (Collections.isNotEmpty(items)) {
                                items.remove(item);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void cancelItems(@NonNull final List<V> itemList, final boolean notify) {
        if (Collections.isNotEmpty(itemList)) {
            for (V item : new ArrayList<V>(itemList)) {
                cancelItem(item, false);
            }
            if (notify) {
                selectedItems.forceNotify();
            }
        }
    }

    /**
     * 选中指定 item
     */
    @Override
    public void setSelected(@NonNull V item, boolean notify) {
        if (isSelectable() && Collections.isNotEmpty(items.get())) {
            if (items.containsObject(item)) {
                boolean isSelected = item.getSelectHelper().isSelected();
                if (isSelected) {
                    // already selected do nothing
                } else {
                    if (isMaximal()) {
                        if (autoSelect) {
                            // cancel old select
                            final List<V> sItems = selectedItems.get();
                            if (sItems != null) {
                                cancelItem(sItems.get(0), false);
                                // set new select
                                item.getSelectHelper().getSelected().set(true);
                                if (notify) {
                                    selectedItems.add(item);
                                } else {
                                    sItems.add(item);
                                }
                                item.getSelectHelper().onSelected();
                            }
                        } else {
                            // do nothing
                        }
                    } else {
                        // set new select
                        item.getSelectHelper().getSelected().set(true);
                        if (notify) {
                            selectedItems.add(item);
                        } else {
                            final List<V> sItems = selectedItems.get();
                            if (sItems != null) {
                                sItems.add(item);
                            }
                        }
                        item.getSelectHelper().onSelected();
                    }
                }
            }
        }
    }

    @Override
    public void setSelected(@NonNull List<V> itemList, boolean notify) {
        if (Collections.isNotEmpty(itemList)) {
            for (V item : new ArrayList<V>(itemList)) {
                setSelected(item, false);
            }
            if (notify) {
                selectedItems.forceNotify();
            }
        }
    }

    /**
     * 删除 item
     */
    @Override
    public void remove(@NonNull V selectableView) {
        selectedItems.remove(selectableView);
        items.remove(selectableView);
        unsubscribeRemoveEvent(selectableView);
        unsubscribeClickEvent(selectableView);
        selectableView.getSelectHelper().detach();
    }

    @Override
    public void removeAll(@NonNull List<V> selectableViews) {
        selectedItems.removeAll(selectableViews);
        items.removeAll(selectableViews);
        for (V v : selectableViews) {
            v.getSelectHelper().detach();
            unsubscribeRemoveEvent(v);
            unsubscribeClickEvent(v);
        }
    }

    private void unsubscribeClickEvent(V selectableView) {
        if (clickObservableList.containsKey(selectableView)) {
            Disposable disposable = clickObservableList.get(selectableView);
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            clickObservableList.remove(selectableView);
        }
    }

    private void unsubscribeRemoveEvent(V selectableView) {
        if (removeObservableList.containsKey(selectableView)) {
            Disposable disposable = removeObservableList.get(selectableView);
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            removeObservableList.remove(selectableView);
        }
    }

    @Override
    public void clear() {
        final List<V> allItems = items.get();
        if (Collections.isNotEmpty(allItems)) {
            for (V v : allItems) {
                v.getSelectHelper().detach();
                unsubscribeClickEvent(v);
                unsubscribeRemoveEvent(v);
            }
        }
        selectedItems.clear();
        items.clear();
        removeObservableList.clear();
        clickObservableList.clear();
    }

    /**
     * 是否已选中至少一个 item
     */
    @Override
    public ReadOnlyRxProperty<Boolean> getIsSelectedProperty() {
        return isSelected;
    }

    /**
     * 选中所有 Item, 此操作会忽略最大选中数量, 强制选中所有 item
     */
    @Override
    public void selectAll() {
        List<V> allItems = items.get();
        final List<V> newSelectedList = new ArrayList<>();
        if (isSelectable() && Collections.isNotEmpty(allItems)) {
            Observable.fromIterable(allItems)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(selectableView -> {
                        if (!selectableView.getSelectHelper().isSelected()) {
                            selectableView.getSelectHelper().getSelected().set(true);
                            newSelectedList.add(selectableView);
                            selectableView.getSelectHelper().onSelected();
                        }
                    })
                    .doOnComplete(() -> {
                        if (Collections.isNotEmpty(newSelectedList)) {
                            selectedItems.addAll(newSelectedList);
                        }
                    })
                    .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + "-selectAll-"));
        }
    }

    @Override
    public void reverseSelect() {
        final List<V> allItems = items.get();
        if (getMaxSelected() == NO_LIMIT) {
            if (Collections.isNotEmpty(allItems)) {
                for (V item : allItems) {
                    if (item.getSelectHelper().isSelected()) {
                        cancelItem(item, false);
                    } else {
                        setSelected(item, false);
                    }
                }
                selectedItems.forceNotify();
            }
        }
    }

    @Override
    public boolean isMaximal() {
        return getMaxSelected() != NO_LIMIT && selectedItems.size() >= getMaxSelected();
    }

    @Override
    public boolean isMinimal() {
        return selectedItems.size() <= getMinSelected();
    }

    public ReadOnlyRxProperty<Integer> getOnMaxSelectedEvent() {
        return onMaxSelectedEvent;
    }

    /**
     * 是否全选中
     */
    public ReadOnlyRxProperty<Boolean> getIsAllSelectedProperty() {
        return isAllSelected;
    }

    public boolean isAllSelected() {
        final Boolean isAllSelected = getIsAllSelectedProperty().get();
        return isAllSelected != null && isAllSelected;
    }

    public boolean isAutoSelect() {
        return autoSelect;
    }

    public void setAutoSelect(boolean autoSelect) {
        this.autoSelect = autoSelect;
    }

    @Override
    public void destroy() {
        super.destroy();
        for (V v : new ArrayList<>(removeObservableList.keySet())) {
            unsubscribeRemoveEvent(v);
        }
        for (V v : new ArrayList<>(clickObservableList.keySet())) {
            unsubscribeClickEvent(v);
        }
        clickObservableList.clear();
        removeObservableList.clear();
        isAllSelectedInternal.dispose();
        onMaxSelectedEvent.dispose();
        isSelected.dispose();
    }
}
