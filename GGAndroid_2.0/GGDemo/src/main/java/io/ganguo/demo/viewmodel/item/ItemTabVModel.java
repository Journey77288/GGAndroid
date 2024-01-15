package io.ganguo.demo.viewmodel.item;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringDef;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemTabIconViewBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ActivityInterface;

/**
 * Created by leo on 16/7/11.
 * item tab
 */
public class ItemTabVModel extends BaseViewModel<ActivityInterface<ItemTabIconViewBinding>> {
    @DrawableRes
    private int icon;
    private String title;

    public ItemTabVModel(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_tab_icon_view;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
