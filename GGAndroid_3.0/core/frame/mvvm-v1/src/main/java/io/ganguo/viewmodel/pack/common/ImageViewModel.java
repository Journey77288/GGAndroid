package io.ganguo.viewmodel.pack.common;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.databinding.ItemImageViewModelBinding;

/**
 * Created by hulkyao on 30/6/2016.
 */

public class ImageViewModel extends BaseViewModel<ViewInterface<ItemImageViewModelBinding>> {
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

    private Builder builder;

    public ImageViewModel(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_image_view_model;
    }

    public int getWidth() {
        if (builder.width == WRAP_CONTENT || builder.width == MATCH_PARENT) {
            return builder.width;
        }
        return getResources().getDimensionPixelOffset(builder.width);
    }

    public int getHeight() {
        if (builder.height == WRAP_CONTENT || builder.height == MATCH_PARENT) {
            return builder.height;
        }
        return getResources().getDimensionPixelOffset(builder.height);
    }

    public int getMarginLeft() {
        return getResources().getDimensionPixelOffset(builder.marginLeft);
    }

    public int getMarginTop() {
        return getResources().getDimensionPixelOffset(builder.marginTop);
    }

    public int getMarginRight() {
        return getResources().getDimensionPixelOffset(builder.marginRight);
    }

    public int getMarginBottom() {
        return getResources().getDimensionPixelOffset(builder.marginBottom);
    }

    public int getPaddingLeft() {
        return getResources().getDimensionPixelOffset(builder.paddingLeft);
    }

    public int getPaddingTop() {
        return getResources().getDimensionPixelOffset(builder.paddingTop);
    }

    public int getPaddingRight() {
        return getResources().getDimensionPixelOffset(builder.paddingRight);
    }

    public int getPaddingBottom() {
        return getResources().getDimensionPixelOffset(builder.paddingBottom);
    }

    public int getBackground() {
        return builder.background;
    }

    public int getSrc() {
        return builder.src;
    }

    public ImageView.ScaleType getScaleType() {
        return builder.scaleType;
    }

    public int getPlaceHolder() {
        return builder.placeHolder;
    }

    public int getErrorHolder() {
        return builder.errorHolder;
    }

    public String getUrl() {
        return builder.url;
    }

    public boolean showSrc() {
        return builder.src != -1;
    }

    public boolean getVisible() {
        return builder.visible;
    }

    public boolean getClickable() {
        return builder.clickable;
    }

    public Bitmap getBitmap() {
        return builder.bitmap;
    }


    public View.OnClickListener onClick() {
        return builder.onClickListener;
    }

    public static class Builder {
        private int width = WRAP_CONTENT;
        private int height = WRAP_CONTENT;

        private int marginLeft = R.dimen.dp_0;
        private int marginTop = R.dimen.dp_0;
        private int marginRight = R.dimen.dp_0;
        private int marginBottom = R.dimen.dp_0;

        private int paddingLeft = R.dimen.dp_0;
        private int paddingTop = R.dimen.dp_0;
        private int paddingRight = R.dimen.dp_0;
        private int paddingBottom = R.dimen.dp_0;

        private boolean clickable = true;
        private boolean visible = true;
        private int background = R.color.white;
        @DrawableRes
        private int src = -1;
        private Bitmap bitmap;
        private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;


        private int placeHolder = -1;
        private int errorHolder = -1;
        private String url = "";

        private View.OnClickListener onClickListener = null;

        public Builder() {
        }


        public Builder setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public Builder width(@DimenRes int width) {
            this.width = width;
            return this;
        }


        public Builder height(@DimenRes int height) {
            this.height = height;
            return this;
        }

        public Builder marginLeft(@DimenRes int marginLeft) {
            this.marginLeft = marginLeft;
            return this;
        }

        public Builder marginTop(@DimenRes int marginTop) {
            this.marginTop = marginTop;
            return this;
        }

        public Builder marginRight(@DimenRes int marginRight) {
            this.marginRight = marginRight;
            return this;
        }

        public Builder marginBottom(@DimenRes int marginBottom) {
            this.marginBottom = marginBottom;
            return this;
        }

        public Builder paddingLeft(@DimenRes int paddingLeft) {
            this.paddingLeft = paddingLeft;
            return this;
        }

        public Builder paddingTop(@DimenRes int paddingTop) {
            this.paddingTop = paddingTop;
            return this;
        }

        public Builder paddingRight(@DimenRes int paddingRight) {
            this.paddingRight = paddingRight;
            return this;
        }

        public Builder paddingBottom(@DimenRes int paddingBottom) {
            this.paddingBottom = paddingBottom;
            return this;
        }

        public Builder background(@DrawableRes int background) {
            this.background = background;
            return this;
        }

        public Builder src(@DrawableRes int src) {
            this.src = src;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }


        public Builder scaleType(ImageView.ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder errorHolder(int errorHolder) {
            this.errorHolder = errorHolder;
            return this;
        }

        public Builder errorHolder(String url) {
            this.url = url;
            return this;
        }

        public Builder onClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder clickable(boolean clickable) {
            this.clickable = clickable;
            return this;
        }

        public Builder enable(boolean enable) {
            this.clickable = enable;
            return this;
        }

        public ImageViewModel build() {
            return new ImageViewModel(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "width=" + width +
                    ", height=" + height +
                    ", marginLeft=" + marginLeft +
                    ", marginTop=" + marginTop +
                    ", marginRight=" + marginRight +
                    ", marginBottom=" + marginBottom +
                    ", paddingLeft=" + paddingLeft +
                    ", paddingTop=" + paddingTop +
                    ", paddingRight=" + paddingRight +
                    ", paddingBottom=" + paddingBottom +
                    ", clickable=" + clickable +
                    ", visible=" + visible +
                    ", background=" + background +
                    ", src=" + src +
                    ", scaleType=" + scaleType +
                    ", placeHolder=" + placeHolder +
                    ", errorHolder=" + errorHolder +
                    ", url='" + url + '\'' +
                    ", onClickListener=" + onClickListener +
                    '}';
        }
    }
}
