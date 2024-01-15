package io.ganguo.rx.selector;


/**
 * 选择适配器
 * Created by Roger on 10/01/2017.
 */

public class SimpleSelectHelper<T> extends SelectHelper<T> {

    /**
     * @param data         点击事件回调数据
     * @param defaultState 默认选中状态
     */
    public SimpleSelectHelper(T data, boolean defaultState) {
        super(data, defaultState);
    }

    /**
     * 选中事件
     */
    @Override
    public void onSelected() {
        if (getOnSelectListener() != null) {
            getOnSelectListener().onSelected();
        }
    }

    /**
     * 取消选中事件
     */
    @Override
    public void onUnselected() {
        if (getOnSelectListener() != null) {
            getOnSelectListener().onUnselected();
        }
    }

    @Override
    public void onDetach() {
        setOnSelectListener(null);
    }
}
