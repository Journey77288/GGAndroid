package io.ganguo.rx.selector;

import java.util.List;

import io.ganguo.rx.RxProperty;
import io.ganguo.utils.util.Collections;
import io.reactivex.functions.Function;

/**
 * 单选管理器
 * Created by Roger on 12/01/2017.
 */

public class SingleSelectManager<V extends SelectableView<?>> extends MultiSelectManager<V> {
    private RxProperty<V> selectedItem;

    public SingleSelectManager() {
        this(true);
    }

    public SingleSelectManager(boolean isAtLeastSelectOne) {
        setMinSelected(isAtLeastSelectOne ? 1 : 0);
        setMaxSelected(1);
        setAutoSelect(true);
        selectedItem = new RxProperty<>(getSelectedItems().map(new Function<List<V>, V>() {
            @Override
            public V apply(List<V> selectedList) {
                if (Collections.isNotEmpty(selectedList)) {
                    return selectedList.get(0);
                } else {
                    return null;
                }
            }
        }));
    }

    public RxProperty<V> getSelectedItem() {
        return selectedItem;
    }

    @Override
    public void destroy() {
        super.destroy();
        selectedItem.dispose();
    }
}
