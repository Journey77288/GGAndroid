package io.ganguo.viewmodel.common.item;

import android.databinding.ObservableField;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;

import io.ganguo.viewmodel.R;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.databinding.ItemSampleBinding;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * <p>
 * 示例ItemViewModel，只用于Demo
 * </p>
 * Created by leo on 2018/8/7.
 */
public class ItemSampleVModel extends BaseViewModel<ViewInterface<ItemSampleBinding>> {
    @DrawableRes
    public int bg = R.color.colorPrimary;
    public ObservableField<String> text = new ObservableField<>();
    public Action action;
    private Consumer<View> viewConsumer;

    public ItemSampleVModel setViewConsumer(Consumer<View> viewConsumer) {
        this.viewConsumer = viewConsumer;
        return this;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_sample;
    }

    @Override
    public void onViewAttached(View view) {

    }


    public ItemSampleVModel setBg(@DrawableRes int bg) {
        this.bg = bg;
        return this;
    }

    public ItemSampleVModel setText(String text) {
        this.text.set(text);
        return this;
    }

    public ItemSampleVModel setAction(Action action) {
        this.action = action;
        return this;
    }


    public View.OnClickListener onClick() {
        return v -> {
            if (action != null) {
                try {
                    action.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (viewConsumer != null) {
                try {
                    viewConsumer.accept(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * <p>
     * create ItemViewModel
     * </p>
     *
     * @param color
     * @param action
     * @param text
     * @return
     */
    public static BaseViewModel onCrateItemViewModel(@ColorRes int color, String text, Action action) {
        return new ItemSampleVModel()
                .setAction(action)
                .setBg(color)
                .setText(text);
    }

    /**
     * <p>
     * create ItemViewModel
     * </p>
     *
     * @param viewConsumer
     * @param text
     * @return
     */
    public static BaseViewModel onCrateItemViewModel(String text, Consumer<View> viewConsumer) {
        return new ItemSampleVModel()
                .setViewConsumer(viewConsumer)
                .setText(text);
    }


    /**
     * <p>
     * create ItemViewModel
     * </p>
     *
     * @param action
     * @param text
     * @return
     */
    public static BaseViewModel onCrateItemViewModel(String text, Action action) {
        return new ItemSampleVModel()
                .setAction(action)
                .setText(text);
    }
}
