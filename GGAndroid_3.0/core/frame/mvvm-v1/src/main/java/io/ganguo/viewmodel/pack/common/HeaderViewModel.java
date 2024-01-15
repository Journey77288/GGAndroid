package io.ganguo.viewmodel.pack.common;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;

import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.IncludeHeaderBinding;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.viewinterface.ViewInterface;

/**
 * HeaderContainer ViewModel
 * 可append任意ViewModel到HeaderContainer里中
 *
 * @see HeaderItemViewModel.BackItemViewModel 默认BackItem样式
 * @see HeaderItemViewModel.TitleItemViewModel 默认TitleItem样式
 * Created by Roger on 6/8/16.
 */
public class HeaderViewModel extends BaseViewModel<ViewInterface<IncludeHeaderBinding>> {

    private Builder mBuilder;

    private HeaderViewModel() {
        this.mBuilder = new Builder();
        checkBuilderValid();
    }

    private HeaderViewModel(Builder builder) {
        this.mBuilder = builder;
        checkBuilderValid();
    }

    @Override
    public void onViewAttached(View view) {

    }

    public Builder getBuilder() {
        return mBuilder;
    }

    public int getBackground() {
        if (mBuilder == null) {
            return 0;
        }
        return mBuilder.background;
    }

    public boolean getHeaderVisible() {
        return mBuilder != null && mBuilder.headerVisible;
    }

    private boolean checkBuilderValid() {
        if (mBuilder == null) {
            throw new NullPointerException("builder is null");
        }
        return true;
    }

    public List<BaseViewModel<?>> getLeftItems() {
        if (mBuilder == null) {
            return null;
        }
        return mBuilder.leftItemList;
    }

    public List<BaseViewModel<?>> getRightItems() {
        if (mBuilder == null) {
            return null;
        }
        return mBuilder.rightItemList;
    }

    public List<BaseViewModel<?>> getCenterItems() {
        if (mBuilder == null) {
            return null;
        }
        return mBuilder.centerItemList;
    }

    public boolean isEnableHeaderElevation() {
        if (mBuilder == null) {
            return false;
        }
        return mBuilder.isEnableHeaderElevation;
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_header;
    }

    public static class Builder {

        /**
         * Change Default style value here;
         */
        @DrawableRes
        private int background = R.color.colorPrimary;
        private boolean headerVisible = true;//默认显示header
        private boolean isEnableHeaderElevation = false;//是否开启阴影显示，默认显示
        private List<BaseViewModel<?>> leftItemList = new ArrayList<>();
        private List<BaseViewModel<?>> rightItemList = new ArrayList<>();
        private List<BaseViewModel<?>> centerItemList = new ArrayList<>();

        public Builder background(@DrawableRes int background) {
            this.background = background;
            return this;
        }

        public Builder headerVisible(boolean headerVisible) {
            this.headerVisible = headerVisible;
            return this;
        }

        public Builder appendItemLeft(BaseViewModel item) {
            this.leftItemList.add(item);
            return this;
        }

        public Builder appendItemRight(BaseViewModel item) {
            this.rightItemList.add(item);
            return this;
        }

        public Builder appendItemCenter(BaseViewModel item) {
            this.centerItemList.add(item);
            return this;
        }

        public Builder setEnableHeaderElevation(boolean enableHeaderElevation) {
            isEnableHeaderElevation = enableHeaderElevation;
            return this;
        }


        public HeaderViewModel build() {
            return new HeaderViewModel(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "background=" + background +
                    ", headerVisible=" + headerVisible +
                    '}';
        }
    }

}
