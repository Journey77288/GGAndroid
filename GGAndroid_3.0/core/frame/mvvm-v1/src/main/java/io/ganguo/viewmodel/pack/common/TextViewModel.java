package io.ganguo.viewmodel.pack.common;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import io.ganguo.core.context.BaseApp;
import io.ganguo.utils.helper.ResHelper;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.ItemTextViewModelBinding;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.viewinterface.ViewInterface;

/**
 * Created by hulkyao on 30/6/2016.
 */
public class TextViewModel extends BaseViewModel<ViewInterface<ItemTextViewModelBinding>> {
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

    private Builder builder;

    private TextViewModel(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text_view_model;
    }

    public int getWidth() {
        if (builder.width == WRAP_CONTENT || builder.width == MATCH_PARENT) {
            return builder.width;
        }
        return BaseApp.me().getResources().getDimensionPixelOffset(builder.width);
    }

    public int getHeight() {
        if (builder.height == WRAP_CONTENT || builder.height == MATCH_PARENT) {
            return builder.height;
        }
        return BaseApp.me().getResources().getDimensionPixelOffset(builder.height);
    }

    public String getContent() {
        return builder.content;
    }

    public String getHint() {
        return builder.hint;
    }

    @Dimension
    public int getTextSize() {
        return ResHelper.getDimensionPixelSize(builder.textSizeRes);
    }

    @ColorInt
    public int getTextColor() {
        return ResHelper.getColor(builder.textColorRes);
    }

    @ColorInt
    public int getTextSelectColor() {
        return ResHelper.getColor(builder.textSelectColorRes);
    }

    @DrawableRes
    public int getBackgroundDrawableRes() {
        return builder.backgroundRes;
    }

    @DrawableRes
    public int getDrawableLeftRes() {
        return builder.drawableLeftRes;
    }

    @DrawableRes
    public int getDrawableRightRes() {
        return builder.drawableRightRes;
    }

    @DrawableRes
    public int getDrawableTopRes() {
        return builder.drawableTopResRes;
    }

    @DrawableRes
    public int getDrawableBottomRes() {
        return builder.drawableBottomRes;
    }

    @Dimension
    public int getPaddingLeft() {
        return ResHelper.getDimensionPixelSize(builder.paddingLeftRes);
    }

    @Dimension
    public int getPaddingRight() {
        return ResHelper.getDimensionPixelSize(builder.paddingRightRes);
    }

    @Dimension
    public int getPaddingTop() {
        return ResHelper.getDimensionPixelSize(builder.paddingTopRes);
    }

    @Dimension
    public int getPaddingBottom() {
        return ResHelper.getDimensionPixelSize(builder.paddingBottomRes);
    }

    @Dimension
    public int getMarginLeft() {
        return ResHelper.getDimensionPixelSize(builder.marginLeftRes);
    }

    @Dimension
    public int getMarginTop() {
        return ResHelper.getDimensionPixelSize(builder.marginTopRes);
    }

    @Dimension
    public int getMarginRight() {
        return ResHelper.getDimensionPixelSize(builder.marginRightRes);
    }

    @TextGravity
    public int getGravity() {
        return builder.gravity;
    }

    @Dimension
    public int getMarginBottom() {
        return ResHelper.getDimensionPixelSize(builder.marginBottomRes);
    }

    @Dimension
    public int getDrawablePadding() {
        return ResHelper.getDimensionPixelSize(builder.drawablePaddingRes);
    }

    public boolean getVisible() {
        return builder.visible;
    }

    public boolean getEnable() {
        return builder.enable;
    }

    public int getMaxLine() {
        return builder.maxLine;
    }

    public int getMaxLength() {
        return builder.maxLength;
    }

    public View.OnClickListener onClick() {
        if (builder.onClickListener == null) {
            return null;
        }
        return builder.onClickListener;
    }

    public Builder getBuilder() {
        return builder;
    }

    public static class Builder {
        private String content = "";
        private String hint = "";
        private boolean visible = true;
        private boolean enable = true;

        private int height = WRAP_CONTENT;
        private int width = WRAP_CONTENT;
        private int textSizeRes = R.dimen.font_15;
        @ColorRes
        private int textColorRes = R.color.black;
        @ColorRes
        private int textSelectColorRes = R.color.black;
        @DrawableRes
        private int backgroundRes = R.color.transparent;
        @DrawableRes
        private int drawableLeftRes = 0;
        @DrawableRes
        private int drawableRightRes = 0;
        @DrawableRes
        private int drawableTopResRes = 0;
        @DrawableRes
        private int drawableBottomRes = 0;
        @DimenRes
        private int drawablePaddingRes = R.dimen.dp_0;

        @DimenRes
        private int paddingLeftRes = R.dimen.dp_0;
        @DimenRes
        private int paddingRightRes = R.dimen.dp_0;
        @DimenRes
        private int paddingTopRes = R.dimen.dp_0;
        @DimenRes
        private int paddingBottomRes = R.dimen.dp_0;
        @DimenRes
        private int marginLeftRes = R.dimen.dp_0;
        @DimenRes
        private int marginTopRes = R.dimen.dp_0;
        @DimenRes
        private int marginRightRes = R.dimen.dp_0;
        @DimenRes
        private int marginBottomRes = R.dimen.dp_0;
        private int maxLine = 1;
        private int maxLength = -1;
        @TextGravity
        private int gravity = Gravity.START;

        private View.OnClickListener onClickListener = null;

        public Builder() {

        }

        public TextViewModel build() {
            return new TextViewModel(this);
        }

        public Builder gravity(@TextGravity int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder hint(String hint) {
            this.hint = hint;
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

        public Builder textSizeRes(@DimenRes int textSizeRes) {
            this.textSizeRes = textSizeRes;
            return this;
        }

        public Builder textColorRes(@ColorRes int textColorRes) {
            this.textColorRes = textColorRes;
            return this;
        }

        public Builder textSelectColorRes(@ColorRes int textSelectColorRes) {
            this.textSelectColorRes = textSelectColorRes;
            return this;
        }

        public Builder backgroundRes(@DrawableRes int backgroundRes) {
            this.backgroundRes = backgroundRes;
            return this;
        }

        public Builder drawableLeftRes(@DrawableRes int drawableLeftRes) {
            this.drawableLeftRes = drawableLeftRes;
            return this;
        }

        public Builder drawableRightRes(@DrawableRes int drawableRightRes) {
            this.drawableRightRes = drawableRightRes;
            return this;
        }

        public Builder drawableTopRes(@DrawableRes int drawableTopRes) {
            this.drawableTopResRes = drawableTopRes;
            return this;
        }

        public Builder drawableBottomRes(@DrawableRes int drawableBottomRes) {
            this.drawableBottomRes = drawableBottomRes;
            return this;
        }

        public Builder paddingLeftRes(@DimenRes int paddingLeftRes) {
            this.paddingLeftRes = paddingLeftRes;
            return this;
        }

        public Builder paddingRightRes(@DimenRes int paddingRightRes) {
            this.paddingRightRes = paddingRightRes;
            return this;
        }

        public Builder paddingTopRes(@DimenRes int paddingTopRes) {
            this.paddingTopRes = paddingTopRes;
            return this;
        }

        public Builder paddingBottomRes(@DimenRes int paddingBottomRes) {
            this.paddingBottomRes = paddingBottomRes;
            return this;
        }

        public Builder marginLeftRes(@DimenRes int marginLeftRes) {
            this.marginLeftRes = marginLeftRes;
            return this;
        }

        public Builder marginTopRes(@DimenRes int marginTopRes) {
            this.marginTopRes = marginTopRes;
            return this;
        }

        public Builder marginRightRes(@DimenRes int marginRightRes) {
            this.marginRightRes = marginRightRes;
            return this;
        }

        public Builder marginBottomRes(@DimenRes int marginBottomRes) {
            this.marginBottomRes = marginBottomRes;
            return this;
        }

        public Builder drawablePaddingRes(@DimenRes int drawablePaddingRes) {
            this.drawablePaddingRes = drawablePaddingRes;
            return this;
        }

        public Builder maxLine(int maxLine) {
            this.maxLine = maxLine;
            return this;
        }

        public Builder maxLength(int maxLength) {
            this.maxLength = maxLength;
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

        public Builder enable(boolean enable) {
            this.enable = enable;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "content='" + content + '\'' +
                    ", hint='" + hint + '\'' +
                    ", visible=" + visible +
                    ", enable=" + enable +
                    ", height=" + height +
                    ", width=" + width +
                    ", textSizeRes=" + textSizeRes +
                    ", textColorRes=" + textColorRes +
                    ", textSelectColorRes=" + textSelectColorRes +
                    ", backgroundRes=" + backgroundRes +
                    ", drawableLeftRes=" + drawableLeftRes +
                    ", drawableRightRes=" + drawableRightRes +
                    ", drawableTopResRes=" + drawableTopResRes +
                    ", drawableBottomRes=" + drawableBottomRes +
                    ", drawablePaddingRes=" + drawablePaddingRes +
                    ", paddingLeftRes=" + paddingLeftRes +
                    ", paddingRightRes=" + paddingRightRes +
                    ", paddingTopRes=" + paddingTopRes +
                    ", paddingBottomRes=" + paddingBottomRes +
                    ", marginLeftRes=" + marginLeftRes +
                    ", marginTopRes=" + marginTopRes +
                    ", marginRightRes=" + marginRightRes +
                    ", marginBottomRes=" + marginBottomRes +
                    ", maxLine=" + maxLine +
                    ", maxLength=" + maxLength +
                    ", onClickListener=" + onClickListener +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TextViewModel{" +
                "builder=" + builder +
                '}';
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Gravity.START, Gravity.END, Gravity.BOTTOM, Gravity.TOP, Gravity.CENTER, Gravity.LEFT, Gravity.RIGHT, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL})
    public @interface TextGravity {
    }
}
