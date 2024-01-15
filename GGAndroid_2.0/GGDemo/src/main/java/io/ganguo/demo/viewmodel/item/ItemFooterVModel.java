package io.ganguo.demo.viewmodel.item;

import android.view.View;


import io.ganguo.demo.R;
import io.ganguo.demo.databinding.IncludeTestFooterBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.reactivex.functions.Consumer;

/**
 * Created by Roger on 6/23/16.
 */

public class ItemFooterVModel extends BaseViewModel<ViewInterface<IncludeTestFooterBinding>> {
    private Consumer<View> actionClear;

    public ItemFooterVModel(Consumer<View> actionClear) {
        this.actionClear = actionClear;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public View.OnClickListener onClear() {
        return v -> {
            try {
                if (actionClear != null) {
                    actionClear.accept(v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.include_test_footer;
    }
}
