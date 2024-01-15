package io.ganguo.rx.selector;

import io.ganguo.rx.Provider;
import io.ganguo.rx.RxProperty;

/**
 * 选择辅助器
 * Created by Roger on 10/01/2017.
 */

public abstract class SelectHelper<T> {
    private RxProperty<Boolean> selected;
    private RxProperty<T> onToggleEvent;
    private RxProperty<T> onRemoveEvent;
    private OnSelectListener onSelectListener;

    private SelectHelper() {
    }

    /**
     * @param data         点击事件回调数据
     * @param defaultState 默认选中状态
     */
    public SelectHelper(T data, boolean defaultState) {
        this.selected = new RxProperty<>(defaultState);
        this.onToggleEvent = new RxProperty<>(new Provider<T>()
                .defaultValue(data)
                .mode(Provider.NONE));
        this.onRemoveEvent = new RxProperty<>(new Provider<T>()
                .defaultValue(data)
                .mode(Provider.NONE));
    }

    /**
     * 切换选中状态，具体选中逻辑（多选或单选）由 SelectManager 处理
     */
    public void toggleSelect() {
        getToggleEvent().forceNotify();
    }

    /**
     * 获取当前选中状态，切勿用于改变选中状态，要改变状态请通过 SelectManager 处理
     */
    public RxProperty<Boolean> getSelected() {
        return selected;
    }

    public boolean isSelected() {
        final Boolean isSelected = selected.get();
        return isSelected != null && isSelected;
    }

    /**
     * 获取切换状态事件
     */
    final RxProperty<T> getToggleEvent() {
        return onToggleEvent;
    }

    /**
     * 获取删除 item 事件
     */
    final RxProperty<T> getRemoveEvent() {
        return onRemoveEvent;
    }

    /**
     * Detach form SelectableView, release resource and unsubscribe select event
     */
    public void detach() {
        onDetach();
        selected.dispose();
        onToggleEvent.dispose();
        onRemoveEvent.dispose();
    }

    public boolean isDetached() {
        return selected.isDisposed()
                && onToggleEvent.isDisposed()
                && onRemoveEvent.isDisposed();
    }

    /**
     * 选中事件
     */
    protected abstract void onSelected();

    /**
     * 取消选中事件
     */
    protected abstract void onUnselected();

    protected abstract void onDetach();

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelected();

        void onUnselected();
    }
}
